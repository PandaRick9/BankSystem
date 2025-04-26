package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class SignInPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Text emailError;

    @FXML
    private TextField emailField;

    @FXML
    private Text passwordError;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'signInPage.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'signInPage.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'signInPage.fxml'.";
        assert passwordError != null : "fx:id=\"passwordError\" was not injected: check your FXML file 'signInPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'signInPage.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'signInPage.fxml'.";
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

        signInButton.setOnAction(actionEvent -> {
            emailError.setText("");
            passwordError.setText("");
            validateClient();

        });
    }

    private void validateClient(){
        Client client = getClientFromDB();
        if(client ==  null){
            emailError.setText("Неправильный логин");
        }else {
            if (passwordField.getText().equals("")) {
                passwordError.setText("Введите пароль");
            } else if (!passwordField.getText().equals(client.getPassword())) {
                passwordError.setText("Неправильный пароль");
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/mainPage.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    Scene nextScene = new Scene(root);
                    MainPageController mainPageController = loader.getController();
                    mainPageController.initData(client);
                    stage.setScene(nextScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Client getClientFromDB(){
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Client client;
        try {
            session.beginTransaction();

            Query<Client> query = session.createQuery("Select c FROM Client c WHERE c.email = :email", Client.class);
            query.setParameter("email", emailField.getText());
            client =  query.uniqueResult();

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
        return client;
    }

}
