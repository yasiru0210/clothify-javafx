package edu.icet.controller;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderBo;
import edu.icet.bo.custom.impl.OrderBoImpl;
import edu.icet.dao.custom.impl.OrderDaoImpl;
import edu.icet.model.Order;
import edu.icet.model.orderTblObj;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrderFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> custID;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private TableColumn<?, ?> netTotal;

    @FXML
    private TableColumn<?, ?> orderID;

    @FXML
    private TableView<?> tblOrder;

    @FXML
    void printBtnOnAction(ActionEvent event) {

    }
    OrderBoImpl orderBo= BoFactory.getInstance().getBo(BoType.ORDER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        netTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));


        System.out.println(orderBo.getAllOrders());
        tblOrder.setItems(orderBo.getAllOrders());
    }
}
