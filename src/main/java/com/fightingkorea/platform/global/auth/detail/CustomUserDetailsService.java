package com.fightingkorea.platform.global.auth.detail;

import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userIdStr)) {
            throw new UsernameNotFoundException(String.format("%s not found.", userIdStr));
        }

        Long userId = Long.parseLong(userIdStr);

        com.fightingkorea.platform.domain.user.entity.User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found.", userId)));

        Long trainerId = null;
        if (Role.TRAINER == user.getRole()) {
            trainerId = trainerRepository.findTrainerIdByUserId(userId)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found.", userId)));
        }

        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                trainerId
        );
    }
}
