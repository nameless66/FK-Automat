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
	private static Collection<Strecke> streckeList = new ArrayList<Strecke>();

	public Collection<Strecke> list() {
		Query query = em.createQuery("SELECT s FROM Strecke s");
		Collection<Strecke> StreckeCollection = new ArrayList<Strecke>();
		for (Strecke Strecke : (ArrayList<Strecke>) query.getResultList())
			StreckeCollection.add(Strecke);
		return StreckeCollection;
	}

	// @TransactionAttribute(TransactionAttributeType.NEVER)
	public static Strecke findByPrimaryKey(long primaryKey)
			throws NoSuchStrecke {
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

	public static void Platzabziehen(long id) {
		try {
			Strecke s = findByPrimaryKey(id);
			s.setPlatz(s.getPlatz() - 1);
			System.out.println("Der Platz wurde abgezogen s = " + s);
		} catch (NoSuchStrecke e) {
			System.out
					.println("StreckeManager:: Exception NoSuchStrecke erkannt !!");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("-----------------------------------");
		}

	}

	public static void save(Strecke s) {
		streckeList.add(s);
	}

	@Remove
	public void checkout() {
	}

}
