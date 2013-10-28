package at.mikemitterer.bv;

import at.mikemitterer.bv.guice.TestModule;
import at.mikemitterer.bv.guice.annotations.StringSample;
import at.mikemitterer.bv.tdd.GuiceJUnitRunner;
import com.google.inject.Inject;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:37
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules(TestModule.class)
public class SimpleGuiceTest {
    private static Logger logger = LoggerFactory.getLogger(SimpleGuiceTest.class);

    @Inject
    @StringSample
    String value;

    @Test
    public void testString() throws Exception {
        Assert.assertEquals("Test", value);
    }

    @Test
    public void testUpperCase() throws Exception {
        Assert.assertEquals("tEST", WordUtils.swapCase(value));
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
