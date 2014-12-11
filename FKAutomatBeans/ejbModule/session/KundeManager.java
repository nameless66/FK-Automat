package session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.*;

import model.Kunde;

import session.NoSuchKunde;

@Stateful
@Remote(KundeManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KundeManager implements java.io.Serializable {
	private static final long serialVersionUID = -3924792265192263673L;

	@PersistenceContext(unitName = "tempdb")
	private static EntityManager em;
	private Kunde kunde;
	private Collection<Kunde> kundeList = new ArrayList<Kunde>();

	public Collection<Kunde> list() {
		return em.createQuery("SELECT k FROM Kunde k").getResultList();
	}

	public Kunde findByPrimaryKey(long primaryKey) throws NoSuchKunde {
		Kunde kunde = em.find(Kunde.class, primaryKey);
		if (kunde == null)
			throw new NoSuchKunde();
		else
			return kunde;
	}

	public Collection<Kunde> findByKid(long id) {
		return em.createNamedQuery("Kunde.findByKid").setParameter("kid", id)
				.getResultList();
	}

	public void delete(long primaryKey) throws NoSuchKunde {
		Kunde kunde = em.find(Kunde.class, primaryKey);
		if (kunde != null)
			em.remove(kunde); // Loesche Kunde
		else
			throw new NoSuchKunde();
	}

	public void insert(Kunde k) {
//		em.flush();
//		em.persist(kunde);
		kundeList.add(k);
		
	}

	// Anstelle von "close()" implementiert:
	//
	@Remove
	public void checkout() {
		for ( Kunde k: kundeList)
			em.persist(k);
	}
}
