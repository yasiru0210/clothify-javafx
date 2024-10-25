package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.ItemBo;
import edu.icet.bo.custom.SupplierBo;
import edu.icet.model.Item;
import edu.icet.model.Supplier;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    @FXML
    private TableView<?> itemTable;

    @FXML
    private TableColumn<?, ?> productCategory;

    @FXML
    private TableColumn<?, ?> productID;

    @FXML
    private TableColumn<?, ?> productName;

    @FXML
    private TableColumn<?, ?> productPrice;

    @FXML
    private TableColumn<?, ?> productQty;

    @FXML
    private TableColumn<?, ?> productSize;

    @FXML
    private JFXComboBox<String> txtCategory;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXComboBox<String> txtSize;

    @FXML
    private JFXComboBox<String> txtSupID;

    @FXML
    private JFXTextField txtSupName;

    @FXML
    void supIDAction(ActionEvent event) {
        try {
            txtSupName.setText(supplierBo.searchSupplierByID(txtSupID.getValue()).getName());
        } catch (Exception e) {
            System.out.println("111");
        }
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtID.getText(),
                txtName.getText(),
                Integer.parseInt(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                txtCategory.getValue(),
                txtSize.getValue(),
                supplierBo.searchSupplierByID(txtSupID.getValue())
        );
        System.out.println(item);
        if (!txtName.getText().equals("")) {
            System.out.println(item.toString());
            boolean isInsert = itemBo.insertItem(item);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Added");
                alert.setContentText("Item Added Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you sure want to delete this Item");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()== ButtonType.OK){
            boolean isDeleted = itemBo.deleteItemById(txtID.getText());
            if (isDeleted){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Item Deleted");
                alert2.setContentText("Item deleted successfully");
                alert2.showAndWait();
                clear();
                refreshTable();
            }
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Item item = itemBo.searchItemByName(txtName.getText());
            if (item!=null){
                txtID.setText(item.getId());
                txtName.setText(item.getName());
                txtCategory.setValue(item.getCategory());
                txtSize.setValue(item.getSize());
                txtPrice.setText(item.getUnitPrice()+"");
                txtQty.setText(item.getQty()+"");
                txtSupID.setValue(item.getSupplier().getId());
                txtSupName.setText(item.getSupplier().getName());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtName.getText().equals("")){
            Item item = new Item(
                    txtID.getText(),
                    txtName.getText(),
                    Integer.parseInt(txtPrice.getText()),
                    Integer.parseInt(txtQty.getText()),
                    txtCategory.getValue(),
                    txtSize.getValue(),
                    supplierBo.searchSupplierByID(txtSupID.getValue())
            );

            boolean isInsert = itemBo.updateItem(item);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item update");
                alert.setContentText("Item Updated Successfully..!");
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

    @FXML
    void supBtnSearchOnAction(ActionEvent event) {
        try {
            txtSupID.setValue(supplierBo.searchUserByName(txtSupName.getText()).getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Supplier Found");
            alert.showAndWait();
        }
    }
    ItemBo itemBo= BoFactory.getInstance().getBo(BoType.ITEM);
    SupplierBo supplierBo=BoFactory.getInstance().getBo(BoType.SUPPLIER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        productQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        itemTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Item) newValue);
            }
        }));

        itemTable.setItems(itemBo.getAllUsers());
        txtID.setText(itemBo.generateItemId());


        loadCategoryMenu();
        loadSizeMenu();
        loadSupplierIDMenu();
    }

    private void setTextToValues(Item newValue) {
        txtID.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtPrice.setText(newValue.getUnitPrice()+"");
        txtQty.setText(newValue.getQty()+"");
        txtCategory.setValue(newValue.getCategory());
        txtSize.setValue(newValue.getSize());
        txtSupID.setValue(newValue.getSupplier().getId());
        txtSupName.setText(newValue.getSupplier().getName());
    }

    private void loadCategoryMenu() {
        ObservableList<String> category = FXCollections.observableArrayList();
        category.add("Ladies");
        category.add("Gents");
        category.add("Kids");
        txtCategory.setItems(category);
    }
    private void loadSizeMenu() {
        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        txtSize.setItems(sizes);
    }

    private void loadSupplierIDMenu() {
        ObservableList<String> suppliers = FXCollections.observableArrayList();
        ObservableList<Supplier> sup=supplierBo.getAllUsers();
        for (Supplier supplier:sup){
            suppliers.add(supplier.getId());
        }
        txtSupID.setItems(suppliers);
    }
    private void refreshTable(){
        itemTable.setItems(itemBo.getAllUsers());
    }

    private void clear(){
        txtID.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtQty.setText("");
        txtCategory.setValue("");
        txtSize.setValue("");
        txtSupID.setValue("");
        txtSupName.setText("");
        txtID.setText(itemBo.generateItemId());
    }
}
