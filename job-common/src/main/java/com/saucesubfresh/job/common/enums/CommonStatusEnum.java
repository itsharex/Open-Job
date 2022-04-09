package com.saucesubfresh.job.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.saucesubfresh.job.common.interfaces.CommonEnumConverter;
import com.saucesubfresh.job.common.interfaces.IntArrayValuable;
import com.saucesubfresh.job.common.interfaces.IntValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author lijunping on 2021/6/22
 */
@AllArgsConstructor
@Getter
public enum CommonStatusEnum implements IntArrayValuable, IntValuable {

    YES(1, "启用"),

    NO(0, "冻结"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonStatusEnum::getValue).toArray();

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static CommonStatusEnum of(Integer value) {
        for (CommonStatusEnum statusEnum : values()) {
            if (Objects.equals(statusEnum.getValue(), value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static class Converter implements CommonEnumConverter<CommonStatusEnum> {
        public CommonStatusEnum intToEnum(Integer value) {
            return CommonStatusEnum.of(value);
        }
    }
}
