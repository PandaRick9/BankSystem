package by.teamwork.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Text birthdayError;

    @FXML
    private TextField birthdayField;

    @FXML
    private Text emailError;

    @FXML
    private TextField emailField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Text lastnameError;

    @FXML
    private CheckBox license;

    @FXML
    private Text nameError;

    @FXML
    private TextField nameField;

    @FXML
    private Text passwordError;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text patronymicError;

    @FXML
    private TextField patronymicField;

    @FXML
    private Text phoneError;

    @FXML
    private TextField phoneField;

    @FXML
    private Button sendButton;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert birthdayError != null : "fx:id=\"birthdayError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert birthdayField != null : "fx:id=\"birthdayField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert lastNameField != null : "fx:id=\"lastNameField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert lastnameError != null : "fx:id=\"lastnameError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert license != null : "fx:id=\"license\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert nameError != null : "fx:id=\"nameError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert passwordError != null : "fx:id=\"passwordError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert patronymicError != null : "fx:id=\"patronymicError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert patronymicField != null : "fx:id=\"patronymicField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert phoneField != null : "fx:id=\"phoneField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'signUpPage.fxml'.";

    }

}
