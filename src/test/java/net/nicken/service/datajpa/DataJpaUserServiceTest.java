package net.nicken.service.datajpa;

import net.nicken.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static net.nicken.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest{
}
