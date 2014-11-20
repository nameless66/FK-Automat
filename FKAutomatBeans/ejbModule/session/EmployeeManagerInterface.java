package session;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Remove;

import bean.Department;
import bean.Employee;

@Remote
public interface EmployeeManagerInterface {

   public Collection<Employee> list();

   public Department findByPrimaryKey(int primaryKey) throws NoSuchEmployee;

   public Collection<Employee> findByDescription(String description);

   public void delete(int primaryKey) throws NoSuchEmployee;

   public void save(Employee p);

   public Collection<Employee> getEmployees(Department dept);

   @Remove
   void checkout();
}
