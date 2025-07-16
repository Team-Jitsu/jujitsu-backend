package com.fightingkorea.platform.domain.earning.repository;

import com.fightingkorea.platform.domain.earning.entitiy.EarningBuffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarningBufferRepository extends JpaRepository<EarningBuffer, Long> {
}
