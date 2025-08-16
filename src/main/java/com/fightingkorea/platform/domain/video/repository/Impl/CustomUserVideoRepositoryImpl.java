package com.fightingkorea.platform.domain.video.repository.Impl;

import com.fightingkorea.platform.domain.video.dto.PurchaseSearchRequest;
import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.entity.QUserVideo;
import com.fightingkorea.platform.domain.video.entity.QVideo;
import com.fightingkorea.platform.domain.video.entity.QVideoCategory;
import com.fightingkorea.platform.domain.video.repository.CustomUserVideoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class CustomUserVideoRepositoryImpl implements CustomUserVideoRepository{

    private final JPAQueryFactory queryFactory;

    QUserVideo userVideo = QUserVideo.userVideo;
    QVideo video = QVideo.video;

    @Override
    public Page<UserVideoResponse> getPurchasedVideoList(Long userId, PurchaseSearchRequest request, Pageable pageable){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(userVideo.user.userId.eq(userId));
        if (request.getSearch() != null && !request.getSearch().isEmpty()) {
            builder.and(video.title.containsIgnoreCase(request.getSearch()));
        }

        JPAQuery<UserVideoResponse> contentQuery = queryFactory
                .select(Projections.constructor(UserVideoResponse.class,
                        userVideo.userVideoId,
                        video.videoId,
                        video.title,
                        userVideo.purchasePrice,
                        userVideo.purchasedAt
                ))
                .from(userVideo)
                .join(userVideo.video, video);

        JPAQuery<Long> countQuery = queryFactory
                .select(userVideo.count())
                .from(userVideo)
                .join(userVideo.video, video);

        if (request.getCategoryId() != null) {
            QVideoCategory vc = QVideoCategory.videoCategory;
            contentQuery.join(video.videoCategories, vc)
                    .where(vc.categoryId.eq(request.getCategoryId()));
            countQuery.join(video.videoCategories, vc)
                    .where(vc.categoryId.eq(request.getCategoryId()));
        }

        contentQuery.where(builder);
        countQuery.where(builder);

        String sortBy = request.getSortBy() == null ? "purchaseDate" : request.getSortBy();
        boolean asc = "asc".equalsIgnoreCase(request.getSortOrder());
        OrderSpecifier<?> order;
        if ("title".equals(sortBy)) {
            order = asc ? video.title.asc() : video.title.desc();
        } else {
            order = asc ? userVideo.purchasedAt.asc() : userVideo.purchasedAt.desc();
        }

        List<UserVideoResponse> contents = contentQuery
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }


}
