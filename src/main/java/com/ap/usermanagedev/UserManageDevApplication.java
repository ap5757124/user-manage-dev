package com.ap.usermanagedev;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ap.usermanagedev.mapper")
public class UserManageDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManageDevApplication.class, args);
    }

}
