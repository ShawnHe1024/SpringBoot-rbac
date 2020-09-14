package pers.shawn.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shawn
 * @create 2020/8/17 16:45
 * @desc 项目启动类
 **/
@SpringBootApplication
@MapperScan("pers.shawn.rbac.module.*.mapper")
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class);
    }

}
