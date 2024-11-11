package com.infoman2.controller;

import com.infoman2.DatabaseConnection;
import com.infoman2.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.rmi.StubNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController {
    public ToggleGroup gender;
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TableView <Student> table;
    @FXML
    private TableColumn<Student, String> colFN;
    @FXML
    private TableColumn<Student, String> colMN;
    @FXML
    private TableColumn<Student, String> colLN;
    @FXML
    private TableColumn<Student, String> colAD;
    @FXML
    private TableColumn<Student, String> colPN;
    @FXML
    private TableColumn<Student, String> colEM;
    @FXML
    private TableColumn<Student, String> colGEN;

    private boolean isEditing = false;
    private int studentId = 0;
    private DatabaseConnection db;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    public void initialize() throws SQLException{
        db = new DatabaseConnection();
        //load and populate our table
        //bind each column to the Student class properties and set the value factories
        colFN.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        colMN.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
        colLN.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        colAD.setCellValueFactory(new PropertyValueFactory<Student, String>("address"));
        colPN.setCellValueFactory(new PropertyValueFactory<Student, String>("phone"));
        colEM.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        colGEN.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));

        loadStudents();
    }

    public void loadStudents() throws SQLException{
        studentList.clear();
        String sql = "SELECT * from students";

        Statement stmt = db.getConnection().createStatement();
        ResultSet result = stmt.executeQuery(sql);

        while(result.next()){
            Student student = new Student(result.getInt("id"),
                    result.getString("firstName"),
                    result.getString("middleName"),
                    result.getString("lastName"),
                    result.getString("address"),
                    result.getString("phoneNumber"),
                    result.getString("email"),
                    result.getString("gender"));
            studentList.add(student);
        }
        table.setItems(studentList);
    }

    @FXML
    private void save() throws SQLException {
        if(!isEditing) { //if creating
            String sql = "INSERT INTO students(firstName, middleName, lastName, address, phoneNumber, email, gender) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, firstName.getText());
            pstmt.setString(2, middleName.getText());
            pstmt.setString(3, lastName.getText());
            pstmt.setString(4, address.getText());
            pstmt.setString(5, phone.getText());
            pstmt.setString(6, email.getText());
            if (male.isSelected()) {
                pstmt.setString(7, "Male");
            } else if (female.isSelected()) {
                pstmt.setString(7, "Female");
            }
            if (pstmt.executeUpdate() == 1) {
                firstName.clear();
                middleName.clear();
                lastName.clear();
                address.clear();
                phone.clear();
                email.clear();
                loadStudents();
            }
        }else{ //if editing
            String sql = "UPDATE students SET firstName = ?, middleName = ?, lastName = ?, address = ?, phoneNumber = ?, email = ?, gender = ? WHERE id = ?";

            try{
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setString(1, firstName.getText());
                pstmt.setString(2, middleName.getText());
                pstmt.setString(3, lastName.getText());
                pstmt.setString(4, address.getText());
                pstmt.setString(5, phone.getText());
                pstmt.setString(6, email.getText());
                if (male.isSelected()) {
                    pstmt.setString(7, "Male");
                } else if (female.isSelected()) {
                    pstmt.setString(7, "Female");
                }
                pstmt.setInt(8, studentId);

                if (pstmt.executeUpdate() == 1) {
                    firstName.clear();
                    middleName.clear();
                    lastName.clear();
                    address.clear();
                    phone.clear();
                    email.clear();
                    loadStudents();
                }

                loadStudents();
            }catch(SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void delete(){
        Student selectedStudent = table.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            String sql = "DELETE from students WHERE id = ?";

            try{
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setInt(1, selectedStudent.getId());
                pstmt.executeUpdate();

                studentList.remove(selectedStudent);
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void edit(){
        Student selectedStudent = table.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            firstName.setText(selectedStudent.getFirstName());
            middleName.setText(selectedStudent.getMiddleName());
            lastName.setText(selectedStudent.getLastName());
            address.setText(selectedStudent.getAddress());
            phone.setText(selectedStudent.getPhone());
            email.setText(selectedStudent.getEmail());

            isEditing = true;
            studentId = selectedStudent.getId();
        }
    }
}
