package com.fightingkorea.platform.domain.earning.service;


import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EarningService {

    EarningResponse createEarning(Long trainerId);

    // 트레이너가 정산 요청을 누름
    void settleEarningsByTrainer(Long trainerId);

    // admin이 확인 버튼 누름
    void settleEarningsByAdmin(Long trainerId);

    // 트레이너 or admin이 trainer의 정산 요청 상세 리스트 알고자 할때
    Page<EarningResponse> getEarningList(Long trainerId, Pageable pageable);

}