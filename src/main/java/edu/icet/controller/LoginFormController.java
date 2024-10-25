package edu.icet.controller;
import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.EmployeeBo;
import edu.icet.model.Employee;
import edu.icet.util.BoType;
import edu.icet.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    Validator validator=new Validator();
    @FXML
    void forgotBtnOnAction(ActionEvent event) {

    }

    @FXML
    void logInBtnOnAction(ActionEvent event) {
        if (txtUserName.getText().equals("admin")&&txtPassword.getText().equals("admin")){
            try {
                Stage stage0 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage0.close();

                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if (!validator.isValidEmail(txtUserName.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Enter valid username!!");
                alert.showAndWait();
                return;
            }
            EmployeeBo employeeBo= BoFactory.getInstance().getBo(BoType.EMPLOYEE);
            Employee employee=employeeBo.searchUserByEmail(txtUserName.getText());
            if (employee==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Enter valid username!!");
                alert.showAndWait();
                return;
            }
            if (validator.checkPassword(txtPassword.getText(),employee.getPassword())){
                try {
                    Stage stage0 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage0.close();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeDashboardForm.fxml"))));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Password Incorrect");
                alert.showAndWait();
            }

        }
    }

}
