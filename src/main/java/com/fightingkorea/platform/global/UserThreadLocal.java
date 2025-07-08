package com.fightingkorea.platform.global;

public class UserThreadLocal {
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<Long> trainerIdHolder = new ThreadLocal<>();

    public static Long getUserId() {
        return userIdHolder.get();
    }

    public static void setUserId(Long userId) {
        userIdHolder.set(userId);
    }

    public static Long getTrainerId() {
        return trainerIdHolder.get();
    }

    public static void setTrainerId(Long trainerId) {
        trainerIdHolder.set(trainerId);
    }

    public static void clear() {
        userIdHolder.remove();
        trainerIdHolder.remove();
    }
}

