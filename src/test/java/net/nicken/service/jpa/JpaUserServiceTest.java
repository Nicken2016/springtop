package net.nicken.service.jpa;

import net.nicken.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static net.nicken.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
