package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import model.Fahrschein;
import model.Kunde;
import model.Strecke;

@Remote
public interface FahrscheinManagerInterface {

   public Collection<Fahrschein> list();

   public Fahrschein findByPrimaryKey(long primaryKey) throws NoSuchFahrschein;

   public Collection<Fahrschein> findByStrecke(Strecke s);

   public void delete(int primaryKey) throws NoSuchFahrschein;

   public void save(Fahrschein f);

   public Collection<Kunde> getKundes(Fahrschein fahr);

   @Remove
   void checkout();
}
