package by.teamwork.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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

    }

}
