package by.teamwork.banksystem.controllers;

import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMoneyPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text accountNumberText;

    @FXML
    private TextField amountField;

    @FXML
    private Button backButton;

    @FXML
    private Text errorText;

    @FXML
    private Button replenishButton;

    @FXML
    private Text successText;


    private Account account;
    private Client client;

    @FXML
    void initialize() {
        assert accountNumberText != null : "fx:id=\"accountNumberText\" was not injected: check your FXML file 'addMoneyPage.fxml'.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file 'addMoneyPage.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'addMoneyPage.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'addMoneyPage.fxml'.";
        assert replenishButton != null : "fx:id=\"replenishButton\" was not injected: check your FXML file 'addMoneyPage.fxml'.";
        assert successText != null : "fx:id=\"successText\" was not injected: check your FXML file 'addMoneyPage.fxml'.";


        replenishButton.setOnAction(actionEvent -> {
            successText.setText("");
            errorText.setText("");
            Integer amount = Integer.valueOf(amountField.getText());
            if(amount <= 0){
                errorText.setText("Сумма должна быть положительной");
            }else if(amount > 5000){
                errorText.setText("Сумма не может превышать 5000");
            }else {
                Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                        .addAnnotatedClass(Account.class);
                SessionFactory sessionFactory = configuration.buildSessionFactory();
                Session session = sessionFactory.getCurrentSession();
                try {
                    session.beginTransaction();
                    Account account1 = session.load(Account.class, account.getAccountId());
                    account1.setAmount(account1.getAmount() + amount);
                    account = account1;
                    session.getTransaction().commit();
                } finally {
                    sessionFactory.close();
                }

                successText.setText("Успешно!");
            }

        });

        backButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/mainPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) backButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                MainPageController mainPageController = loader.getController();
                mainPageController.controllersData(client, account);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initData(Account account, Client client) {
        this.account = account;
        this.client = client;
        accountNumberText.setText(String.valueOf(account.getAccountNumber()));
    }

}
