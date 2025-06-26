package com.fightingkorea.platform.domain.user.config;

/**
 * 권한 있는 사람만 메소드를 실행 가능하게 합니다.
 * 권한 :
 *
 * @HasROle({"ROLE_ADMIN", "ROLE_OWNER"})
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasRole {
     String[] value();
}
