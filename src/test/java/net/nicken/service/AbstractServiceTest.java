package net.nicken.service;

import net.nicken.ActiveDbProfileResolver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.instanceOf;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceTest.class);

    private static StringBuffer results = new StringBuffer();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @AfterClass

    public static void printResult() {

        LOG.info("\n---------------------------------" +

                "\nTest                 Duration, ms" +

                "\n---------------------------------\n" +

                results +

                "---------------------------------\n");

        results.setLength(0);
    }

    public static <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass){
        try{
            runnable.run();
            Assert.fail("Expected "+ exceptionClass.getName());
        }catch (Exception e){
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }

    public static Throwable getRootCause(Throwable t){
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)){
            result = cause;
        }
    return result;
    }
}