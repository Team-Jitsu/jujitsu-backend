package com.fightingkorea.platform.user.converter;

import com.fightingkorea.platform.user.domain.Sex;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA에서 Sex enum을 DB에 저장하거나 읽을 때 문자열(label)로 변환해주는 컨버터.
 * DB 저장 시: Sex → String(label)
 * DB 조회 시: String(label) → Sex
 */
@Converter(autoApply = false) // 자동 적용하지 않고 @Convert를 명시한 필드에만 적용
public class SexConverter implements AttributeConverter<Sex, String> {

    /**
     * 엔티티 필드(Sex enum)를 DB에 저장할 문자열(label)로 변환
     * 예: Sex.MALE → "남성"
     *
     * @param sex Sex enum 값
     * @return DB에 저장할 문자열 (label)
     */
    @Override
    public String convertToDatabaseColumn(Sex sex) {
        return sex != null ? sex.getLabel() : null;
    }

    /**
     * DB에서 읽어온 문자열(label)을 Sex enum으로 변환
     * 예: "남성" → Sex.MALE
     *
     * @param dbData DB에서 조회된 문자열
     * @return 매칭되는 Sex enum
     */
    @Override
    public Sex convertToEntityAttribute(String dbData) {
        return dbData != null ? Sex.fromLabel(dbData) : null;
    }
}
