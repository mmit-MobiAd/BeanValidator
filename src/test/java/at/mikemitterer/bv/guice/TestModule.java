package at.mikemitterer.bv.guice;

import at.mikemitterer.bv.SimpleCalc;
import at.mikemitterer.bv.guice.annotations.*;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestModule extends AbstractModule {
    static Logger logger = LoggerFactory.getLogger(TestModule.class);

    @Override
    protected void configure() {
        logger.debug("Guice configuration starts here...");

        bindConstant().annotatedWith(StringSample.class).to("Test");
        bind(SimpleCalc.class);

        logger.debug("Guice configuration - done!");
    }

/*
    @Provides
    @Singleton
    SimpleCalc providesSimpleCalc() {
        return new SimpleCalc();
    }
*/

}
