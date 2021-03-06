package net.nicken;

import net.nicken.matcher.ModelMatcher;
import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static net.nicken.model.BaseEntity.START_SEQ;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", 2005, Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", 1900, Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
//                            && Objects.equals(expected.getRoles(), actual.getRoles())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    public static boolean comparePassword(String rawOrEncodedPassword, String password){
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)){
            return rawOrEncodedPassword.equals(password);
        }else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)){
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }


}
