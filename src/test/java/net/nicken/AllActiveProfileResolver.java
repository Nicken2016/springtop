package net.nicken;

import org.springframework.test.context.ActiveProfilesResolver;

public class AllActiveProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.DB_IMPLEMENTATION, Profiles.getActiveDbProfile()};
    }
}
