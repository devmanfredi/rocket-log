package com.rocketlog.builders;

import com.rocketlog.model.entity.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;

public class UserBuilder {

    private BCryptPasswordEncoder bCrypt;
    private User user;

    public static UserBuilder admin() {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .email("admin@admin.com")
                .fullName("Admin")
                .password(builder.bCrypt.encode(("admin")))
                .build();
        return builder;
    }

    public User build(){
        return user;
    }

}
