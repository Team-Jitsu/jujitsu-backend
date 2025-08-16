package com.fightingkorea.platform.domain.earning.repository;

import com.fightingkorea.platform.domain.admin.dto.AdminEarningSearchRequest;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.QEarning;
import com.fightingkorea.platform.domain.trainer.entity.QTrainer;
import com.fightingkorea.platform.domain.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class EarningQueryRepository {

    private final JPAQueryFactory queryFactory;

    public JPAQuery<Earning> createAdminEarningQuery(AdminEarningSearchRequest request) {
        QEarning earning = QEarning.earning;
        QTrainer trainer = QTrainer.trainer;
        QUser user = QUser.user;

        JPAQuery<Earning> query = queryFactory.selectFrom(earning)
                .leftJoin(earning.trainer, trainer)
                .leftJoin(trainer.user, user);

        if (StringUtils.hasText(request.getSearchTerm())) {
            query.where(user.nickname.containsIgnoreCase(request.getSearchTerm()));
        }

        if ("pending".equals(request.getStatus())) {
            query.where(earning.isSettled.eq(false));
        } else if ("settled".equals(request.getStatus())) {
            query.where(earning.isSettled.eq(true));
        }

        switch (request.getSortBy()) {
            case "amount":
                if ("asc".equalsIgnoreCase(request.getSortOrder())) {
                    query.orderBy(earning.totalAmount.asc());
                } else {
                    query.orderBy(earning.totalAmount.desc());
                }
                break;
            case "trainer":
                if ("asc".equalsIgnoreCase(request.getSortOrder())) {
                    query.orderBy(user.nickname.asc());
                } else {
                    query.orderBy(user.nickname.desc());
                }
                break;
            default:
                query.orderBy(earning.requestSettlementAt.desc());
        }

        return query;
    }
}
