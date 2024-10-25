package edu.icet.bo.custom;
import edu.icet.bo.SuperBo;
import edu.icet.model.Item;
import edu.icet.model.Supplier;
import javafx.collections.ObservableList;

public interface ItemBo extends SuperBo {
    String generateItemId();
    boolean insertItem(Item item);
    ObservableList getAllUsers();
    boolean updateItem(Item item);
    boolean deleteItemById(String text);
    Item searchItemByName(String name);
    Item searchItemByID(String id);
}
