package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import model.Reservierung;
import model.Strecke;

@Remote
public interface StreckeManagerInterface {

   public Collection<Strecke> list();

   public Strecke findByPrimaryKey(long primaryKey) throws NoSuchStrecke;

   //public Collection<Reservierung> findByDescription(String description);

   public void delete(long primaryKey) throws NoSuchStrecke;

   public void save(Strecke s);



   @Remove
   void checkout();
}
