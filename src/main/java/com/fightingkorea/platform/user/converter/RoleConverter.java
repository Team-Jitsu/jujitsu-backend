package com.fightingkorea.platform.user.converter;

import com.fightingkorea.platform.user.domain.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA에서 Role enum을 DB에 저장하거나 읽을 때 문자열(label)로 변환해주는 컨버터.
 * DB 저장 시: Role → String(label)
 * DB 조회 시: String(label) → Role
 */
@Converter(autoApply = false) // 자동 적용하지 않고 @Convert를 명시한 필드에만 적용
public class RoleConverter implements AttributeConverter<Role, String> {

    /**
     * 엔티티 필드(Role enum)를 DB에 저장할 문자열(label)로 변환
     * 예: Role.ADMIN → "관리자"
     *
     * @param role Role enum 값
     * @return DB에 저장할 문자열 (label)
     */
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role != null ? role.getLabel() : null;
    }

    /**
     * DB에서 읽어온 문자열(label)을 Role enum으로 변환
     * 예: "관리자" → Role.ADMIN
     *
     * @param dbData DB에서 조회된 문자열
     * @return 매칭되는 Role enum
     */
    @Override
    public Role convertToEntityAttribute(String dbData) {
        return dbData != null ? Role.fromLabel(dbData) : null;
    }
}
