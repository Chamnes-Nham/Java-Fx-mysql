package com.mycompany.mysqlproject.student;

import com.mycompany.mysqlproject.dbtool.DBconfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> colId;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, String> colEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearch;

    private ObservableList<Student> students;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        students = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadStudents();

        tableView.setItems(students);
    }

    private void loadStudents() {
        try {
            Statement stmt = DBconfig.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                students.add(new Student(rs.getString("id"), rs.getString("name"), rs.getString("email")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
//    private void addStudent() {
//        try {
//            Statement stmt = DBconfig.getConnection().createStatement();
//            String sql = String.format("INSERT INTO students (id, name, email) VALUES ('%d', %s', '%s')", Integer.parseInt(txtId.getText()), txtEmail.getText(),  txtName.getText());
//            stmt.executeUpdate(sql);
//
//            students.clear();
//            loadStudents();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void addStudent() {
    try {
        // Get the connection
        Connection conn = DBconfig.getConnection();
        
        // Prepare the SQL query with placeholders for values
        String sql = "INSERT INTO students (id, name, email) VALUES (?, ?, ?)";
        
        // Create a PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Set the values for the PreparedStatement
        pstmt.setString(1, txtId.getText());
        pstmt.setString(2, txtName.getText());
        pstmt.setString(3, txtEmail.getText());
        
        // Execute the update
        pstmt.executeUpdate();
        
        // Clear the students list and reload it
        students.clear();
        loadStudents();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    @FXML
private void updateStudent() {
    try {
        // Get the connection
        Connection conn = DBconfig.getConnection();
        
        // Prepare the SQL query with placeholders for values
        String sql = "UPDATE students SET name = ?, email = ? WHERE id = ?";
        
        // Create a PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Set the values for the PreparedStatement
        pstmt.setString(1, txtName.getText());
        pstmt.setString(2, txtEmail.getText());
        pstmt.setString(3, txtId.getText());
        
        // Execute the update
        pstmt.executeUpdate();
        
        // Clear the students list and reload it
        students.clear();
        loadStudents();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
private void deleteStudent() {
    try {
        // Get the connection
        Connection conn = DBconfig.getConnection();
        
        // Prepare the SQL query with a placeholder for the id
        String sql = "DELETE FROM students WHERE id = ?";
        
        // Create a PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Set the id value for the PreparedStatement
        pstmt.setString(1, txtId.getText());
        
        // Execute the update
        pstmt.executeUpdate();
        
        // Clear the students list and reload it
        students.clear();
        loadStudents();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
//    private void searchStudent() {
//        try {
//            students.clear();
//            Statement stmt = DBconfig.getConnection().createStatement();
//            String sql = String.format("SELECT * FROM students WHERE name LIKE '%%%s%%' OR email LIKE '%%%s%%'" , txtName.getText(), txtEmail.getText());
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void searchStudent() {
    try {
        students.clear();
        Statement stmt = DBconfig.getConnection().createStatement();
        
        // Collect search criteria
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        
        // Build the SQL query with optional conditions
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");
        
        if (!id.isEmpty()) {
            sql.append(" AND id = '").append(id).append("'");
        }
        if (!name.isEmpty()) {
            sql.append(" AND name LIKE '%").append(name).append("%'");
        }
        if (!email.isEmpty()) {
            sql.append(" AND email LIKE '%").append(email).append("%'");
        }
        
        ResultSet rs = stmt.executeQuery(sql.toString());

        while (rs.next()) {
            students.add(new Student(rs.getString("id"), rs.getString("name"), rs.getString("email")));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
    private void showStudentDetails() {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            txtId.setText(String.valueOf(selectedStudent.getId()));
            txtName.setText(selectedStudent.getName());
            txtEmail.setText(selectedStudent.getEmail());
        }
    }
}
