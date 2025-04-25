package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ChangeProfileDataController {

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
    private Button changeButton;

    @FXML
    private Text emailError;

    @FXML
    private TextField emailField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Text lastnameError;

    @FXML
    private Text nameError;

    @FXML
    private TextField nameField;

    @FXML
    private Text passwordError;

    @FXML
    private TextField passwordField;

    @FXML
    private Text patronymicError;

    @FXML
    private TextField patronymicField;

    @FXML
    private Text phoneError;

    @FXML
    private TextField phoneField;

    private Client client;
    private boolean incorrect = false;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert birthdayError != null : "fx:id=\"birthdayError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert birthdayField != null : "fx:id=\"birthdayField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert changeButton != null : "fx:id=\"changeButton\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert lastNameField != null : "fx:id=\"lastNameField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert lastnameError != null : "fx:id=\"lastnameError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert nameError != null : "fx:id=\"nameError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert passwordError != null : "fx:id=\"passwordError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert patronymicError != null : "fx:id=\"patronymicError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert patronymicField != null : "fx:id=\"patronymicField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";
        assert phoneField != null : "fx:id=\"phoneField\" was not injected: check your FXML file 'changeProfileDataPage.fxml'.";

        disableFocus();

        changeButton.setOnAction(event -> {
            nameError.setText("");
            emailError.setText("");
            birthdayError.setText("");
            phoneError.setText("");
            lastnameError.setText("");
            patronymicError.setText("");
            passwordError.setText("");
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

            if(!incorrect){
                Thread writeThread = new Thread(()->{
                    writeInfo();
                });
                writeThread.start();
            }
            incorrect = false;
        });

        backButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/mainPage.fxml"));
                Parent root = loader.load();
                MainPageController mainPageController = loader.getController();
                mainPageController.initData(client);
                Platform.runLater(() -> {
                    Stage stage = (Stage) changeButton.getScene().getWindow();
                    Scene nextScene = new Scene(root);
                    stage.setScene(nextScene);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initData(Client client) {
        this.client = client;
        lastNameField.setText(client.getLastname());
        nameField.setText(client.getName());
        patronymicField.setText(client.getPatronymic());
        emailField.setText(client.getEmail());
        birthdayField.setText(client.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        phoneField.setText(client.getPhone());
        passwordField.setText(client.getPassword());
    }

    private void writeInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String input = birthdayField.getText();
        LocalDate parsedDate = LocalDate.parse(input, formatter);
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            client.setLastname(lastNameField.getText());
            client.setName(nameField.getText());
            client.setPatronymic(patronymicField.getText());
            client.setEmail(emailField.getText());
            client.setPassword(passwordField.getText());
            client.setPhone(phoneField.getText());
            client.setBirthday(parsedDate);
            session.update(client);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
    private void disableFocus(){
        birthdayField.setFocusTraversable(false);
        emailField.setFocusTraversable(false);
        nameField.setFocusTraversable(false);
        phoneField.setFocusTraversable(false);
        changeButton.setFocusTraversable(false);
        lastNameField.setFocusTraversable(false);
        patronymicField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }

}
