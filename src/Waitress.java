import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Waitress extends Staff {
    private ArrayList<Tables> tablesWorking;
    private Menu menu;
    private Order order;
    public Path path = Paths.get("src/activeOrders.txt");


    public Waitress() {

    }


    public void work(Menu menu, ArrayList<Tables> tables) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter what do you want do ");
            System.out.println("1) Check for free table and get a order");
            System.out.println("2) Add to menu");
            System.out.println("3) Add to order");
            System.out.println("4) Remove from order");
            System.out.println("5) Calculate sum of table");
            System.out.println("-1) Leave");
            System.out.print("Enter: ");
            int answer = sc.nextInt();


            switch (answer) {
                case 1:
                    checkForFreeTable(tables, menu);
                    break;
                case 2:
                    menu.writeAddedDishToFile();
                    break;
                case 3:
                    addToOrder(tables, menu);
                    break;
                case 4:
                    removeFromOrder(tables);
                    break;
                case 5:
                    calculateSumOfTable(tables);
                    break;
                case -1:
                    break;
            }
            if (answer == -1) {
                break;
            }
        }
    }

    private void checkForFreeTable(ArrayList<Tables> tables, Menu menu) {
        boolean allTablesTaken = false;
        int takenTables = 0;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                takenTables += 1;
            }
        }
        if (takenTables == tables.size()) {
            allTablesTaken = true;
        }
        if (allTablesTaken == true) {
            System.out.println("All Tables are taken !");
        } else {
            System.out.println("Free tables are: ");
            for (int i = 0; i < tables.size(); i++) {
                if (tables.get(i).isTableTaken() == false)
                    System.out.println("Table " + (i + 1));
            }
        }
        int currentTableNumber;
        System.out.println("Enter the number of the table: ");
        while (true) {
            currentTableNumber = sc.nextInt();
            if (tables.get(currentTableNumber - 1).isTableTaken() == true) {
                System.out.println(" Please enter the number of free table: ");
            } else {
                break;
            }
        }
        takeOrder(tables.get(currentTableNumber - 1), menu);

    }

    public void calculateSumOfTable(ArrayList<Tables> tables) {

        Scanner sc = new Scanner(System.in);
        int tableNum;
        double sumOfTable = 0;
        System.out.println("Which table Total to sum: ");

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                System.out.println("Table " + (i + 1));
            }
        }
        System.out.print("Enter: ");
        tableNum = sc.nextInt();

        for (int i = 0; i < tables.get(tableNum - 1).getOrder().orderItems.size(); i++) {
            sumOfTable += tables.get(tableNum - 1).getOrder().orderItems.get(i).price;
        }

        System.out.println("Table " + tableNum + " TOTAL: " + sumOfTable);

        int lineOfTable = findLineInFile(tables.get(tableNum - 1));
        int lastDishLine = (tables.get(tableNum - 1).getOrder().orderItems.size()) + lineOfTable;

        removeTableFromFile(lineOfTable - 1, lastDishLine);

        tables.get(tableNum - 1).getOrder().orderItems.clear();
        tables.get(tableNum - 1).setTableTaken(false);
        tables.get(tableNum - 1).getOrder().setOrderStatus(OrderStatus.PAID);
    }

    public void takeOrder(Tables table, Menu menu) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("The date and time is(dd-MM-yyyy HH:mm:ss): ");
            table.getOrder().setCreationDateTimeFromString(sc.nextLine());

            int answer;

            for (int i = 0; i < menu.menuItems.size(); i++) {
                System.out.println((i + 1) + ") " + menu.menuItems.get(i).name + ", price: " + menu.menuItems.get(i).price);
            }
            System.out.println();
            System.out.println("Enter number of product(if your order is finished write '-1'): ");
            while (true) {

                answer = sc.nextInt();
                if (answer == -1) {
                    break;
                }
                table.getOrder().orderItems.add(menu.menuItems.get(answer - 1));
            }
            writeToFileTheOrder(table);
            table.setTableTaken(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addToOrder(ArrayList<Tables> tables, Menu menu) {

        Scanner sc = new Scanner(System.in);
        int tableNum;
        System.out.println("In which table to add: ");
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                System.out.println("Table " + (i + 1));
            }
        }
        System.out.print("Enter: ");
        tableNum = sc.nextInt();

        int answer;
        for (int i = 0; i < menu.menuItems.size(); i++) {
            System.out.println((i + 1) + ") " + menu.menuItems.get(i).name + ", price: " + menu.menuItems.get(i).price);
        }
        System.out.println();
        System.out.println("Enter number of product(if your order is finished write '-1'): ");
        while (true) {

            answer = sc.nextInt();
            if (answer == -1) {
                break;
            }
            tables.get(tableNum - 1).getOrder().orderItems.add(menu.menuItems.get(answer - 1));
            writeAddedDish(tables.get(tableNum - 1));
        }
    }

    private void removeFromOrder(ArrayList<Tables> tables) {

        Scanner sc = new Scanner(System.in);
        int tableNum;
        System.out.println("In which table to remove: ");
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken() == true) {
                System.out.println("Table " + (i + 1));
            }
        }
        System.out.print("Enter: ");
        tableNum = sc.nextInt();


        for (int i = 0; i < tables.get(tableNum - 1).getOrder().orderItems.size(); i++) {
            System.out.println(i + 1 + ") " + tables.get(tableNum - 1).getOrder().orderItems.get(i).toString());
        }
        System.out.println();
        System.out.print("Enter which element to remove: ");
        int num = sc.nextInt();

        tables.get(tableNum - 1).getOrder().orderItems.remove(num - 1);

        int lineOfTable = findLineInFile(tables.get(tableNum - 1));

        int lineOfDish = lineOfTable + num;
        removeDishFromTableFile(lineOfDish);
    }

    private void removeDishFromTableFile(int line) {
        try {
            List<String> lines = Files.readAllLines(path);

            if (line >= 1 && line <= lines.size()) {
                lines.remove(line - 1);

                Files.write(path, lines);


            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void writeAddedDish(Tables table) {
        try {

            String newContent = table.getOrder().orderItems.get(table.getOrder().orderItems.size() - 1).toString();
            int size = (findLineInFile(table) + (table.getOrder().orderItems.size())) - 1;

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            if (lines.size() >= size) {
                lines.add(size, newContent);
            } else {
                while (lines.size() < size + 1) {
                    lines.add("");
                    lines.add(newContent);
                }
            }

            Files.write(path, lines, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findLineInFile(Tables table) {
        String searchText = "Table " + table.getTableNum();

        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;


            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.equalsIgnoreCase(searchText)) {
                    return lineNumber;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineNumber;
    }


    private void writeToFileTheOrder(Tables table) {

        try {
            FileWriter fileWriter = new FileWriter(path.toFile(), true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(table));
            bufferedWriter.newLine();


            bufferedWriter.close();

            table.getOrder().setOrderStatus(OrderStatus.SERVED);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeTableFromFile(int firstline, int lastLine) {
        try {
            List<String> lines = Files.readAllLines(path);

            if (firstline >= 1 && firstline <= lines.size() &&
                    lastLine >= 1 && lastLine <= lines.size() && lastLine >= firstline) {

                lines.subList(firstline - 1, lastLine).clear();

                Files.write(path, lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


