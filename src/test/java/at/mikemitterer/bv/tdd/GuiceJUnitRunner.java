package at.mikemitterer.bv.tdd;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.*;

/**
 * @see <a href="http://goo.gl/2N8tX" target="_blank">JUnit tests made easy with Guice </a>
 * @author Mike Mitterer (office@mikemitterer.at)
 * 
 */
public class GuiceJUnitRunner extends BlockJUnit4ClassRunner {
	private final Injector	injector;

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	public @interface GuiceModules {
		Class<?>[] value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
	 */
	@Override
	public Object createTest() throws Exception {
		final Object obj = super.createTest();
		injector.injectMembers(obj);
		return obj;
	}

	/**
	 * Instances a new JUnit runner.
	 * 
	 * @param klass
	 *            The test class
	 * @throws InitializationError
	 */
	public GuiceJUnitRunner(final Class<?> klass) throws InitializationError {
		super(klass);
		final Class<?>[] classes = getModulesFor(klass);
		injector = createInjectorFor(classes);
	}

	private Injector createInjectorFor(final Class<?>[] classes) throws InitializationError {
		final Module[] modules = new Module[classes.length];
		for (int i = 0; i < classes.length; i++) {
			try {
				modules[i] = (Module) (classes[i]).newInstance();
			}
			catch (final InstantiationException e) {
				throw new InitializationError(e);
			}
			catch (final IllegalAccessException e) {
				throw new InitializationError(e);
			}
		}
		return Guice.createInjector(modules);
	}

	/**
	 * Gets the Guice modules for the given test class.
	 * 
	 * @param klass
	 *            The test class
	 * @return The array of Guice {@link Module} modules used to initialize the
	 *         injector for the given test.
	 * @throws InitializationError
	 */
	private Class<?>[] getModulesFor(final Class<?> klass) throws InitializationError {
		final GuiceModules annotation = klass.getAnnotation(GuiceModules.class);
		if (annotation == null)
			throw new InitializationError(
					"Missing @GuiceModules annotation for unit test '" + klass.getName()
							+ "'");
		return annotation.value();
	}

}