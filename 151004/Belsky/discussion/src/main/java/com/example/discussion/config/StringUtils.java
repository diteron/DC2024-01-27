package com.example.discussion.config;

import java.util.Optional;

public class StringUtils {
    public static String valueOf(Object object) {
        return Optional.ofNullable(object)
                .map(Object::toString)
                .orElse(null);
    }
}
