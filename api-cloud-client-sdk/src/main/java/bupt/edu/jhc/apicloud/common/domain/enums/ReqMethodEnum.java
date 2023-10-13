package bupt.edu.jhc.apicloud.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 请求类型枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
@Getter
@AllArgsConstructor
public enum ReqMethodEnum {
    GET("GET", "GET 请求类型"),
    POST("POST", "POST 请求类型"),
    ;

    private final String value;
    private final String desc;

    private static final Map<String, ReqMethodEnum> cache;

    static {
        cache = Arrays.stream(ReqMethodEnum.values()).collect(Collectors.toMap(ReqMethodEnum::getValue, Function.identity()));
    }

    public static ReqMethodEnum of(String value) {
        return cache.get(value);
    }
}
