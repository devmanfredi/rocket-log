package com.rocketlog.builders;

import com.rocketlog.model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
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
                .createdDate(LocalDateTime.now())
                .build();
        return builder;
    }

    public static UserBuilder comum(String name) {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .email("comum@comum.com")
                .fullName(name)
                .password(builder.bCrypt.encode(("comum")))
                .createdDate(LocalDateTime.now())
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
                .createdDate(LocalDateTime.now())
                .build();
        return builder;
    }

    public static UserBuilder adminComId(String fullName) {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .id(UUID.randomUUID())
                .email("admin@admin.com")
                .fullName(fullName)
                .password(builder.bCrypt.encode(("admin")))
                .createdDate(LocalDateTime.now())
                .build();
        return builder;
    }

    public User build() {
        return user;
    }

}
