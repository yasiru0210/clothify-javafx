package edu.icet.dao.custom;

import edu.icet.dao.CrudDao;
import edu.icet.entity.EmployeeEntity;
import edu.icet.model.Employee;

public interface EmployeeDao extends CrudDao<EmployeeEntity,String> {
    EmployeeEntity searchByEmail(String email);
}
