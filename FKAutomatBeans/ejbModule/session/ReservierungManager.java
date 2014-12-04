package session;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.*;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Reservierung;


@Stateful
@Remote(ReservierungManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservierungManager implements java.io.Serializable {
	private static final long serialVersionUID = -2900702404043525937L;

	@PersistenceContext(unitName = "tempdb")
	private EntityManager em;
	private Reservierung Reservierung;

	public Collection<Reservierung> list() {
		Query query = em.createQuery("SELECT r FROM Reservierung r");
		Collection<Reservierung> ReservierungCollection = new ArrayList<Reservierung>();
		for (Reservierung Reservierung : (ArrayList<Reservierung>) query.getResultList()) 
			ReservierungCollection.add(Reservierung);
		return ReservierungCollection;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Reservierung findByPrimaryKey(long primaryKey) throws NoSuchReservierung {
		Reservierung Reservierung = em.find(Reservierung.class, primaryKey);
		if (Reservierung == null)
			throw new NoSuchReservierung();
		else
			return Reservierung;
	}


	public Collection<Reservierung> findByDescription(String description) {
		Query query = em.createNamedQuery("Reservierung.findByDescription");
		query.setParameter("description", description);
		Collection<Reservierung> ReservierungCollection = new ArrayList<Reservierung>();
		for (Reservierung Reservierung : (ArrayList<Reservierung>) query
				.getResultList())
			ReservierungCollection.add(Reservierung);
		return ReservierungCollection;
	}

	public void delete(int primaryKey) throws NoSuchReservierung {
		Reservierung Reservierung = em.find(Reservierung.class, primaryKey);
		if (Reservierung != null)
			em.remove(Reservierung);
		else
			throw new NoSuchReservierung();
	}

	// Bemerkung:
	// Der nachfolgend implementierte Umweg ist "nur" deshalb noetig, weil
	// "JBoss"
	// nicht in der Lage ist, mehrere Beziehungen des Typs "EAGER" aufzubauen
	// und
	// deshalb die Exception mit Fehlermeldung ...
	// "org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags"
	// ... produziert (siehe auch Bean "Reservierung").
	// Deshalb muss man sich dazu entscheiden, eine (oder mehrere) Beziehungen
	// von "EAGER" auf "LAZY"
	// zu vereinbaren (Vorsicht: der FetchType von OneToMany ist per Default auf
	// "EAGER" gesetzt!).
	// Dies zieht dann nachfolgende Implementierung nach sich, die anstelle der
	// automatisch bei EAGER
	// erfolgenden Datenbereitstellung explizit "ausprogrammiert" werden muss,
	// beispielsweise eben
	// wie folgt:
	//
//	public Collection<Employee> getEmployees(Reservierung dept) {
//		Query query = em
//				.createQuery("SELECT e FROM Employee e WHERE e.Reservierung = "
//						+ dept.getAbtnr());
//		Collection<Employee> employeeCollection = new ArrayList<Employee>();
//		for (Employee employee : (ArrayList<Employee>) query.getResultList())
//			employeeCollection.add(employee);
//		return employeeCollection;
//	}

	public void save(Reservierung r) {
		//
		// Vorsicht:
		//
		// EntityTransaction tx = em.getTransaction();
		// tx.begin();
		// Reservierung Reservierung = em.find(Reservierung.class, p.getDeptNo());
		// if (Reservierung != null) {
		// em.merge(p);
		// } else
		// em.persist(p);
		// tx.commit();
		//
		// ... aus Hibernate-Implementierung wird zu:
		//
		Reservierung Reservierung = em.find(Reservierung.class, r.getRid());
		if (Reservierung != null) {
			em.merge(r);
		} else
			em.persist(r);
	}

	@Remove
	public void checkout() {
		if (Reservierung != null)
			em.persist(Reservierung);
	}

}
