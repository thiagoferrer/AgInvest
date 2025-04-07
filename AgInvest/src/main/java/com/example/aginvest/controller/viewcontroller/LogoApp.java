package com.example.aginvest.controller.viewcontroller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class LogoApp extends Application {
    // Método start() modificado
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Tente usar o caminho absoluto do recurso
            //URL fxmlUrl = getClass().getResource("/com/example/aginvest/LogoApp.fxml");
            //bypass para simulacao RF
            URL fxmlUrl = getClass().getResource("/com/example/aginvest/RendaFixa.fxml");
            if (fxmlUrl == null) {
                System.err.println("FXML file not found!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root,360, 640);
            stage.setScene(scene);
            stage.setTitle("AgInvest");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}