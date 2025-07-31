package com.fightingkorea.platform.domain.earning.repository;

import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EarningBufferRepository extends JpaRepository<EarningBuffer, Long> {

    void deleteByUserVideo(UserVideo userVideo);

    // earning이 null인 리스트에서 파라미터 값으로 주어진 Trainer로 찾기
    List<EarningBuffer> findByTrainer(Long trainerId);

    List<EarningBuffer> findByTrainerAndEarningIsNotNull(Long trainerId);

    boolean existsByTrainerIdAndEarningIsNull(Long trainerId);

    // trainerId 기준, bufferId가 null이 아닌 것만 페이징 조회
    Page<EarningBuffer> findByTrainer(Long trainerId, Pageable pageable);
}