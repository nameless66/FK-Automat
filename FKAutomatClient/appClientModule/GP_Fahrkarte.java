
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Kunde;
import model.Reservierung;
import model.Strecke;
import session.*;

public class GP_Fahrkarte {
	public static void usage() {
		System.out.println("Usage: java Test alleAbteilungen");
		System.out.println("Usage: java Test alleMitarbeiter");
	}

	public static void main(String args[]) {
		// GP
		// Kunde meldet sich an
		// Kunde wählt Strecke
		// Kunde will reservieren
		// Fahrschein wird gespeichtert und ausgegeben
		if (args.length < -1)
			usage();
		else {
			InitialContext ctx;
			try {
				ctx = new InitialContext();

				Kunde kunde = new Kunde();
				kunde.setNachname("x");
				kunde.setVorname("y");

				KundeManagerInterface kmanager = (KundeManagerInterface) ctx
						.lookup("FKAutomatBeans/KundeManager!session.KundeManagerInterface");
				StreckeManagerInterface smanager = (StreckeManagerInterface) ctx
						.lookup("FKAutomatBeans/StreckeManager!session.StreckeManagerInterface");
				ReservierungManagerInterface rmanager = (ReservierungManagerInterface) ctx
						.lookup("FKAutomatBeans/ReservierungManager!session.ReservierungManagerInterface");


				System.out
						.println("Bitte loggen sie sich mit ihrer Kundennummer ein!");
				long primaryKey = 952;
				Kunde kundex = kmanager.findByPrimaryKey(primaryKey);
				System.out.println("Der Kunde " + kundex.getVorname() + " "
						+ kundex.getNachname() + " mit der Nummer "
						+ kundex.getKid() + " ist eingeloggt");
				System.out
						.println("Bitte geben sie an welche Strecke sie fahren möchten!");
				long streckekey = 2;
				Strecke streckex = smanager.findByPrimaryKey(streckekey);
				System.out.println("Sie haben die Strecke von:"
						+ streckex.getVon() + " Nach: " + streckex.getNach()
						+ " ausgewählt");
				System.out
						.println("Bitte warten sie einen Moment es wird geprüft ob noch Plätze vorhanden sind");

				if (streckex.getPlatz() > 0) {
					System.out.println("Es sind noch " + streckex.getPlatz()
							+ " Plaetze verfuegbar");
					Reservierung neueReservierung = new Reservierung();
					neueReservierung.setStrecke(streckex);
					rmanager.reservierungEinpflegen(neueReservierung);
					rmanager.checkout();
					smanager.checkout();
				} else {
					System.out
							.println("Es tut uns leid es sind keine Plaetze verfuegbar");
				}

				// if (args[0].equals("alleAbteilungen")) {
				// DepartmentManagerInterface manager =
				// (DepartmentManagerInterface) ctx
				// .lookup("FKAutomatBeans/DepartmentManager!session.DepartmentManagerInterface");
				// for (Department dept : (ArrayList<Department>)
				// manager.list())
				// System.out.println("Test.main:: dept = " + dept);
				// manager.checkout();
				// } else if (args[0].equals("alleMitarbeiter")) {
				// EmployeeManagerInterface manager = (EmployeeManagerInterface)
				// ctx
				// .lookup("FKAutomatBeans/EmployeeManager!session.EmployeeManagerInterface");
				// for (Employee o : (ArrayList<Employee>) manager.list())
				// System.out.println("Test.main:: o = " + o);
				// manager.checkout();
				// } else
				// usage();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (NoSuchStrecke e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
