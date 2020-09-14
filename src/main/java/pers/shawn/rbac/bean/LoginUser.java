package pers.shawn.rbac.bean;

import lombok.Data;

/**
 * @author shawn
 * @create 2020/8/18 15:45
 * @desc 登录用户信息
 **/
@Data
public class LoginUser {

    private Integer id;

    private String username;

    private String realname;

    private String position;

    private String phone;

    private String token;

}
