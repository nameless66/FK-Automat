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
import model.Strecke;
/// muss noch geändert werden

@Stateful
@Remote(StreckeManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StreckeManager implements java.io.Serializable {
	private static final long serialVersionUID = -2900702404043525937L;

	@PersistenceContext(unitName = "tempdb")
	private static EntityManager em;
	private Strecke Strecke;
	private static Collection<Strecke> streckeList = new ArrayList<Strecke>();

	public Collection<Strecke> list() {
		Query query = em.createQuery("SELECT s FROM Strecke s");
		Collection<Strecke> StreckeCollection = new ArrayList<Strecke>();
		for (Strecke Strecke : (ArrayList<Strecke>) query.getResultList()) 
			StreckeCollection.add(Strecke);
		return StreckeCollection;
	}

	//@TransactionAttribute(TransactionAttributeType.NEVER)
	public static Strecke findByPrimaryKey(long primaryKey) throws NoSuchStrecke {
		Strecke Strecke = em.find(Strecke.class, primaryKey);
		if (Strecke == null)
			throw new NoSuchStrecke();
		else
			return Strecke;
	}




	public void delete(long primaryKey) throws NoSuchStrecke {
		Strecke Strecke = em.find(Strecke.class, primaryKey);
		if (Strecke != null)
			em.remove(Strecke);
		else
			throw new NoSuchStrecke();
	}

	// Bemerkung:
	// Der nachfolgend implementierte Umweg ist "nur" deshalb noetig, weil
	// "JBoss"
	// nicht in der Lage ist, mehrere Beziehungen des Typs "EAGER" aufzubauen
	// und
	// deshalb die Exception mit Fehlermeldung ...
	// "org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags"
	// ... produziert (siehe auch Bean "Strecke").
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
//	public Collection<Employee> getEmployees(Strecke dept) {
//		Query query = em
//				.createQuery("SELECT e FROM Employee e WHERE e.Strecke = "
//						+ dept.getAbtnr());
//		Collection<Employee> employeeCollection = new ArrayList<Employee>();
//		for (Employee employee : (ArrayList<Employee>) query.getResultList())
//			employeeCollection.add(employee);
//		return employeeCollection;
//	}

	
	
	public static void Platzabziehen(long id) {
	try {
		Strecke s = findByPrimaryKey(id);
		int platzanzahl = s.getPlatz();
		platzanzahl = platzanzahl -1;
		//Query query = em.createQuery("UPDATE STRECKE SET STRECKE.PLATZ =" + platzanzahl + " WHERE SID = " + s.getSid() + ";");
		s.setPlatz(platzanzahl);
		System.out.println("Der platz wurde abgezogen!");
		
	} catch (NoSuchStrecke e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	}
	
	
	
		//
		// Vorsicht:
		//
		// EntityTransaction tx = em.getTransaction();
		// tx.begin();
		// Strecke Strecke = em.find(Strecke.class, p.getDeptNo());
		// if (Strecke != null) {
		// em.merge(p);
		// } else
		// em.persist(p);
		// tx.commit();
		//
		// ... aus Hibernate-Implementierung wird zu:
		//
		public static void save(Strecke s) {
			
			streckeList.add(s);
		}

		@Remove
		public void checkout() {
			for ( Strecke s: streckeList)
				em.persist(s);
		}

}
