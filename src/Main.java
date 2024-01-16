import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    /*
    menu.menuItems.add(new MainCourse());
                menu.menuItems.add(new Starters());
    menu.menuItems.add(new Deserts());
        menu.menuItems.add(new Drinks());
        */

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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String PURPLE_BOLD = "\033[1;35m";

    public static void main(String[] args){
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

        Menu menu = makeMenu();


        Waitress waitress = new Waitress();
        waitress.work(menu);

    }
}
