import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Kunde;
import session.*;

public class GP_Kunde {
   public static void usage() {
      System.out.println("Usage: java Test alleAbteilungen");
      System.out.println("Usage: java Test alleMitarbeiter");
   }

   public static void main(String args[]) {
      // Bemerkung:
      // Wenn ein Client-Programm mit Main-Argumenten via Ant gestartet werden
      // soll, so muss dies ueber den Zugriff auf "System-Properties"
      // erfolgen.
      // Anstelle des String-Arrays "args" treten also "getProperty"-Aufrufe.
      // Die nachfolgenden Zeilen sind in diesem Fall zu "aktivieren"!

//      args = new String[8];
//      for (int i = 0; i < args.length; i++)
//         System.out.println("Test.main:: prop" + i + " = " + System.getProperty("prop" + i));
//      for (int i = 0; i < args.length; i++)
//         args[i] = System.getProperty("prop" + i);
//
      if (args.length < 1)
         usage();
      else {
         InitialContext ctx;
         try {
            ctx = new InitialContext();
            
            Kunde kunde = new Kunde();
    		kunde.setNachname("x");
    		kunde.setVorname("y");
    		
    		KundeManagerInterface manager = (KundeManagerInterface) ctx.lookup("FKAutomatBeans/KundeManager!session.KundeManagerInterface");
    		 		
    		
    		for (Kunde dept : (ArrayList<Kunde>) manager.list()) 
    			System.out.println("Test.main:: dept = " + dept);
    		
            
//            if (args[0].equals("alleAbteilungen")) {
//               DepartmentManagerInterface manager = (DepartmentManagerInterface) ctx
//                     .lookup("FKAutomatBeans/DepartmentManager!session.DepartmentManagerInterface");
//               for (Department dept : (ArrayList<Department>) manager.list()) 
//                  System.out.println("Test.main:: dept = " + dept);
//               manager.checkout();
//            } else if (args[0].equals("alleMitarbeiter")) {
//               EmployeeManagerInterface manager = (EmployeeManagerInterface) ctx
//                     .lookup("FKAutomatBeans/EmployeeManager!session.EmployeeManagerInterface");
//               for (Employee o : (ArrayList<Employee>) manager.list()) 
//                  System.out.println("Test.main:: o = " + o);
//               manager.checkout();
//            } else
//               usage();
         } catch (NamingException e) {
            e.printStackTrace();
         }
      }
   }
}
