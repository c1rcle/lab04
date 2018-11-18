package UI.Cells.Factories;

import UI.Cells.GenericCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class GenericFactory<T> implements Callback<ListView<T>, ListCell<T>>
{
    @Override
    public ListCell<T> call(ListView<T> param)
    {
        return new GenericCell<>();
    }
}
