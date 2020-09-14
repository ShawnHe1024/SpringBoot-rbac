package pers.shawn.rbac.util;

import pers.shawn.rbac.bean.LoginUser;

/**
 * @author shawn
 * @create 2020/8/20 12:14
 * @desc
 **/
public class ThreadUtil {

    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    private static final ThreadLocal<LoginUser> userHolder = new ThreadLocal<>();

    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    public static String getToken() {
        return tokenHolder.get();
    }

    public static void setUserHolder(LoginUser user) {
        userHolder.set(user);
    }

    public static LoginUser getUserHolder() {
        return userHolder.get();
    }

    public static void remove() {
        tokenHolder.remove();
        userHolder.remove();
    }

}
