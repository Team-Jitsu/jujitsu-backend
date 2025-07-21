package com.fightingkorea.platform.domain.video.repository.Impl;

import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.entity.QUserVideo;
import com.fightingkorea.platform.domain.video.entity.QVideo;
import com.fightingkorea.platform.domain.video.repository.CustomUserVideoRepository;
import com.querydsl.core.types.Projections;
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
    public Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable){

        List<UserVideoResponse> contents = queryFactory
                .select(Projections.constructor(UserVideoResponse.class,
                        video.videoId,
                        video.title,
                        userVideo.purchasePrice,
                        userVideo.purchasedAt
                ))
                .from(userVideo)
                .join(userVideo.video, video)
                .where(userVideo.user.userId.eq(userId))
                .orderBy(userVideo.purchasedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(userVideo.count())
                .from(userVideo)
                .where(userVideo.user.userId.eq(userId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }


}
