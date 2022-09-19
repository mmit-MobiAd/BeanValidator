package at.mikemitterer.bv;

import at.mikemitterer.bv.guice.TestModule;
import at.mikemitterer.bv.guice.annotations.StringSample;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:37
 */
public class SimpleGuiceTest {
    private static Logger logger = LoggerFactory.getLogger(SimpleGuiceTest.class);

    private static final Injector injector = Guice.createInjector(new TestModule());

    @Inject
    @StringSample
    String value;

    @BeforeEach
    public void setUp() throws Exception {
        injector.injectMembers(this);
    }

    @Test
    public void testString() throws Exception {
        assertEquals("Test", value);
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
