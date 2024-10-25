package edu.icet.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.bo.custom.SupplierBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.SupplierDao;
import edu.icet.entity.CustomerEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.model.Customer;
import edu.icet.model.Supplier;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierBoImpl implements SupplierBo {

    SupplierDao supplierDao= DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public String generateSupplierId() {
        String lastSupplierId = supplierDao.getLatestId();
        if (lastSupplierId==null){
            return "S0001";
        }
        int number = Integer.parseInt(lastSupplierId.split("S")[1]);
        number++;
        return String.format("S%04d", number);
    }

    @Override
    public boolean insertUser(Supplier supplier) {
        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.save(supplierEntity);
    }

    @Override
    public ObservableList getAllUsers() {
        ObservableList<SupplierEntity> list = supplierDao.getAll();
        ObservableList<Supplier> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Supplier.class));
        });
        return userList;
    }

    @Override
    public boolean updateUser(Supplier supplier) {
        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.update(supplierEntity);
    }

    @Override
    public boolean deleteUserById(String text) {
        return supplierDao.delete(text);
    }

    @Override
    public Supplier searchUserByName(String name) {
        SupplierEntity supplierEntity = supplierDao.searchByName(name);
        return new ObjectMapper().convertValue(supplierEntity,Supplier.class);
    }

    @Override
    public Supplier searchSupplierByID(String id) {
        SupplierEntity supplierEntity = supplierDao.search(id);
        return new ObjectMapper().convertValue(supplierEntity,Supplier.class);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
