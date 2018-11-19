package UI.Windows;

import Core.Holders.DataHolder;
import UI.Alerts.CustomAlert;
import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class MainWindow extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Region root = FXMLLoader.load(getClass().getResource("/UI/Resources/mainWindowLayout.fxml"));
        primaryStage.setTitle("Dziennik");
        //Scene scene = new Scene(root, 640, 480);
        UndecoratorScene.setClassicDecoration();
        UndecoratorScene scene = new UndecoratorScene(primaryStage, root);
        scene.lookup("#StageMenu").setVisible(false);
        scene.lookup(".decoration-button-fullscreen").setVisible(false);

        scene.getStylesheets().add(getClass().getResource("/UI/Resources/Styles/modena_dark.css").toExternalForm());
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/UI/Resources/Drawable/icon.png"));
        primaryStage.setHeight(640);
        primaryStage.show();

        DataHolder.initialize();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();

        Alert alert = new CustomAlert(Alert.AlertType.CONFIRMATION).getAlert();
        alert.setTitle("Wybór");
        alert.setHeaderText("Zapisanie danych");
        alert.setContentText("Czy chcesz zapisać dane?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) DataHolder.save();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
