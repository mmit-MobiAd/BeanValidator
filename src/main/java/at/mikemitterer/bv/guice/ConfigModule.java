package at.mikemitterer.bv.guice;

import at.mikemitterer.bv.SimpleCalc;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigModule extends AbstractModule {
    static Logger logger = LoggerFactory.getLogger(ConfigModule.class);

    @Override
    protected void configure() {
        logger.debug("Guice configuration starts here...");

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
