package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.Customer;
import edu.icet.model.Employee;
import javafx.collections.ObservableList;

public interface EmployeeBo extends SuperBo {
    String generateEmployeeId();
    boolean insertUser(Employee employee);
    ObservableList getAllUsers();
    boolean updateUser(Employee employee);
    boolean deleteUserById(String text);
    Employee searchUserByName(String name);
    Employee searchUserByEmail(String email);
}
