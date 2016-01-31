package pl.googleappengine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Maciek
 * Caches entity manager factories
 */
public class EntityManagerFactoryCache {
	
	private static EntityManagerFactory emf = null;
	
	private EntityManagerFactoryCache() {}
	
	public static synchronized EntityManagerFactory getInstance() {
		if( emf == null ) {
			emf = Persistence.createEntityManagerFactory("default");
		}
		
		return emf;
	}

}
