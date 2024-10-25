package edu.icet.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.bo.custom.CustomerBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.CustomerDao;
import edu.icet.entity.CustomerEntity;
import edu.icet.entity.ItemEntity;
import edu.icet.model.Customer;
import edu.icet.model.Item;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDaoImpl= DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public String generateCustomerId() {
        String lastCustomerId = customerDaoImpl.getLatestId();
        if (lastCustomerId==null){
            return "C0001";
        }
        int number = Integer.parseInt(lastCustomerId.split("C")[1]);
        number++;
        return String.format("C%04d", number);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public boolean insertUser(Customer customer) {
        CustomerEntity userEntity = new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDaoImpl.save(userEntity);
    }

    @Override
    public ObservableList getAllUsers() {

        ObservableList<CustomerEntity> list = customerDaoImpl.getAll();
        ObservableList<Customer> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Customer.class));
        });
        return userList;
    }
    @Override
    public boolean updateUser(Customer customer) {
        CustomerEntity userEntity = new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDaoImpl.update(userEntity);
    }
    @Override
    public boolean deleteUserById(String text) {
        return customerDaoImpl.delete(text);
    }

    @Override
    public Customer searchUserByName(String name) {
        CustomerEntity customerEntity = customerDaoImpl.searchByName(name);
        return new ObjectMapper().convertValue(customerEntity,Customer.class);
    }

    @Override
    public Customer searchItemByID(String id) {
        CustomerEntity customerEntity = customerDaoImpl.search(id);
        return new ObjectMapper().convertValue(customerEntity, Customer.class);
    }
}
