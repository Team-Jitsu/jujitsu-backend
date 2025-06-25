package com.fightingkorea.platform;

import lombok.Getter;

public class UserThread {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // threadLocal에 userId 저장
    public static void setUserId(Long userId) {
        threadLocal.set(userId);
    }

    // theradLocal의 userId 불러옴
    public static Long getUserId() {
        return threadLocal.get();
    }
}
