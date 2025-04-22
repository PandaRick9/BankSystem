package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    private Text licenseError;

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
    private boolean incorrect = false;
    private boolean termsOfTheUserAgreement = false;

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
        assert licenseError != null : "fx:id=\"licenseError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert nameError != null : "fx:id=\"nameError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert passwordError != null : "fx:id=\"passwordError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert patronymicError != null : "fx:id=\"patronymicError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert patronymicField != null : "fx:id=\"patronymicField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert phoneField != null : "fx:id=\"phoneField\" was not injected: check your FXML file 'signUpPage.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'signUpPage.fxml'.";
        disableFocus();
        backButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/hello-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) backButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendButton.setOnAction(event -> {
            nameError.setText("");
            emailError.setText("");
            birthdayError.setText("");
            phoneError.setText("");
            lastnameError.setText("");
            patronymicError.setText("");
            passwordError.setText("");
            licenseError.setText("");
            if(!lastNameField.getText().matches("[А-Я][а-я]+")) {
                lastnameError.setText("Некорректный ввод фамилии");
                incorrect = true;
            }
            if(!nameField.getText().matches("[А-Я][а-я]+")) {
                nameError.setText("Некорректный ввод имени");
                incorrect = true;
            }
            if(!(patronymicField.getText().equals("")) && !patronymicField.getText().matches("[А-Я][а-я]+")) {
                patronymicError.setText("Некорректный ввод отчества");
                incorrect = true;
            }
            if(!emailField.getText().matches("^[A-Za-z0-9]+@[a-z]+\\.[a-z]+$")) {
                emailError.setText("Некорректный ввод почты");
                incorrect = true;
            }
            if(!birthdayField.getText().matches("^(?:(?:31\\.(?:01|03|05|07|08|10|12))" +
                    "|(?:30\\.(?:01|03|04|05|06|07|08|09|10|11|12))|" +
                    "(?:29\\.02\\.(?:19(?:2[4-9]|[3-9]\\d)|200[0-8]|" +
                    "200[48]|201[26]|202[048]))|(?:0[1-9]|1\\d|2[0-8])\\." +
                    "(?:0[1-9]|1[0-2])\\.(?:19(?:2[5-9]|[3-9]\\d)|200\\d|200[0-9]))$")) {
                birthdayError.setText("Возраст должен быть от 16 до 100 лет");
                incorrect = true;
            }
            if(!phoneField.getText().matches("^\\+375[0-9]{9}$")) {
                phoneError.setText("Некорректный ввод номера тефона");
                incorrect = true;
            }
            if(!passwordField.getText().matches("^(?=.*[A-Z])(?=.*\\d).*$")) {
                passwordError.setText("Пароль должен содержать хотя бы одну заглавную букву и цифру");
                incorrect = true;
            }
            if(!license.isSelected()){
                licenseError.setText("Примите пользовательские соглашения");
            }else
                termsOfTheUserAgreement = true;

            if(!incorrect && termsOfTheUserAgreement){
                Client client = getClient();
                Thread writeThread = new Thread(()->{
                    writeInfo(client);
                });
                Thread loadScene = new Thread(()->{
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/mainPage.fxml"));
                        Parent root = loader.load();
                        MainPageController mainPageController = loader.getController();
                        mainPageController.initData(client);
                        Platform.runLater(() -> {
                            Stage stage = (Stage) sendButton.getScene().getWindow();
                            Scene nextScene = new Scene(root);
                            stage.setScene(nextScene);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writeThread.start();
                loadScene.start();


            }
            termsOfTheUserAgreement= false;
            incorrect = false;
        });


    }

    private Client getClient() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String input = birthdayField.getText();
        LocalDate parsedDate = LocalDate.parse(input, formatter);
        Client client = Client.builder()
                .lastname(lastNameField.getText())
                .name(nameField.getText())
                .patronymic(patronymicField.getText())
                .phone(phoneField.getText())
                .birthday(parsedDate)
                .email(emailField.getText())
                .password(passwordField.getText())
                .build();
        return client;
    }

    private void writeInfo(Client client){
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            session.save(client);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
    private void disableFocus(){
        birthdayField.setFocusTraversable(false);
        license.setFocusTraversable(false);
        emailField.setFocusTraversable(false);
        nameField.setFocusTraversable(false);
        phoneField.setFocusTraversable(false);
        sendButton.setFocusTraversable(false);
        lastNameField.setFocusTraversable(false);
        patronymicField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }
    
}
