import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static Menu makeMenu() {
        Menu menu = new Menu();

        Scanner sc = null;
        String[] menuItemsCharacteristc;

        try {
            File file = new File("src/menu.txt");
            sc = new Scanner(file);
            int typeOfDish = 0;
            while (sc.hasNext()) {
                menuItemsCharacteristc = sc.nextLine().split(",");
                String name = menuItemsCharacteristc[0];
                double price = Double.parseDouble(menuItemsCharacteristc[1]);
                String type = menuItemsCharacteristc[2];


                if (type.equalsIgnoreCase("Starters")) {
                    typeOfDish = 1;
                } else if (type.equalsIgnoreCase("main")) {
                    typeOfDish = 2;
                } else if (type.equalsIgnoreCase("Dessert")) {
                    typeOfDish = 3;
                } else if (type.equalsIgnoreCase("Drinks")) {
                    typeOfDish = 4;
                }

                menu.addToMenu(typeOfDish, name, price);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        sc.close();
        return menu;
    }

    public static ArrayList<Tables> makeTables() {


        ArrayList<Tables> tablesWorking = new ArrayList<>();
        String filePath = "src/activeOrders.txt";
        Scanner sc = null;


        for (int i = 0; i < 5; i++) {
            tablesWorking.add(new Tables(i + 1, false));
        }

        try {
            File file = new File(filePath);
            sc = new Scanner(file);
            int typeOfDish = 0;
            int tableNum = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains("Table")) {
                    String[] splitTableLine = line.split(" ");
                    tableNum = Integer.parseInt(splitTableLine[1]);
                } else {
                    String[] splitOrders = line.split(",");
                    String name = splitOrders[0];
                    double price = Double.parseDouble(splitOrders[1]);
                    String type = splitOrders[2];


                    if (type.equalsIgnoreCase("Starters")) {
                        typeOfDish = 1;
                    } else if (type.equalsIgnoreCase("main")) {
                        typeOfDish = 2;
                    } else if (type.equalsIgnoreCase("Dessert")) {
                        typeOfDish = 3;
                    } else if (type.equalsIgnoreCase("Drinks")) {
                        typeOfDish = 4;
                    }

                    tablesWorking.get(tableNum - 1).addToOrder(typeOfDish, name, price);
                    tablesWorking.get(tableNum - 1).setTableTaken(true);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        sc.close();

        return tablesWorking;

    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String PURPLE_BOLD = "\033[1;35m";

    public static void main(String[] args) {
        System.out.println(PURPLE_BOLD +
                "     .\n" +
                "    . .\n" +
                "      ...\n" +
                "   \\~~~~~/\n" +
                "    \\   /\n" +
                " W E L C O M E" +
                "      \n" +
                "      |\n" +
                "      |\n" +
                "     ---");


        Scanner sc = new Scanner(System.in);
        Menu menu = makeMenu();
        ArrayList<Tables> tables = makeTables();

        while (true) {
            System.out.println(ANSI_RESET + "Login as: ");
            System.out.println("1) Waitress");
            System.out.println("2) Chef");
            System.out.println("-1) Stop program");
            System.out.print("Enter: ");
            int response = sc.nextInt();

            switch (response) {
                case 1:
                    Waitress waitress = new Waitress();
                    waitress.work(menu, tables);
                    break;
                case 2:
                    Chef chef = new Chef();
                    chef.work(tables);
                case -1:
                    break;
            }
            if (response == -1) {
                break;
            }
        }
    }
}
