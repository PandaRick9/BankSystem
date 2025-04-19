package by.teamwork.banksystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5430/bankDB", "banking", "secret")
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }

    public static void main(String[] args) {
        launch();
    }
}