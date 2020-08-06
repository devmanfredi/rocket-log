package com.rocketlog.builders;

import com.rocketlog.model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    public static UserBuilder comum() {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .email("comum@comum.com")
                .fullName("Comum")
                .password(builder.bCrypt.encode(("comum")))
                .build();
        return builder;
    }

    public static UserBuilder rocketlog() {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .email("rocketlog@rocketlog.com")
                .fullName("Rocketlog")
                .password(builder.bCrypt.encode(("rocketlog")))
                .build();
        return builder;
    }

    public User build() {
        return user;
    }

}
