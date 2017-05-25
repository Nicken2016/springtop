package net.nicken.service.jdbc;

import net.nicken.service.AbstractMealServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static net.nicken.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest{
}
