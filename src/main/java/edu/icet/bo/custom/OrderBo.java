package edu.icet.bo.custom;
import edu.icet.bo.SuperBo;
import edu.icet.model.Order;
import javafx.collections.ObservableList;

public interface OrderBo extends SuperBo {
    String generateOrderId();
    boolean insertOrder(Order order);
    ObservableList getAllOrders();
    boolean deleteOrderById(String text);
    Order searchOrderByID(String id);
}
