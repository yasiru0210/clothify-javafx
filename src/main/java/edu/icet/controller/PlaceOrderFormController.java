package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.CustomerBo;
import edu.icet.bo.custom.ItemBo;
import edu.icet.bo.custom.OrderBo;
import edu.icet.bo.custom.impl.OrderBoImpl;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.model.*;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private Label netTotal;

    @FXML
    private TableView<TblCart> tblCart;

    @FXML
    private TableColumn<?, ?> tblItemCode;

    @FXML
    private TableColumn<?, ?> tblItemName;

    @FXML
    private TableColumn<?, ?> tblPrice;

    @FXML
    private TableColumn<?, ?> tblQty;

    @FXML
    private TableColumn<?, ?> tblTotal;

    @FXML
    private JFXComboBox<String> txtCustID;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private Label txtDate;

    @FXML
    private JFXComboBox<String> txtItemCode;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private Label txtSize;

    @FXML
    private Label txtUnitPrice;

    ObservableList<TblCart> cartList = FXCollections.observableArrayList();
    List<OrderDetailEntity> orderDetailList=new ArrayList<>();
    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String itemCode=txtItemCode.getValue();
        String itemName=txtItemName.getText();
        Integer qty=Integer.parseInt(txtQty.getText());
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        Double total=unitPrice*qty;
        boolean available=false;
        for(TblCart c:cartList){
            if (c.getItemCode().equals(itemCode)){
                available=true;
                c.setQty(c.getQty()+qty);
                c.setTotal(c.getTotal()+total);
                tblCart.getItems().clear();
                break;
            }
        }
        if (!available) {
            TblCart cart = new TblCart(itemCode, itemName, qty, unitPrice, total);
            cartList.add(cart);
        }
        tblCart.setItems(FXCollections.observableArrayList(cartList));
        Double net=Double.parseDouble(netTotal.getText())+total;
        netTotal.setText(net+"");
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        LocalDate orderDate = LocalDate.now();
        Double orderTotal = Double.parseDouble(netTotal.getText());
        Customer customer=customerBo.searchItemByID(txtCustID.getValue());
        String orderID=txtOrderID.getText();
        Order order=new Order(orderID,customer,orderDate,orderTotal);

        for(TblCart cart : cartList){
            OrderDetailEntity orderDetailEntity=new OrderDetailEntity(cart.getItemCode(), cart.getQty(), cart.getTotal());
            Item item=itemBo.searchItemByID(orderDetailEntity.getItemID());
            Integer newQty=item.getQty()-orderDetailEntity.getQty();
            if (newQty<0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Out of stock : Item Code"+item.getId());
                alert.showAndWait();
                cartList.remove(cart);
                tblCart.setItems(cartList);
                tblCart.refresh();
                return;
            }
            item.setQty(newQty);
            itemBo.updateItem(item);
            orderDetailList.add(orderDetailEntity);
        }
        orderBo.insert(order,orderDetailList);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Order Placed Successfully");
        alert.showAndWait();
        clear();
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        try {
            Item item=itemBo.searchItemByName(txtItemName.getText());
            txtItemCode.setValue(item.getId());
            txtUnitPrice.setText(item.getUnitPrice()+"");
            txtSize.setText(item.getSize());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Item Found");
            alert.showAndWait();
            txtItemCode.setValue("");
            txtUnitPrice.setText("0");
            txtSize.setText("0");
        }
    }

    @FXML
    void custIDonAction(ActionEvent event) {
        try {
            txtCustName.setText(customerBo.searchItemByID(txtCustID.getValue()).getName());
        } catch (Exception e) {
            System.out.println("");
        }
    }

    @FXML
    void itemCodeOnAction(ActionEvent event) {
        try {
            Item item=itemBo.searchItemByID(txtItemCode.getValue());
            txtItemName.setText(item.getName());
            txtUnitPrice.setText(item.getUnitPrice()+"");
            txtSize.setText(item.getSize());
        } catch (Exception e) {
            System.out.println("");
        }
    }

    @FXML
    void supBtnSearchOnAction(ActionEvent event) {
        try {
            txtCustID.setValue(customerBo.searchUserByName(txtCustName.getText()).getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Customer Found");
            alert.showAndWait();
        }
    }

    CustomerBo customerBo= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private void loadCustomerIdmenu(){
        ObservableList<String> customers = FXCollections.observableArrayList();
        ObservableList<Customer> sup=customerBo.getAllUsers();
        for (Customer customer:sup){
            customers.add(customer.getId());
        }
        txtCustID.setItems(customers);
    }

    ItemBo itemBo= BoFactory.getInstance().getBo(BoType.ITEM);
    private void loadItemIdmenu(){
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Item> sup=itemBo.getAllUsers();
        for (Item item:sup){
            items.add(item.getId());
        }
        txtItemCode.setItems(items);
    }

    private void loadDateAndTime(){
        Date date =new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow=f.format(date);

        txtDate.setText(dateNow);
    }
    OrderBoImpl orderBo=BoFactory.getInstance().getBo(BoType.ORDER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadCustomerIdmenu();
        loadItemIdmenu();
        loadDateAndTime();
        clear();
    }
    private void clear(){
        txtOrderID.setText(orderBo.generateOrderId());
        txtCustID.setValue(null);
        txtCustName.setText(null);
        txtItemCode.setValue(null);
        txtItemName.setText(null);
        txtQty.setText(null);
        netTotal.setText("0");
        txtUnitPrice.setText("0");
        txtSize.setText("0");
        cartList = FXCollections.observableArrayList();
        orderDetailList=new ArrayList<>();
        tblCart.setItems(cartList);
    }
}
