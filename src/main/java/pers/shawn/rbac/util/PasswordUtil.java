package pers.shawn.rbac.util;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

/**
 * @author shawn
 * @create 2020/8/19 15:54
 * @desc 加密相关工具类
 **/
public class PasswordUtil {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[8];
        random.nextBytes(bytes);
        return Base64.encodeBase64String(bytes);
    }

}
