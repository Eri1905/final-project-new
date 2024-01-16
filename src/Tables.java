import java.util.ArrayList;
import java.util.Arrays;

public class Tables {
    int tableNum;
    boolean isTableTaken;
    Order order;

    public Tables(int tableNum, boolean isTableTaken) {
        this.tableNum = tableNum;
        this.isTableTaken = isTableTaken;
        order = new Order();
    }


/*public String arrayString(ArrayList<Order> order) {
    for (int i = 0; i < order.size() ; i++) {
        System.out.println(order.get(i));
    }

}*/
    @Override
    public String toString() {
        return "Table "+ tableNum +order.toString();
    }
}

