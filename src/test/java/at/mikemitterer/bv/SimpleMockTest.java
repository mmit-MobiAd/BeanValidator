package at.mikemitterer.bv;

import at.mikemitterer.bv.guice.TestModule;
import at.mikemitterer.bv.guice.annotations.StringSample;
import at.mikemitterer.bv.tdd.GuiceJUnitRunner;
import com.google.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:37
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules(TestModule.class)
public class SimpleMockTest {
    private static Logger logger = LoggerFactory.getLogger(SimpleMockTest.class);

    @Inject
    @StringSample
    String value;

    @Test
    public void testMock() throws Exception {
        final SimpleCalc mockSimpleCalc = mock(SimpleCalc.class);

        when(mockSimpleCalc.getResult(10, 10)).thenReturn(5);
        Assert.assertEquals(5, mockSimpleCalc.getResult(10, 10));
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
