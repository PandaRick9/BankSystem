package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
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
    private Account currentAccount;
    private List<Account> accountList = new ArrayList<>();

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
        assert manageProfileButton != null : "fx:id=\"manageProfileButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert nextPaginationButton != null : "fx:id=\"nextPaginationButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert openAccountButton != null : "fx:id=\"openAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert searchClientsButton != null : "fx:id=\"searchClientsButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert topUpAccountButton != null : "fx:id=\"topUpAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert transferToAccountButton != null : "fx:id=\"transferToAccountButton\" was not injected: check your FXML file 'mainPage.fxml'.";
        nextPaginationButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            int indexInList = accountList.indexOf(currentAccount);
            if(indexInList == (accountList.size() - 1)){
                currentAccount = accountList.getFirst();
                setAccountSettings();
            }else{
                currentAccount = accountList.get(indexInList + 1);
                setAccountSettings();
            }
        });
        backPaginationButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            int indexInList = accountList.indexOf(currentAccount);
            System.out.println(indexInList);
            System.out.println(accountList.toString());
            if(indexInList == 0 || indexInList == -1){
                currentAccount = accountList.getLast();
                setAccountSettings();
            }else{
                currentAccount = accountList.get(indexInList - 1);
                setAccountSettings();
            }
        });
        topUpAccountButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/addMoneyPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) issuanceButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                AddMoneyPageController addMoneyPageController = loader.getController();
                addMoneyPageController.initData(currentAccount, client);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        issuanceButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/issuancePage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) issuanceButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                IssuancePageController issuancePageController = loader.getController();
                issuancePageController.initData(currentAccount,client);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        closeAccountButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            if(currentAccount.getAmount() == 0){
                Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                        .addAnnotatedClass(Account.class);
                SessionFactory sessionFactory = configuration.buildSessionFactory();
                Session session = sessionFactory.getCurrentSession();
                try {
                    session.beginTransaction();
                    Account account = session.load(Account.class, currentAccount.getAccountId());
                    session.remove(account);
                    session.getTransaction().commit();
                } finally {
                    sessionFactory.close();
                }
                loadFirstAccount(client);
            }else {
                closeAccountError.setText("Положительный баланс!");

            }

        });

        transferToAccountButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/transferPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) issuanceButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                TransferPageController transferPageController = loader.getController();
                transferPageController.initData(currentAccount,client);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        openAccountButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Account.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Integer accountNumberGen;
            try {
                session.beginTransaction();
                while (true) {
                    accountNumberGen = getAccountNumber();
                    Query<Account> query = session.createQuery("Select c FROM Account c WHERE c.accountNumber = :accountNumber", Account.class);
                    query.setParameter("accountNumber", accountNumberGen);
                    if (query.uniqueResult() == null) {
                        break;
                    }
                }
                Account account = Account.builder()
                        .amount(0)
                        .accountNumber(accountNumberGen)
                        .client(client)
                        .build();
                accountList.add(account);
                session.save(account);
                session.getTransaction().commit();
            } finally {
                sessionFactory.close();
            }

        });
        exitButton.setOnAction(actionEvent -> {
            setCloseErrorEmpty();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/hello-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) exitButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void setCloseErrorEmpty() {
        closeAccountError.setText("");
    }


    private Integer getAccountNumber() {
        Random random = new Random();
        int ownerAccount = 1; //для физлица 1
        int goalAccount = random.nextInt(10);
        int accountCurrency = 4;//валюта счета  4 = $
        int controlNumber = random.nextInt(10); //контрольная цифра
        int bankDepartment = random.nextInt(1000);
        int randomNumber = random.nextInt(1000);
        String result = String.valueOf(ownerAccount) + String.valueOf(goalAccount) + String.valueOf(accountCurrency) + String.valueOf(controlNumber) + String.format("%03d", bankDepartment) + String.format("%03d", randomNumber);
        return Integer.parseInt(result);


    }

    public void initData(Client client) {
        this.client = client;
        setPersonalInfo(client);
        loadFirstAccount(client);
    }

    private void setPersonalInfo(Client client) {
        fullNameText.setText(client.getLastname() + " " + client.getName() + " " + client.getPatronymic());
        emailText.setText(client.getEmail());
    }

    public void controllersData(Client client, Account account){
        this.client = client;
        setPersonalInfo(client);
        loadFirstAccount(client);
        this.currentAccount = account;
        setAccountSettings();
    }


    private void loadFirstAccount(Client client) {
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Client client1 = session.load(Client.class, client.getId());
            currentAccount = client1.getAccounts().getFirst();
            accountList.addAll(client1.getAccounts());
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
        setAccountSettings();
    }

    private void setAccountSettings() {
        accountNumberText.setText(String.valueOf(currentAccount.getAccountNumber()));
        amountText.setText(currentAccount.getAmount() + "$");
    }

}
