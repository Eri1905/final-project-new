import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\033[0;34m";

    public Path path = Paths.get("src/menu.txt");
    ArrayList<MenuItems> menuItems;


    public Menu() {
        this.menuItems = new ArrayList<>();
    }

    private void showStarters() {
        System.out.println(ANSI_YELLOW + " ,+.      ,=|=.     ,+.       ,-\"-. \n" +
                "((|))    (XXXXX)   //|\\\\     / ,-. \\\n" +
                " )|(      |   STARTERS ||    |(:::)|\n" +
                "((|))     \\   /    \\\\|//     \\ `-' /\n" +
                " `-'       `+'      `+'       `-.-'" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("starters"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showMainCourse() {
        System.out.println(ANSI_RED +
                "        _.:`.--|--.`:._\n" +
                "     .: .'\\o  | o /'. '.\n" +
                "    // '.  MAIN COURSES '.\\\n" +
                "    //'._o'. \\ |o/ o_.-'o\\\\\n" +
                "    || o '-.'.\\|/.-' o   ||" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("main"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDeserts() { //fix it for desserts
        System.out.println(ANSI_YELLOW +
                "         (\n" +
                "          )\n" +
                "     __..---..__\n" +
                " ,-='  /  |  \\  `=-.\n" +
                ":--.._DESSERTS_..--;\n" +
                " \\.,_____________,./" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("desserts"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void showDrinks() {
        System.out.println(ANSI_BLUE +
                " .-'---------|  \n" +
                "( C|/\\/\\/\\/\\/|\n" +
                " '-/\\ DRINKS |\n" +
                "   '_________'\n" +
                "    '-------'" + ANSI_RESET);
        try {
            Files.lines(path)
                    .filter(line -> line.contains("drinks"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to see: ");
        System.out.println("1) STARTERS");
        System.out.println("2) MAIN COURSES");
        System.out.println("3) DESSERTS");
        System.out.println("4) DRINKS");

        int number;
        System.out.println("Enter number: ");
        while (true) {
            number = sc.nextInt();
            if (number < 1 || number > 4) {
                System.out.println("Please enter number between 1 and 4: ");
            }
            break;
        }

        switch (number) {
            case 1:
                showStarters();
                break;
            case 2:
                showMainCourse();
                break;
            case 3:
                showDeserts();
                break;
            case 4:
                showDrinks();
                break;
        }
    }

    public void writeAddedDishToFile() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter type of the thing you want to add ");
            System.out.println("1) STARTERS");
            System.out.println("2) MAIN COURSES");
            System.out.println("3) DESSERTS");
            System.out.println("4) DRINKS");
            System.out.println("5) LEAVE !!!");

            int number;
            System.out.print("Enter number: ");
            while (true) {
                number = sc.nextInt();
                if (number < 1 || number > 5) {
                    System.out.print("Please enter number between 1 and 4: ");
                }
                break;
            }
            if (number == 5) {
                break;
            }
            System.out.println("Enter name: ");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.println("Enter price: ");
            double price = sc.nextDouble();

            addToMenu(number, name, price);
            System.out.println("You added new dish to the menu!");
            try {

                String newContent = (menuItems.get((menuItems.size()) - 1)).toString();
                int size = menuItems.size();

                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                if (lines.size() >= size) {
                    lines.add(size - 1, newContent);
                } else {
                    while (lines.size() < size - 1) {
                        lines.add("");
                    }
                    lines.add(newContent);
                }
                Files.write(path, lines, StandardCharsets.UTF_8);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void addToMenu(int number, String name, double price) {

        switch (number) {
            case 1:
                menuItems.add(new Starters(name, price));
                break;
            case 2:
                menuItems.add(new MainCourse(name, price));
                break;
            case 3:
                menuItems.add(new Desserts(name, price));
                break;
            case 4:
                menuItems.add(new Drinks(name, price));
                break;
        }

    }


}

