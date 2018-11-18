package UI.Windows;

import Core.Holders.DataHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class MainWindow extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/Resources/mainWindowLayout.fxml"));
        primaryStage.setTitle("Dziennik");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/UI/Resources/Drawable/icon.png"));
        primaryStage.show();

        DataHolder.initialize();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wybór");
        alert.setHeaderText("Zapisanie danych");
        alert.setContentText("Czy chcesz zapisać dane?");
        alert.initStyle(StageStyle.TRANSPARENT);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) DataHolder.save();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
