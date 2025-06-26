package com.fightingkorea.platform.global;

public class UserThreadLocal {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // theradLocal의 userId 불러옴
    public static Long getUserId() {
        return threadLocal.get();
    }

    // threadLocal에 userId 저장
    public static void setUserId(Long userId) {
        threadLocal.set(userId);
    }
}
