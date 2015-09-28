package changkon.imj.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Main application class responsible for running Internet Movie Journal
 * @author Chang Kon Han
 */
@ApplicationPath("/services")
public class IMJApplication extends Application {

	public static String PERSISTENCEUNIT = "IMJ";
	public static String BASEURI = "http://localhost:10003/services";
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> components = new HashSet<Class<?>>();
	
	public IMJApplication() {
		singletons.add(new ViewerResource());
		singletons.add(new MovieResource());
		singletons.add(new OMDBResource());
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
