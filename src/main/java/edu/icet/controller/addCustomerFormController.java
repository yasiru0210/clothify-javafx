package edu.icet.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.impl.CustomerBoImpl;
import edu.icet.model.Customer;
import edu.icet.model.Employee;
import edu.icet.util.BoType;
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

public class addCustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> CusEmail;

    @FXML
    private TableColumn<?, ?> CusID;

    @FXML
    private TableColumn<?, ?> CusName;

    @FXML
    private TableColumn<?, ?> cusAddress;

    @FXML
    private Pane dpane_111;

    @FXML
    private Pane dpane_1111;

    @FXML
    private Pane dpane_11111;

    @FXML
    private Pane dpane_111111;

    @FXML
    private TableView<?> tblCustomer;

    @FXML
    private JFXTextField txtCusAddress;

    @FXML
    private JFXTextField txtCusEmail;

    @FXML
    private JFXTextField txtCusID;

    @FXML
    private JFXTextField txtCusName;

    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CusID.setCellValueFactory(new PropertyValueFactory<>("id"));
        CusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        CusEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Customer) newValue);
            }
        }));

        tblCustomer.setItems(customerBoImpl.getAllUsers());
        txtCusID.setText(customerBoImpl.generateCustomerId());
    }

    private void setTextToValues(Customer newValue) {
        txtCusID.setText(newValue.getId());
        txtCusName.setText(newValue.getName());
        txtCusEmail.setText(newValue.getEmail());
        txtCusAddress.setText(newValue.getAddress());
    }

    private void refreshTable(){
        tblCustomer.setItems(customerBoImpl.getAllUsers());
    }

    private void clear(){
        txtCusID.setText(customerBoImpl.generateCustomerId());
        txtCusName.setText("");
        txtCusEmail.setText("");
        txtCusAddress.setText("");
    }
    @FXML
    void cusBtnAdd(ActionEvent event) {
        Customer customer = new Customer(
                txtCusID.getText(),
                txtCusName.getText(),
                txtCusEmail.getText(),
                txtCusAddress.getText()
        );
        if (!txtCusName.getText().equals("") && customerBoImpl.isValidEmail(txtCusEmail.getText()) && !txtCusAddress.getText().equals("")) {
            System.out.println(customer.toString());
            boolean isInsert = customerBoImpl.insertUser(customer);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Added");
                alert.setContentText("Customer Added Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    @FXML
    void cusBtnDelete(ActionEvent event) {
        if (!txtCusID.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = customerBoImpl.deleteUserById(txtCusID.getText());
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
    void cusBtnSearch(ActionEvent event) {
        try {
            Customer customer = customerBoImpl.searchUserByName(txtCusName.getText());
            if (customer!=null){
                txtCusID.setText(customer.getId());
                txtCusName.setText(customer.getName());
                txtCusEmail.setText(customer.getEmail());
                txtCusAddress.setText(customer.getAddress());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    @FXML
    void cusBtnUpdate(ActionEvent event) {

        if (!txtCusName.getText().equals("") && customerBoImpl.isValidEmail(txtCusEmail.getText()) && !txtCusAddress.getText().equals("")){
            Customer customer = new Customer(
                    txtCusID.getText(),
                    txtCusName.getText(),
                    txtCusEmail.getText(),
                    txtCusAddress.getText()
            );

            boolean isInsert = customerBoImpl.updateUser(customer);
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



