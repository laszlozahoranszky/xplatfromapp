package ch.zahoo.xplatformapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebBrowserViewer extends Application {
    public static void main(String[] args){
        startHttpServerInBackground();
        launch(args);
    }

    private static void startHttpServerInBackground() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(
                () ->
                {
                    try {
                        HttpServer.startHttpServer();
                        System.out.println("http server stated");
                    } catch (Exception e) {
                        System.out.println("ERROR at starting http server: " + e);
                    }
                });
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Cross platform app example");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        String url = "http://localhost:" + HttpServer.PSEUDO_RANDOM_PORT;
        webEngine.load(url);

        StackPane sp = new StackPane();
        sp.getChildren().add(webView);

        Scene root = new Scene(sp);

        stage.setScene(root);
        addCloseButtonHandler(stage);
        stage.show();
    }

    private void addCloseButtonHandler(Stage stage) {
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        // Application Closed by click to Close Button(X)
                        try {
                            HttpServer.stopHttpServer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}