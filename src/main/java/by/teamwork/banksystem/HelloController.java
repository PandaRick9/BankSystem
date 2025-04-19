package by.teamwork.banksystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Bank System!");
        /*Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println(person.getName());
            System.out.println(person.getAge());

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }*/
    }
}


