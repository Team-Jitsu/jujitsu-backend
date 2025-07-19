package com.fightingkorea.platform.global;

import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TrainerService trainerService;
    private final EarningService earningService;

    /**
     * 전체 유저 목록을 조회하는 메서드.
     *
     * @param name
     * @param sex
     * @param fromDate
     * @param toDate
     * @param pageable
     * @return
     */
    @GetMapping("/users")
    public Page<User> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Sex sex,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable) {
        return userService.getUsers(name, sex, role, fromDate, toDate, pageable);
    }


    /**
     * 특정 유저의 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return
     */
    @GetMapping("/users/{user-id}")
    public UserResponse getUserInfo(@PathVariable("user-id") Long userId) {
        UserResponse userResponse = userService.getUserInfo(userId);
        return userResponse;
    }

    /**
     * 관리자가 특정 유저의 상태를 변경합니다.(활성화/비활성화)
     *
     * @param userId
     * @param isActive
     * @return
     */
    @PutMapping("/users/{user-id}")
    public UserResponse updateUserActive(@PathVariable("user-id") Long userId, @RequestParam Boolean isActive) {
        UserResponse userResponse = userService.updateUserActive(userId, isActive);

        return userResponse;
    }

    // 트레이너 목록 조회 (페이징)
    @GetMapping("/trainers")
    public PageImpl<TrainerResponse> getTrainers(Pageable pageable) {

        return trainerService.getTrainers(pageable);
    }

    // 1) 트레이너가 출금 신청한 건 지급해주는 로직
    // 2) 매월 일정한 날짜에 earningBuffer-> earning으로 옮겨서 지급해주는 로직

    // 정산
    @PostMapping("/settle")
    public void settleEarningsByAdmin(@RequestBody SettleRequest req) {
        earningService.settleEarningsByAdmin(req, );
    }



}
