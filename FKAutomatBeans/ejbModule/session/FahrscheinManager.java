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

import model.Fahrschein;
import model.Kunde;
import model.Strecke;

@Stateful
@Remote(FahrscheinManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FahrscheinManager implements java.io.Serializable {
	private static final long serialVersionUID = -2900702404043525937L;

	@PersistenceContext(unitName = "tempdb")
	private EntityManager em;
	private Fahrschein Fahrschein;
	private Collection<Fahrschein> fahrscheinList = new ArrayList<Fahrschein>();

	public Collection<Fahrschein> list() {
		Query query = em.createQuery("SELECT f FROM Fahrschein f");
		Collection<Fahrschein> FahrscheinCollection = new ArrayList<Fahrschein>();
		for (Fahrschein Fahrschein : (ArrayList<Fahrschein>) query.getResultList()) 
			FahrscheinCollection.add(Fahrschein);
		return FahrscheinCollection;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Fahrschein findByPrimaryKey(long primaryKey) throws NoSuchFahrschein {
		Fahrschein Fahrschein = em.find(Fahrschein.class, primaryKey);
		if (Fahrschein == null)
			throw new NoSuchFahrschein();
		else
			return Fahrschein;
	}

	public Collection<Fahrschein> findByStrecke(Strecke strecke) {
		Query query = em.createNamedQuery("Fahrschein.findByStrecke");
		query.setParameter("fk_strecke", strecke);
		Collection<Fahrschein> FahrscheinCollection = new ArrayList<Fahrschein>();
		for (Fahrschein Fahrschein : (ArrayList<Fahrschein>) query
				.getResultList())
			FahrscheinCollection.add(Fahrschein);
		return FahrscheinCollection;
	}

	public void delete(long primaryKey) throws NoSuchFahrschein {
		Fahrschein Fahrschein = em.find(Fahrschein.class, primaryKey);
		if (Fahrschein != null)
			em.remove(Fahrschein);
		else
			throw new NoSuchFahrschein();
	}

	

	public void save(Fahrschein f) {
		
		fahrscheinList.add(f);
	}

	@Remove
	public void checkout() {
		for ( Fahrschein f: fahrscheinList)
			em.persist(f);
	}

}
