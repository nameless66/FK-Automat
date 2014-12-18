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
	private static EntityManager em;

	public Collection<Reservierung> list() {
		Query query = em.createQuery("SELECT r FROM Reservierung r");
		Collection<Reservierung> ReservierungCollection = new ArrayList<Reservierung>();
		for (Reservierung Reservierung : (ArrayList<Reservierung>) query
				.getResultList())
			ReservierungCollection.add(Reservierung);
		return ReservierungCollection;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Reservierung findByPrimaryKey(long primaryKey)
			throws NoSuchReservierung {
		Reservierung Reservierung = em.find(Reservierung.class, primaryKey);
		if (Reservierung == null)
			throw new NoSuchReservierung();
		else
			return Reservierung;
	}

	public static void reservierungEinpflegen(Reservierung r) {
		em.persist(r);
		StreckeManager.Platzabziehen(r.getStrecke().getSid());
	}

	public void delete(int primaryKey) throws NoSuchReservierung {
		Reservierung Reservierung = em.find(Reservierung.class, primaryKey);
		if (Reservierung != null)
			em.remove(Reservierung);
		else
			throw new NoSuchReservierung();
	}

	@Remove
	public void checkout() {
	}

}
