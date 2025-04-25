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

public class TransferPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text accountError;

    @FXML
    private TextField accountField;

    @FXML
    private Text amountError;

    @FXML
    private TextField amountField;

    @FXML
    private Button backButton;

    @FXML
    private Button transferButton;
    private Account account;
    private Client client;

    @FXML
    void initialize() {
        assert accountError != null : "fx:id=\"accountError\" was not injected: check your FXML file 'transferPage.fxml'.";
        assert accountField != null : "fx:id=\"accountField\" was not injected: check your FXML file 'transferPage.fxml'.";
        assert amountError != null : "fx:id=\"amountError\" was not injected: check your FXML file 'transferPage.fxml'.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file 'transferPage.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'transferPage.fxml'.";
        assert transferButton != null : "fx:id=\"transferButton\" was not injected: check your FXML file 'transferPage.fxml'.";

        transferButton.setOnAction(actionEvent -> {
            Integer secondAccount = Integer.valueOf(accountField.getText());
            Integer amountOfMoney = Integer.valueOf(amountField.getText());
            if(amountOfMoney > account.getAmount()){

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
    }


}
