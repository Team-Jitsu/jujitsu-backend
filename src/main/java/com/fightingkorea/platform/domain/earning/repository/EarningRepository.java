package com.fightingkorea.platform.domain.earning.repository;

import com.fightingkorea.platform.domain.earning.entity.Earning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarningRepository extends JpaRepository<Earning, Long> {

}
