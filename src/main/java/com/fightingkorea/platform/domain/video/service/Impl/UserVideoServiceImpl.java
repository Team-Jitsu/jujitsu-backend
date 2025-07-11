package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.repository.CustomUserVideoRepository;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.domain.video.service.UserVideoService;
import com.fightingkorea.platform.global.UserThreadLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserVideoServiceImpl implements UserVideoService {

    private final UserVideoRepository userVideoRepository;

    // 페이징된 특정 유저의 강의 구매 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable) {
        log.info("강의 구매 내역 조회 요청, 페이지 번호: {}, 페이지 크기: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<UserVideoResponse> result = userVideoRepository.getPurchasedVideoList(userId, pageable);

        log.info("강의 구매 내역조회 완료, 조회 수 : {}", result.getNumberOfElements());

        return result;
    }
}
