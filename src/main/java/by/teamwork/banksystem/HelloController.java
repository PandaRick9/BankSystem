package by.teamwork.banksystem;

import by.teamwork.banksystem.models.Account;
import by.teamwork.banksystem.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Bank System!");
        Configuration configuration = new Configuration().addAnnotatedClass(Client.class)
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
        }
    }
}


