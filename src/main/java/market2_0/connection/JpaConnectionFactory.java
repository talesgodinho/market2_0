package market2_0.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaConnectionFactory {

	private EntityManagerFactory factory;

	public JpaConnectionFactory() {
		this.factory = Persistence.createEntityManagerFactory("market2_0");
	}

	public EntityManager getEntityManager() {
		return this.factory.createEntityManager();
	}

}
