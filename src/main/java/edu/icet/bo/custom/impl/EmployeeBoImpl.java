package edu.icet.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.bo.custom.EmployeeBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.EmployeeDao;
import edu.icet.entity.EmployeeEntity;
import edu.icet.model.Employee;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeBoImpl implements EmployeeBo {

    EmployeeDao employeeDaoImpl= DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public String generateEmployeeId() {
        String lastEmployeeId = employeeDaoImpl.getLatestId();
        if (lastEmployeeId==null){
            return "E0001";
        }
        int number = Integer.parseInt(lastEmployeeId.split("E")[1]);
        number++;
        return String.format("E%04d", number);
    }


    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public boolean insertUser(Employee employee) {
        EmployeeEntity userEntity = new ObjectMapper().convertValue(employee, EmployeeEntity.class);
        return employeeDaoImpl.save(userEntity);
    }

    @Override
    public ObservableList getAllUsers() {
        ObservableList<EmployeeEntity> list = employeeDaoImpl.getAll();
        ObservableList<Employee> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Employee.class));
        });
        return userList;
    }

    @Override
    public boolean updateUser(Employee employee) {
        EmployeeEntity userEntity = new ObjectMapper().convertValue(employee, EmployeeEntity.class);
        return employeeDaoImpl.update(userEntity);
    }

    @Override
    public boolean deleteUserById(String text) {
        return employeeDaoImpl.delete(text);
    }


    @Override
    public Employee searchUserByName(String name) {
        EmployeeEntity employeeEntity = employeeDaoImpl.searchByName(name);
        return new ObjectMapper().convertValue(employeeEntity,Employee.class);
    }

    @Override
    public Employee searchUserByEmail(String email) {
        EmployeeEntity employeeEntity = employeeDaoImpl.searchByEmail(email);
        return new ObjectMapper().convertValue(employeeEntity,Employee.class);
    }
}
