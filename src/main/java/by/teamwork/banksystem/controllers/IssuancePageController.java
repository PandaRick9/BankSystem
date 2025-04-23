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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class IssuancePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text accountNumberText;

    @FXML
    private Text amountText;

    @FXML
    private Button backButton;

    @FXML
    private Text issuanceError;

    @FXML
    private TextField issuanceField;

    @FXML
    private Text successText;

    @FXML
    private Button withdrawButton;

    private Account account;
    private Client client;

    @FXML
    void initialize() {
        assert accountNumberText != null : "fx:id=\"accountNumberText\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert amountText != null : "fx:id=\"amountText\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert issuanceError != null : "fx:id=\"issuanceError\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert issuanceField != null : "fx:id=\"issuanceField\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert successText != null : "fx:id=\"successText\" was not injected: check your FXML file 'issuancePage.fxml'.";
        assert withdrawButton != null : "fx:id=\"withdrawButton\" was not injected: check your FXML file 'issuancePage.fxml'.";
        withdrawButton.setOnAction(actionEvent -> {
            successText.setText("");
            issuanceError.setText("");
            Integer amount = Integer.valueOf(issuanceField.getText());
            if(amount > account.getAmount()){
                issuanceError.setText("Недостаточно средств");
            }else if(amount < 0){
                issuanceError.setText("Введите положительное число");
            }else {
                Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                        .addAnnotatedClass(Account.class);
                SessionFactory sessionFactory = configuration.buildSessionFactory();
                Session session = sessionFactory.getCurrentSession();
                try {
                    session.beginTransaction();
                    Account account1 = session.load(Account.class, account.getAccountId());
                    account1.setAmount(account1.getAmount() - amount);
                    account.setAmount(account.getAmount() - amount);
                    session.getTransaction().commit();
                } finally {
                    sessionFactory.close();
                }
                amountText.setText(account.getAmount() + "$");
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
        amountText.setText(account.getAmount() + "$");
    }
}
