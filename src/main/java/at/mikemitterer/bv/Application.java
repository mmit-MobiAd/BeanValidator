package at.mikemitterer.bv;

import at.mikemitterer.bv.guice.ConfigModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikemitterer, Date: 09.08.13, Time: 12:28
 */
public class Application {

    public static void main(String args[]) {
        final Injector injector = Guice.createInjector(new ConfigModule());
        final Main main = injector.getInstance(Main.class);

        main.run(args);
    }

    //---------------------------------------------------------------------------------------------
    // Application
    //---------------------------------------------------------------------------------------------

    private static class Main {
        static Logger logger = LoggerFactory.getLogger(Application.class.getSimpleName());

        private final SimpleCalc simpleCalc;

        @Inject
        private Main(final SimpleCalc simpleCalc) {
            this.simpleCalc = simpleCalc;
        }

        void run(final String[] args) {
            logger.debug("Calc: 10 + 10 = {}", simpleCalc.getResult(10, 10));
        }

    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
