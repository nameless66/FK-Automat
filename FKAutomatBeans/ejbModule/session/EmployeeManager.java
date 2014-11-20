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

import bean.Department;
import bean.Employee;
import session.NoSuchEmployee;
import session.NoSuchDepartment;

@Stateful
@Remote(EmployeeManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeManager implements java.io.Serializable {
	private static final long serialVersionUID = -3924792265192263673L;

	@PersistenceContext(unitName = "tempdb")
	private EntityManager em;
	private Employee employee;

	public Collection<Employee> list() {
		return em.createQuery("SELECT e FROM Employee e").getResultList();
	}

	// Hier eine Variante der Methode "list", die jede Instanz aus der Datenbank
	// liest - unabhaengig davon, ob bereits zuvor schon Instanzen eingelesen
	// wurden!
	public Collection<Employee> listRefresh() {
		Query query = em.createQuery("SELECT e FROM Employee e");
		for (Employee employee : (Collection<Employee>) query.getResultList())
			em.refresh(employee);
		return query.getResultList();
	}

	public Employee findByPrimaryKey(int primaryKey) throws NoSuchEmployee {
		Employee employee = em.find(Employee.class, primaryKey);
		if (employee == null)
			throw new NoSuchEmployee();
		else
			return employee;
	}

	public Collection<Employee> findByName(String name) {
		return em.createNamedQuery("Employee.findByName")
				.setParameter("name", name).getResultList();
	}

	public Collection<Employee> findByChrisname(String chrisname) {
		// alternative Zuweisung eines Parameters (siehe Klasse Employee.java)
		return em.createNamedQuery("Employee.findByChrisname")
				.setParameter(1, chrisname).getResultList();
	}

	public Collection<Employee> findEmployeeByProject(String description) {
		return em.createNamedQuery("Employee.findEmployeeByProject")
				.setParameter("description", description).getResultList();
	}

	public void delete(int primaryKey) throws NoSuchEmployee {
		Employee employee = em.find(Employee.class, primaryKey);
		if (employee != null)
			em.remove(employee); // Loesche Employee
		else
			throw new NoSuchEmployee();
	}

	// Anstelle von "close()" implementiert:
	//
	@Remove
	public void checkout() {
		if (employee != null)
			em.persist(employee);
	}
}
