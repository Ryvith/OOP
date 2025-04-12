package com.github.ryvith;

import com.github.ryvith.ui.UIhandler.GameUIController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL fxmlUrl = getClass().getResource("/com/github/ryvith/game.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML file not found");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            GameUIController controller = loader.getController();

            Platform.runLater(() -> {
                controller.initializeGame();
                controller.initializeBoard();
                controller.updateUI();
            });

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/github/ryvith/style.css")).toExternalForm());

            primaryStage.setTitle("棋盘游戏 (Peace/Reversi/Gomoku)");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("应用程序启动失败:");
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        InputStream is = Main.class.getClassLoader()
                .getResourceAsStream("com/github/ryvith/game.fxml");
        System.out.println("资源流: " + (is != null ? "存在" : "不存在"));

        // 列出所有资源（调试用）
        try {
            Enumeration<URL> resources = Main.class.getClassLoader()
                    .getResources("com/github/ryvith/");
            while (resources.hasMoreElements()) {
                System.out.println("找到资源: " + resources.nextElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);

    }
}
