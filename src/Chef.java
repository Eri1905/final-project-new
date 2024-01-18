import java.util.ArrayList;
import java.util.Scanner;

public class Chef extends Staff {
    private Order orderCooking;

    public Chef() {
    }

    public void work(ArrayList<Tables> tables) {
        while (true) {
            Scanner sc = new Scanner(System.in);

            System.out.println("What do you want to do: ");
            System.out.println("1) See all active orders");
            System.out.println("2) Change status of an order");
            System.out.println("-1) Leave");
            System.out.println("Enter: ");
            int response = sc.nextInt();

            switch (response) {
                case 1:
                    seeActiveOrders(tables);
                    break;
                case 2:
                    changeStatus(tables);
                    break;
                case -1:
                    break;
            }
            if (response == -1) {
                break;
            }
        }
    }

    private void seeActiveOrders(ArrayList<Tables> tables) {
        System.out.println("Active orders are: ");

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                System.out.println(tables.get(i));
                System.out.println();
            }
        }

    }

    private void changeStatus(ArrayList<Tables> tables) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Which table's order status you want to change: ");
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                System.out.println("Table " + (i + 1));
            }
            System.out.print("Enter: ");
            int tableNum = sc.nextInt();

            System.out.println();
            System.out.println(tables.get(tableNum - 1));
            System.out.println();
            System.out.println("Please enter the status you want to change the order to:");
            System.out.println("1) Preparing");
            System.out.println("2) Ready");
            int response = sc.nextInt();

            switch (response) {
                case 1:
                    tables.get(tableNum - 1).getOrder().setOrderStatus(OrderStatus.PREPARING);
                    break;
                case 2:
                    tables.get(tableNum - 1).getOrder().setOrderStatus(OrderStatus.READY);
                    break;
            }
        }


    }
}
