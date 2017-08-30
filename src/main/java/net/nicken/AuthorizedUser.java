package net.nicken;

import net.nicken.model.BaseEntity;
import net.nicken.util.UserUtil;

import java.util.Set;

public class AuthorizedUser {
    public static int id = BaseEntity.START_SEQ;

    public static int id(){
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getCaloriesPerDay(){
        return UserUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
