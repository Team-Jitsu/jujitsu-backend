package com.fightingkorea.platform.domain.trainer.repository.impl;

import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerSearchRequest;
import com.fightingkorea.platform.domain.trainer.entity.QTrainer;
import com.fightingkorea.platform.domain.trainer.entity.QTrainerSpecialty;
import com.fightingkorea.platform.domain.trainer.repository.CustomTrainerRepository;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.QUser;
import com.fightingkorea.platform.domain.video.entity.QVideo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomTrainerRepositoryImpl implements CustomTrainerRepository {

    private final JPAQueryFactory queryFactory;

    QTrainer trainer = QTrainer.trainer;
    QUser user = QUser.user;
    QTrainerSpecialty trainerSpecialty = QTrainerSpecialty.trainerSpecialty;
    QVideo video = QVideo.video;

    @Override
    public PageImpl<TrainerResponse> search(TrainerSearchRequest request, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.isActive.eq(true));

        if (request.getRegion() != null) {
            builder.and(user.region.eq(request.getRegion()));
        }

        if (request.getSearch() != null) {
            builder.and(user.nickname.containsIgnoreCase(request.getSearch())
                    .or(trainer.bio.containsIgnoreCase(request.getSearch())));
        }

        if (request.getSpecialtyId() != null) {
            builder.and(trainerSpecialty.specialtyId.eq(request.getSpecialtyId()));
        }

        Order order = "asc".equalsIgnoreCase(request.getSortOrder()) ? Order.ASC : Order.DESC;
        OrderSpecifier<?> orderSpecifier;
        if ("videoCount".equalsIgnoreCase(request.getSortBy())) {
            orderSpecifier = new OrderSpecifier<>(order, video.count());
        } else {
            orderSpecifier = new OrderSpecifier<>(order, user.createdAt);
        }

        List<TrainerResponse> contents = queryFactory
                .select(Projections.bean(TrainerResponse.class,
                        trainer.trainerId,
                        trainer.accountOwnerName,
                        trainer.accountNumber,
                        trainer.bio,
                        trainer.automaticSettlement,
                        trainer.charge,
                        Projections.bean(UserResponse.class, // UserResponse도 bean 매핑
                                user.userId,
                                user.nickname,
                                user.role,
                                user.createdAt
                        ).as("user"), // TrainerResponse의 user 필드에 매핑
                        trainer.user.email
                ))
                .from(trainer)
                .join(trainer.user, user)
                .leftJoin(trainerSpecialty).on(trainerSpecialty.trainerId.eq(trainer.trainerId))
                .leftJoin(trainer.videos, video)
                .where(builder)
                .groupBy(trainer.trainerId)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(trainer.trainerId.countDistinct())
                .from(trainer)
                .join(trainer.user, user)
                .leftJoin(trainerSpecialty).on(trainerSpecialty.trainerId.eq(trainer.trainerId))
                .leftJoin(trainer.videos, video)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public PageImpl<TrainerResponse> getTrainers(Pageable pageable) {
        List<TrainerResponse> contents = queryFactory
                .select(Projections.constructor(TrainerResponse.class,
                        trainer.trainerId,
                        trainer.accountOwnerName,
                        trainer.accountNumber,
                        trainer.bio,
                        trainer.automaticSettlement,
                        trainer.charge,
                        Projections.constructor(UserResponse.class,
                                user.userId,
                                user.nickname,
                                user.role,
                                user.createdAt
                        )
                ))
                .from(trainer)
                .join(trainer.user, user)
                .where(user.isActive.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.createdAt.desc()) // 기본 정렬 createdAt desc
                .fetch();

        Long total = queryFactory
                .select(trainer.count())
                .from(trainer)
                .join(trainer.user, user)
                .where(user.isActive.eq(true))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total == null ? 0 : total);
    }


}
