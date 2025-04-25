package by.teamwork.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class SearchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button nextPaginationButton;

    @FXML
    private Button backPaginationButton;

    @FXML
    private TableColumn<Client, Integer> amountColumn;

    @FXML
    private Button backButton;

    @FXML
    private Text emailError;

    @FXML
    private TableColumn<Client, String> lastnameColumn;

    @FXML
    private Text passwordError;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Client> table;

    @FXML
    private TextField searchField;

    private Client client;

    @FXML
    void initialize() {
        assert amountColumn != null : "fx:id=\"amountColumn\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert lastnameColumn != null : "fx:id=\"lastnameColumn\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert passwordError != null : "fx:id=\"passwordError\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'searchPage.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'searchPage.fxml'.";

        loadFirstTimeTable();

        searchButton.setOnAction(actionEvent -> {

            Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Account.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            try {
                session.beginTransaction();
                Query<Client> query = session.createQuery("Select c FROM Client c WHERE c.lastname LIKE :lastname", Client.class);
                query.setParameter("lastname", "%" + searchField.getText() + "%");
                ObservableList<Client> clients = FXCollections.observableArrayList(query.list());
                table.setItems(clients);
                System.out.println(query.list());
               lastnameColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(cellDate.getValue().getLastname()));
               amountColumn.setCellValueFactory(cellData -> getTotalBills(cellData.getValue()));
                session.getTransaction().commit();
            } finally {
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

    private ObservableValue<Integer> getTotalBills(Client clientForTable) {

        List<Account> accountList;
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Client clientForSession = session.load(Client.class, clientForTable.getId());
            accountList = new ArrayList<>(clientForSession.getAccounts());
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
        return new SimpleIntegerProperty(accountList.stream()
                .reduce(0,(x,y)-> x + y.getAmount(), (x, y) -> x + y) )
                .asObject();

    }

    private void loadFirstTimeTable(){
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Client> query = session.createQuery("Select c FROM Client c", Client.class);
            ObservableList<Client> clients = FXCollections.observableArrayList(query.list());
            table.setItems(clients);

            lastnameColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(cellDate.getValue().getLastname()));
            amountColumn.setCellValueFactory(cellData -> getTotalBills(cellData.getValue()));
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

}
