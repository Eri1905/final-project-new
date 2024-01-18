import java.util.ArrayList;
import java.util.Arrays;

public class Tables {
    private int tableNum;
    private boolean isTableTaken;
    private Order order;

    public Tables(int tableNum, boolean isTableTaken) {
        this.tableNum = tableNum;
        this.isTableTaken = isTableTaken;
        order = new Order();
    }

    public int getTableNum() {
        return tableNum;
    }

    public boolean isTableTaken() {
        return isTableTaken;
    }

    public void setTableTaken(boolean tableTaken) {
        isTableTaken = tableTaken;
    }

    public Order getOrder() {
        return order;
    }


    public Tables() {
    }

    public void addToOrder(int number, String name, double price) {

        switch (number) {
            case 1:
                order.orderItems.add(new Starters(name, price));
                break;
            case 2:
                order.orderItems.add(new MainCourse(name, price));
                break;
            case 3:
                order.orderItems.add(new Desserts(name, price));
                break;
            case 4:
                order.orderItems.add(new Drinks(name, price));
                break;
        }

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

