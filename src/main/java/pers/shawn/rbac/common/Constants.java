package pers.shawn.rbac.common;

import java.time.Duration;

/**
 * @author shawn
 * @create 2020/8/18 9:59
 * @desc
 **/
public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final Duration TOKEN_EXPIRE_TIME = Duration.ofMinutes(30);

    public static final String REDIS_HASH_PREFIX = "hash_";

    public static final String REDIS_STR_PREFIX = "str_";

    public static final Integer RESOURCE_TYPE_PAGE = 0;

    public static final Integer RESOURCE_TYPE_API = 1;

}
