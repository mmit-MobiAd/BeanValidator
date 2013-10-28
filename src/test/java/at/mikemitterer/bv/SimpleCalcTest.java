package at.mikemitterer.bv;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:37
 */
public class SimpleCalcTest extends Assert {
    private static Logger logger = LoggerFactory.getLogger(SimpleCalcTest.class);

    @Test
    public void testGetResult() throws Exception {
        final SimpleCalc simpleCalc = new SimpleCalc();
        Assert.assertEquals(20, simpleCalc.getResult(10, 10));
        logger.debug("Result: {}", simpleCalc.getResult(10, 10));
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
