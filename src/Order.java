import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
    public LocalDateTime creationDateTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public ArrayList<MenuItems> orderItems;
    private OrderStatus orderStatus;

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCreationDateTimeFromString(String dateTimeString) {
        this.creationDateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    public Order() {
        this.orderItems = new ArrayList<>() ;
    }

    public String toString() {
        String results ="";
        for(MenuItems d : orderItems) {
            results += "\n";
            results += d.toString();
        }
        return results;
    }
}
