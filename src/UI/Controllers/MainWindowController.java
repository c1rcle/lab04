package UI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainWindowController
{
    @FXML
    private BorderPane mainBorderPane;

    public void menuItemPressed(ActionEvent actionEvent) throws IOException
    {
        MenuItem source = (MenuItem) actionEvent.getSource();
        switch (source.getId())
        {
            case "menuItemList":
                loadNewScene("listWindowLayout");
                break;
            case "menuItemGrades":
                loadNewScene("gradesWindowLayout");
                break;
            case "menuItemAbsence":
                loadNewScene("absenceWindowLayout");
                break;
            case "menuItemData":
                loadNewScene("dataWindowLayout");
                break;
        }
    }

    private void loadNewScene(String layout) throws IOException
    {
        mainBorderPane.setCenter(null);
        GridPane pane = FXMLLoader.load(getClass().getResource("/UI/Resources/" + layout + ".fxml"));
        mainBorderPane.setCenter(pane);
    }
}
