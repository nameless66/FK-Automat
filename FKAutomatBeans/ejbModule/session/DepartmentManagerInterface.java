package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import bean.Department;
import bean.Employee;

@Remote
public interface DepartmentManagerInterface {

   public Collection<Department> list();

   public Department findByPrimaryKey(int primaryKey) throws NoSuchDepartment;

   public Collection<Department> findByDescription(String description);

   public void delete(int primaryKey) throws NoSuchDepartment;

   public void save(Department p);

   public Collection<Employee> getEmployees(Department dept);

   @Remove
   void checkout();
}
