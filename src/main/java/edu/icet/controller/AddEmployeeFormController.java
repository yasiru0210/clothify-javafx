package edu.icet.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.impl.EmployeeBoImpl;
import edu.icet.model.Employee;
import edu.icet.util.BoType;
import edu.icet.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AddEmployeeFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> EmpID;

    @FXML
    private TableColumn<?, ?> EmpMobile;

    @FXML
    private TableColumn<?, ?> EmpName;

    @FXML
    private TableColumn<?, ?> EmpNic;

    @FXML
    private TableView<?> EmpTable;

    @FXML
    private Pane dpane_111;

    @FXML
    private Pane dpane_1111;

    @FXML
    private Pane dpane_11111;

    @FXML
    private Pane dpane_111111;

    @FXML
    private Pane dpane_11112;

    @FXML
    private Pane dpane_11113;

    @FXML
    private JFXTextField txtEmpEmail;

    @FXML
    private JFXTextField txtEmpID;

    @FXML
    private JFXTextField txtEmpMobile;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtEmpNic;

    @FXML
    private JFXTextField txtEmpPassword;

    EmployeeBoImpl employeeBoImpl = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmpID.setCellValueFactory(new PropertyValueFactory<>("id"));
        EmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        EmpMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        EmpNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        EmpTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Employee) newValue);
            }
        }));

        EmpTable.setItems(employeeBoImpl.getAllUsers());
        txtEmpID.setText(employeeBoImpl.generateEmployeeId());
    }

    private void setTextToValues(Employee newValue) {
        txtEmpID.setText(newValue.getId());
        txtEmpName.setText(newValue.getName());
        txtEmpMobile.setText(newValue.getMobile());
        txtEmpNic.setText(newValue.getNic());
        txtEmpEmail.setText(newValue.getEmail());
    }

    private void refreshTable(){
        EmpTable.setItems(employeeBoImpl.getAllUsers());
    }

    private void clear(){
        txtEmpID.setText(employeeBoImpl.generateEmployeeId());
        txtEmpName.setText("");
        txtEmpMobile.setText("");
        txtEmpNic.setText("");
        txtEmpEmail.setText("");
        txtEmpPassword.setText("");
    }
    Validator validator=new Validator();
    @FXML
    void empBtnOnActionAdd(ActionEvent event) {

        Employee employee = new Employee(
                txtEmpID.getText(),
                txtEmpName.getText(),
                txtEmpMobile.getText(),
                txtEmpNic.getText(),
                txtEmpEmail.getText(),
                validator.hashPassword(txtEmpPassword.getText())
        );
        if (!txtEmpName.getText().equals("") && employeeBoImpl.isValidEmail(txtEmpEmail.getText()) && !txtEmpNic.getText().equals("")) {
            System.out.println(employee.toString());
            boolean isInsert = employeeBoImpl.insertUser(employee);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }

    }

    @FXML
    void empBtnOnActionDelete(ActionEvent event) {
        if (!txtEmpID.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = employeeBoImpl.deleteUserById(txtEmpID.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully");
                    alert2.showAndWait();
                    clear();
                    refreshTable();
                }
            }
        }
    }

    @FXML
    void empBtnOnActionSearch(ActionEvent event) {
        try {
            Employee employee = employeeBoImpl.searchUserByName(txtEmpName.getText());
            if (employee!=null){
                txtEmpID.setText(employee.getId());
                txtEmpNic.setText(employee.getNic());
                txtEmpEmail.setText(employee.getEmail());
                txtEmpMobile.setText(employee.getMobile());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }

    }

    @FXML
    void empBtnOnActionUpdate(ActionEvent event) {

        if (!txtEmpName.getText().equals("") && employeeBoImpl.isValidEmail(txtEmpEmail.getText()) && !txtEmpNic.getText().equals("")){
            Employee employee = new Employee(
                    txtEmpID.getText(),
                    txtEmpName.getText(),
                    txtEmpMobile.getText(),
                    txtEmpNic.getText(),
                    txtEmpEmail.getText(),
                    validator.hashPassword(txtEmpPassword.getText())
            );

            boolean isInsert = employeeBoImpl.updateUser(employee);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee update");
                alert.setContentText("Employee Updated Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Couldn't update!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }

    }


}
