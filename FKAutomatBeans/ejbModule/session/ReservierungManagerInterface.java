package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import model.Reservierung;
import model.Strecke;

@Remote
public interface ReservierungManagerInterface {

   public Collection<Reservierung> list();

   public Reservierung findByPrimaryKey(int primaryKey) throws NoSuchReservierung;

   public Collection<Reservierung> findByDescription(String description);

   public void delete(int primaryKey) throws NoSuchReservierung;

   public void save(Reservierung p);



   @Remove
   void checkout();
}
