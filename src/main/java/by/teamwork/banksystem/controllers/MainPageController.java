package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;


import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text accountNumberText;

    @FXML
    private Text amountText;

    @FXML
    private Button backPaginationButton;

    @FXML
    private Button closeAccountButton;

    @FXML
    private Text closeAccountError;

    @FXML
    private Text emailText;

    @FXML
    private Button exitButton;

    @FXML
    private Text fullNameText;

    @FXML
    private Button issuanceButton;

    @FXML
    private Text issuanceError;

    @FXML
    private Button manageProfileButton;

    @FXML
    private Button nextPaginationButton;

    @FXML
    private Button openAccountButton;

    @FXML
    private Button searchClientsButton;

    @FXML
    private Button topUpAccountButton;

    @FXML
    private Button transferToAccountButton;

    private Client client;

    @FXML
    void initialize() {
        assert accountNumberText != null : "fx:id=\"accountNumberText\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert amountText != null : "fx:id=\"amountText\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert backPaginationButton != null : "fx:id=\"backPaginationButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert closeAccountButton != null : "fx:id=\"closeAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert closeAccountError != null : "fx:id=\"closeAccountError\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert emailText != null : "fx:id=\"emailText\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert fullNameText != null : "fx:id=\"fullNameText\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert issuanceButton != null : "fx:id=\"issuanceButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert issuanceError != null : "fx:id=\"issuanceError\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert manageProfileButton != null : "fx:id=\"manageProfileButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert nextPaginationButton != null : "fx:id=\"nextPaginationButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert openAccountButton != null : "fx:id=\"openAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert searchClientsButton != null : "fx:id=\"searchClientsButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert topUpAccountButton != null : "fx:id=\"topUpAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert transferToAccountButton != null : "fx:id=\"transferToAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        issuanceButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/issuancePage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) issuanceButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openAccountButton.setOnAction(actionEvent -> {
            Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Account.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Integer accountNumberGen;
            try {
                session.beginTransaction();
                while (true){
                accountNumberGen = getAccountNumber();
                    Query<Account> query = session.createQuery("Select c FROM Account c WHERE c.accountNumber = :accountNumber", Account.class);
                    query.setParameter("accountNumber", accountNumberGen);
                    if(query.uniqueResult() == null){
                        break;
                    }
                }
                Account account = Account.builder()
                        .amount(0)
                        .accountNumber(accountNumberGen)
                        //.client_id(client.getId())
                        .build();
                session.save(account);


                session.getTransaction().commit();
            } finally {
                sessionFactory.close();
            }
        });
    }


    private Integer getAccountNumber() {
            Random random = new Random();
            int ownerAccount = 1; //для физлица 1
            int goalAccount = random.nextInt(10);
            int accountCurrency = 4;//валюта счета  4 = $
            int controlNumber = random.nextInt(10); //контрольная цифра
            int bankDepartament = random.nextInt(1000);
            int randomNumber = random.nextInt(1000);
            String result = ownerAccount + goalAccount + accountCurrency + controlNumber + String.format("%03d\n", bankDepartament) + String.format("%03d\n", randomNumber);
            return Integer.parseInt(result);


    }

    public void initData(Client client){
        this.client = client;
        fullNameText.setText(client.getLastname() + " " + client.getName() + " " + client.getPatronymic());
        emailText.setText(client.getEmail());
    }

    private void setTextForElement(){


    }

}
