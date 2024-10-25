package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.Customer;
import edu.icet.model.Supplier;
import javafx.collections.ObservableList;

public interface CustomerBo extends SuperBo {
    String generateCustomerId();
    boolean insertUser(Customer customer);
    ObservableList getAllUsers();
    boolean updateUser(Customer customer);
    boolean deleteUserById(String text);
    Customer searchUserByName(String name);
    Customer searchItemByID(String id);
}
