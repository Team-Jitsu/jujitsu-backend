package com.fightingkorea.platform.domain.user.repository.Impl;

import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.QUser;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.user.repository.UserRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> searchUsers(String nickname, Sex sex, LocalDateTime fromDate,
                                  LocalDateTime toDate, Pageable pageable) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();



/**
 * containsIgnoreCase() = 괄호안에 든 문자열을 대소문자 구분 없이 조회하라.
 */
        if (nickname != null && !nickname.isEmpty()){
            builder.and(qUser.nickname.containsIgnoreCase(nickname));
        }

        if (sex != null) {
            builder.and(qUser.sex.eq(sex));
        }

        if (fromDate != null) {
            builder.and(qUser.createdAt.goe(fromDate));
        }

        if (toDate != null ) {
            builder.and(qUser.createdAt.loe(toDate));
        }

        List<User> content = queryFactory
                .selectFrom(qUser)
                .where(builder) //BooleanBuilder에 들어있는 조건들 적용
                .offset(pageable.getOffset()) // 몇 번째부터 가져올지 (0부터시작)
                .limit(pageable.getPageSize()) // 몇 개 가져올지 (한 페이지의 크기)
                .fetch(); // 결과 리스트로 가져오기


/**
 *  페이지에 보여줄 전체 항목 수를 구하기 위한 쿼리
 */
        long total = queryFactory
                .select(qUser.count()) // 전체 개수 카운트
                .from(qUser)
                .where(builder) // 같은 조건 사용
                .fetchOne(); // 총 갯수 = 1개


/**
 * 스프링에서 사용하는 Page<T>객체를 직접 만들어서 반환.
 * content : 실제 데이터 리스트(List<User>)
 * pageable : 요청에서 받은 페이지 정보 (page, size, sort 등)
 * total : 전체 데이터 개수
 */
        return new PageImpl<>(content, pageable, total);


    }
}
