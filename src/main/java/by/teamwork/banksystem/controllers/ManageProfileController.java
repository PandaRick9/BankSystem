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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button changeProfileDataButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Text deleteError;

    private Client client;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'manageProfilePage.fxml'.";
        assert changeProfileDataButton != null : "fx:id=\"changeProfileDataButton\" was not injected: check your FXML file 'manageProfilePage.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'manageProfilePage.fxml'.";
        assert deleteError != null : "fx:id=\"deleteError\" was not injected: check your FXML file 'manageProfilePage.fxml'.";

        changeProfileDataButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/changeProfileDataPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) changeProfileDataButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                ChangeProfileDataController changeProfileDataController = loader.getController();
                changeProfileDataController.initData(client);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteButton.setOnAction(actionEvent -> {
            Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Account.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            try {
                session.beginTransaction();
                Client client1 = session.load(Client.class, client.getId());
                if(client1.getAccounts() == null){
                    session.delete(client1);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/hello-view.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) deleteButton.getScene().getWindow();
                    Scene nextScene = new Scene(root);
                    stage.setScene(nextScene);
                }
                else{
                    deleteError.setText("У вас есть не закрытые счета");
                }
                session.getTransaction().commit();
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                sessionFactory.close();
            }
        });
        backButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/by/teamwork/banksystem/mainPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) backButton.getScene().getWindow();
                Scene nextScene = new Scene(root);
                MainPageController mainPageController = loader.getController();
                mainPageController.initData(client);
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void initData(Client client) {
        this.client = client;
    }
    public void helloWorld(){

    }


}
