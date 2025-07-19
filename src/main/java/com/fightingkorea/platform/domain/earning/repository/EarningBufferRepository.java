package com.fightingkorea.platform.domain.earning.repository;

import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EarningBufferRepository extends JpaRepository<EarningBuffer, Long> {

    void deleteByUserVideo(UserVideo userVideo);

    // earning이 null인 리스트에서 파라미터 값으로 주어진 Trainer로 찾기
    List<EarningBuffer> findByTrainerAndEarningIsNull(Trainer trainer);
}
