package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.admin.dto.AdminVideoSearchRequest;
import com.fightingkorea.platform.domain.video.entity.QVideo;
import com.fightingkorea.platform.domain.video.entity.QVideoCategory;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.entity.QUserVideo;
import com.fightingkorea.platform.domain.trainer.entity.QTrainer;
import com.fightingkorea.platform.domain.user.entity.QUser;
import com.fightingkorea.platform.domain.video.entity.QCategory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class VideoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public JPAQuery<Video> createAdminVideoQuery(AdminVideoSearchRequest request) {
        QVideo video = QVideo.video;
        QTrainer trainer = QTrainer.trainer;
        QUser user = QUser.user;
        QVideoCategory videoCategory = QVideoCategory.videoCategory;
        QCategory category = QCategory.category;
        QUserVideo userVideo = QUserVideo.userVideo;

        JPAQuery<Video> query = queryFactory.selectFrom(video)
                .leftJoin(video.trainer, trainer)
                .leftJoin(trainer.user, user)
                .leftJoin(video.videoCategories, videoCategory)
                .leftJoin(videoCategory.category, category)
                .leftJoin(userVideo).on(userVideo.video.eq(video))
                .groupBy(video.videoId);

        if (StringUtils.hasText(request.getSearchTerm())) {
            query.where(video.title.containsIgnoreCase(request.getSearchTerm())
                    .or(user.nickname.containsIgnoreCase(request.getSearchTerm())));
        }

        if (request.getCategoryId() != null) {
            query.where(category.categoryId.eq(request.getCategoryId()));
        }

        if ("active".equals(request.getStatus())) {
            query.having(userVideo.count().gt(0));
        } else if ("inactive".equals(request.getStatus())) {
            query.having(userVideo.count().eq(0));
        }

        switch (request.getSortBy()) {
            case "popularity":
                query.orderBy(userVideo.count().desc());
                break;
            case "price":
                if ("asc".equalsIgnoreCase(request.getSortOrder())) {
                    query.orderBy(video.price.asc());
                } else {
                    query.orderBy(video.price.desc());
                }
                break;
            case "title":
                if ("asc".equalsIgnoreCase(request.getSortOrder())) {
                    query.orderBy(video.title.asc());
                } else {
                    query.orderBy(video.title.desc());
                }
                break;
            default:
                query.orderBy(video.uploadTime.desc());
        }

        return query;
    }
}
