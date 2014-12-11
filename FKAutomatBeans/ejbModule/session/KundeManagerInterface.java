package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import model.Kunde;

@Remote
public interface KundeManagerInterface {

	public Collection<Kunde> list();

	public Collection<Kunde> findByKid(long id);

	public void delete(long primaryKey) throws NoSuchKunde;

	public void save(Kunde k);

	@Remove
	void checkout();

	public void insert(Kunde kunde);
}
