package edu.icet.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.SupplierBo;
import edu.icet.bo.custom.impl.SupplierBoImpl;
import edu.icet.model.Customer;
import edu.icet.model.Supplier;
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
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> SupCompany;

    @FXML
    private TableColumn<?, ?> SupID;

    @FXML
    private TableColumn<?, ?> SupMobile;

    @FXML
    private TableColumn<?, ?> SupName;

    @FXML
    private Pane dpane_111;

    @FXML
    private Pane dpane_1111;

    @FXML
    private Pane dpane_11111;

    @FXML
    private Pane dpane_111111;

    @FXML
    private Pane dpane_1111111;

    @FXML
    private TableView<?> tblSupplier;

    @FXML
    private JFXTextField txtSupCompany;

    @FXML
    private JFXTextField txtSupEmail;

    @FXML
    private JFXTextField txtSupID;

    @FXML
    private JFXTextField txtSupMobile;

    @FXML
    private JFXTextField txtSupName;


    SupplierBoImpl supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SupID.setCellValueFactory(new PropertyValueFactory<>("id"));
        SupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        SupMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        SupCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        tblSupplier.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Supplier) newValue);
            }
        }));

        tblSupplier.setItems(supplierBo.getAllUsers());
        txtSupID.setText(supplierBo.generateSupplierId());
    }

    private void setTextToValues(Supplier newValue) {
        txtSupID.setText(newValue.getId());
        txtSupName.setText(newValue.getName());
        txtSupEmail.setText(newValue.getEmail());
        txtSupMobile.setText(newValue.getMobile());
        txtSupCompany.setText(newValue.getCompany());
    }

    private void refreshTable(){
        tblSupplier.setItems(supplierBo.getAllUsers());
    }

    private void clear(){
        txtSupID.setText(supplierBo.generateSupplierId());
        txtSupName.setText("");
        txtSupEmail.setText("");
        txtSupMobile.setText("");
        txtSupCompany.setText("");
    }


    @FXML
    void supBtnAddOnAction(ActionEvent event) {
        Supplier supplier = new Supplier(
                txtSupID.getText(),
                txtSupName.getText(),
                txtSupEmail.getText(),
                txtSupMobile.getText(),
                txtSupCompany.getText()
        );
        if (!txtSupName.getText().equals("") && supplierBo.isValidEmail(txtSupEmail.getText()) && !txtSupMobile.getText().equals("")) {
            System.out.println(supplier.toString());
            boolean isInsert = supplierBo.insertUser(supplier);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Added");
                alert.setContentText("Supplier Added Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    @FXML
    void supBtnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you sure want to delete this Supplier");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()== ButtonType.OK){
            boolean isDeleted = supplierBo.deleteUserById(txtSupID.getText());
            if (isDeleted){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Supplier Deleted");
                alert2.setContentText("Supplier deleted successfully");
                alert2.showAndWait();
                clear();
                refreshTable();
            }
        }
    }


    @FXML
    void supBtnSearchOnAction(ActionEvent event) {
        try {
            Supplier supplier = supplierBo.searchUserByName(txtSupName.getText());
            if (supplier!=null){
                txtSupID.setText(supplier.getId());
                txtSupName.setText(supplier.getName());
                txtSupEmail.setText(supplier.getEmail());
                txtSupMobile.setText(supplier.getMobile());
                txtSupCompany.setText(supplier.getCompany());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }
    }
    Validator validator=new Validator();
    @FXML
    void supBtnUpdateOnAction(ActionEvent event) {
        if (!txtSupName.getText().equals("") && validator.isValidEmail(txtSupEmail.getText()) && !txtSupMobile.getText().equals("")){
            Supplier supplier = new Supplier(
                    txtSupID.getText(),
                    txtSupName.getText(),
                    txtSupEmail.getText(),
                    txtSupMobile.getText(),
                    txtSupCompany.getText()
            );

            boolean isInsert = supplierBo.updateUser(supplier);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier update");
                alert.setContentText("Supplier Updated Successfully..!");
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
