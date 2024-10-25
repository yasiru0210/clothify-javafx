package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.model.Customer;
import edu.icet.model.Supplier;
import javafx.collections.ObservableList;

public interface SupplierBo extends SuperBo {
    String generateSupplierId();
    boolean insertUser(Supplier supplier);
    ObservableList getAllUsers();
    boolean updateUser(Supplier supplier);
    boolean deleteUserById(String text);
    Supplier searchUserByName(String name);
    Supplier searchSupplierByID(String id);
}
