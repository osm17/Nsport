package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.persistence.*;
public class
View extends Application {
    public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Hibernate");
    @Override
    public void start(Stage stage) throws Exception {
        // Content of application
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/org/example/viewz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 722, 383);
        stage.setTitle("Notes");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void run(String[] args){
        launch(args);
    }
    public static EntityManagerFactory getENTITY_MANAGER_FACTORY() {
        return ENTITY_MANAGER_FACTORY;
    }
}
