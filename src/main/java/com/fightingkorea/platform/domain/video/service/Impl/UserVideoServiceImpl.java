package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserVideoServiceImpl {

    private final UserVideoRepository userVideoRepository;


}
