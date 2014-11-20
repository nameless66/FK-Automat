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

import bean.Department;
import bean.Employee;

@Stateful
@Remote(DepartmentManagerInterface.class)
// Die nachfolgende Injizierung des Transaktionsmanagements kann weggelassen
// werden, da der Wert "Container" den Default-Einstellung darstellt!
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepartmentManager implements java.io.Serializable {
	private static final long serialVersionUID = -2900702404043525937L;

	@PersistenceContext(unitName = "tempdb")
	private EntityManager em;
	private Department department;

	public Collection<Department> list() {
		Query query = em.createQuery("SELECT p FROM Department p");
		Collection<Department> departmentCollection = new ArrayList<Department>();
		for (Department department : (ArrayList<Department>) query.getResultList()) 
			departmentCollection.add(department);
		return departmentCollection;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Department findByPrimaryKey(int primaryKey) throws NoSuchDepartment {
		Department department = em.find(Department.class, primaryKey);
		if (department == null)
			throw new NoSuchDepartment();
		else
			return department;
	}

	public Collection<Department> findByDescription(String description) {
		Query query = em.createNamedQuery("Department.findByDescription");
		query.setParameter("description", description);
		Collection<Department> departmentCollection = new ArrayList<Department>();
		for (Department department : (ArrayList<Department>) query
				.getResultList())
			departmentCollection.add(department);
		return departmentCollection;
	}

	public void delete(int primaryKey) throws NoSuchDepartment {
		Department department = em.find(Department.class, primaryKey);
		if (department != null)
			em.remove(department);
		else
			throw new NoSuchDepartment();
	}

	// Bemerkung:
	// Der nachfolgend implementierte Umweg ist "nur" deshalb noetig, weil
	// "JBoss"
	// nicht in der Lage ist, mehrere Beziehungen des Typs "EAGER" aufzubauen
	// und
	// deshalb die Exception mit Fehlermeldung ...
	// "org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags"
	// ... produziert (siehe auch Bean "Department").
	// Deshalb muss man sich dazu entscheiden, eine (oder mehrere) Beziehungen
	// von "EAGER" auf "LAZY"
	// zu vereinbaren (Vorsicht: der FetchType von OneToMany ist per Default auf
	// "EAGER" gesetzt!).
	// Dies zieht dann nachfolgende Implementierung nach sich, die anstelle der
	// automatisch bei EAGER
	// erfolgenden Datenbereitstellung explizit "ausprogrammiert" werden muss,
	// beispielsweise eben
	// wie folgt:
	//
	public Collection<Employee> getEmployees(Department dept) {
		Query query = em
				.createQuery("SELECT e FROM Employee e WHERE e.department = "
						+ dept.getAbtnr());
		Collection<Employee> employeeCollection = new ArrayList<Employee>();
		for (Employee employee : (ArrayList<Employee>) query.getResultList())
			employeeCollection.add(employee);
		return employeeCollection;
	}

	public void save(Department d) {
		//
		// Vorsicht:
		//
		// EntityTransaction tx = em.getTransaction();
		// tx.begin();
		// Department department = em.find(Department.class, p.getDeptNo());
		// if (department != null) {
		// em.merge(p);
		// } else
		// em.persist(p);
		// tx.commit();
		//
		// ... aus Hibernate-Implementierung wird zu:
		//
		Department department = em.find(Department.class, d.getAbtnr());
		if (department != null) {
			em.merge(d);
		} else
			em.persist(d);
	}

	@Remove
	public void checkout() {
		if (department != null)
			em.persist(department);
	}

}
