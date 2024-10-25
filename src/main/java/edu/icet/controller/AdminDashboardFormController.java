package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardFormController implements Initializable {

    @FXML
    private JFXButton addCustomerNavBtn;

    @FXML
    private AnchorPane centerContainer;

    @FXML
    private Pane dpane_1;

    @FXML
    private Pane dpane_11;

    @FXML
    private Pane dpane_111;

    @FXML
    private AnchorPane homeAnchorpane;

    @FXML
    private JFXButton homeNavBtn;

    @FXML
    private Pane inner_pane;

    @FXML
    private JFXButton manageEmployeeBtn;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private JFXButton placeOrderNavBtn;

    @FXML
    private JFXButton productNavBtn;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private JFXButton supplierNavBtn;

    @FXML
    void ManageEmployeeNavBtnOnAction1(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/AddEmployeeForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void addCustomerNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void homeNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/demoForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void placeOrderNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void productNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/AddItemForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void supplierNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/AddSupplierForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void OrderHistryNavBtnOnAction(ActionEvent event) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/ViewOrderForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    void LogOutNavBtnOnAction(ActionEvent event) {
        try {
            Stage stage0 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage0.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/view/demoForm.fxml"));
            centerContainer.getChildren().removeAll();
            centerContainer.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

}
