package com.mycompany.mysqlproject.login;

import com.mycompany.mysqlproject.App;
import com.mycompany.mysqlproject.dbtool.DBconfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void doLogin(ActionEvent event) {
        try {
            final String sql = "SELECT COUNT(username) FROM tbl_account "
                    + "WHERE username = '" + txtUsername.getText() + "' "
                    + "AND password = '" + txtPassword.getText() + "'";
            Statement st = DBconfig.getConnection().createStatement();
            ResultSet rst = st.executeQuery(sql);

            rst.next();
            if (rst.getInt(1) > 0) {
                System.out.println("Login success.");
                showStudentPanel();
            } else {
                System.out.println("Login failed!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showStudentPanel() {
        try {
            // The path to the Student.fxml file based on your project structure
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/mysqlproject/login/Student.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btnlogin.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
