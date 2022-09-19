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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:37
 */
public class SimpleMockTest {
    private static Logger logger = LoggerFactory.getLogger(SimpleMockTest.class);

    private static final Injector injector = Guice.createInjector(new TestModule());

    @Inject
    @StringSample
    String value;

    @BeforeEach
    public void setUp() throws Exception {
        injector.injectMembers(this);
    }

    @Test
    public void testMock() throws Exception {
        final SimpleCalc mockSimpleCalc = mock(SimpleCalc.class);

        when(mockSimpleCalc.getResult(10, 10)).thenReturn(5);
        assertEquals(5, mockSimpleCalc.getResult(10, 10));
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
