package UI.Cells;

import javafx.scene.control.ListCell;

public class GenericCell<T> extends ListCell<T>
{
    @Override
    protected void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);

        int index = this.getIndex();
        String name = null;

        if (item != null)
        {
            name = (index + 1) + ". " + item.toString();
        }

        this.setText(name);
        setGraphic(null);
    }
}
