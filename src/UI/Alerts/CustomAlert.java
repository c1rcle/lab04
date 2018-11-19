package UI.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class CustomAlert
{
    private Alert alert;

    public CustomAlert(Alert.AlertType type)
    {
        alert = new Alert(type);
        alert.initStyle(StageStyle.UTILITY);
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(getClass().getResource("/UI/Resources/Styles/dialog_dark.css").toExternalForm());
    }

    public Alert getAlert()
    {
        return alert;
    }
}
