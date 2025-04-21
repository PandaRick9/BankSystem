package by.teamwork.banksystem;

import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("/by/teamwork/banksystem/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 720);
        stage.setTitle("BetaBank!");
        stage.setScene(scene);
        stage.show();
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5430/bankDB", "banking", "secret")
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        /*Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
                .addAnnotatedClass(Account.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Client client = new Client ();
            client.setLastname("Ivanov");
            client.setName("Ivan");
            client.setPatronymic("Ivanovich");
            client.setEmail("yes@gmail.com");
            client.setPassword("12345");
            client.setPhone("+375749209487");
            client.setBirthday(LocalDateTime.now());
            session.save(client);


            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }*/
    }

    public static void main(String[] args) {
        launch();
    }
}