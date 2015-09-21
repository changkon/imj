package changkon.imj.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import changkon.imj.converters.MovieConverter;

/**
 * Main application class responsible for running Internet Movie Journal
 * @author Chang Kon Han
 */
public class IMJApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> components = new HashSet<Class<?>>();
	
	public IMJApplication() {
		singletons.add(new ViewerResource());
		singletons.add(new MovieResource());
		components.add(MovieConverter.class);
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return components;
	}
}
