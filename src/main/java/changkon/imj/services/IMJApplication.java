package changkon.imj.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Main application class responsible for running Internet Movie Journal
 * @author Chang Kon Han
 */
public class IMJApplication extends Application {

	private Set<Class<?>> services = new HashSet<Class<?>>();
	
	public IMJApplication() {
		services.add(ViewerResource.class);
		services.add(MovieResource.class);
		services.add(LogResource.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return services;
	}
}
