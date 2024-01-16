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
    ArrayList<Tables> tablesWorking;
    Menu menu;
    Order order;
    public Path path = Paths.get("src/activeOrders.txt");

    public Waitress() {
        this.tablesWorking = createTables();
    }

    private ArrayList<Tables> createTables() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter tables: ");
        int tableNum = sc.nextInt();
        this.tablesWorking = new ArrayList<>();

        for (int i = 0; i < tableNum; i++) {
            Tables table = new Tables(i + 1, false);
            tablesWorking.add(table);
        }
        return tablesWorking;
    }

    public void work(Menu menu) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter what do you want do ");
            System.out.println("1) Check for free table and get a order");
            System.out.println("2) add to menu");
            System.out.println("3) add to order");
            System.out.println("4) remove from order");
            System.out.println("5) calculate sum of table");
            System.out.println("-1) Stop program");
            System.out.print("Enter: ");
            int answer = sc.nextInt();


            switch (answer) {
                case 1:
                    checkForFreeTable(tablesWorking, menu);
                    break;
                case 2:
                    menu.writeAddedDishToFile();
                    break;
                case 3:
                    addToOrder(tablesWorking, menu);
                    break;
                case 4:
                    removeFromOrder(tablesWorking);
                    break;
                case 5:
                    calculateSumOfTable(tablesWorking);
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
            if (tables.get(i).isTableTaken == true) {
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
                if (tables.get(i).isTableTaken == false)
                    System.out.println("Table " + (i + 1));
            }
        }
        int currentTableNumber;
        System.out.println("Enter the number of the table: ");
        while (true) {
            currentTableNumber = sc.nextInt();
            if (tables.get(currentTableNumber - 1).isTableTaken == true) {
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
            if (tables.get(i).isTableTaken == true) {
                System.out.println("Table " + (i + 1));
            }
        }
        System.out.print("Enter: ");
        tableNum = sc.nextInt();

        for (int i = 0; i < tables.get(tableNum - 1).order.orderItems.size(); i++) {
            sumOfTable += tables.get(tableNum - 1).order.orderItems.get(i).price;
        }

        System.out.println("Table " + tableNum + " TOTAL: " + sumOfTable);

        int lineOfTable = findLineInFile(tables.get(tableNum - 1));
        int lastDishLine =(tables.get(tableNum-1).order.orderItems.size())+lineOfTable;

        removeTableFromFile(lineOfTable-1, lastDishLine);

        tables.get(tableNum - 1).order.orderItems.clear();
        tables.get(tableNum - 1).isTableTaken = false;
    }

    public void takeOrder(Tables table, Menu menu) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("The date and time is(dd-MM-yyyy HH:mm:ss): ");
            table.order.setCreationDateTimeFromString(sc.nextLine());

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
                table.order.orderItems.add(menu.menuItems.get(answer - 1));
            }
            writeToFileTheOrder(table);
            table.isTableTaken = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addToOrder(ArrayList<Tables> tables, Menu menu) {

        Scanner sc = new Scanner(System.in);
        int tableNum;
        System.out.println("In which table to add: ");
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken == true) {
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
        System.out.println("Enter number of product(if your order is finished write '0'): ");
        while (true) {

            answer = sc.nextInt();
            if (answer == 0) {
                break;
            }
            tables.get(tableNum - 1).order.orderItems.add(menu.menuItems.get(answer - 1));
            writeAddedDish(tables.get(tableNum - 1));
        }
    }

    private void removeFromOrder(ArrayList<Tables> tables) {

        Scanner sc = new Scanner(System.in);
        int tableNum;
        System.out.println("In which table to remove: ");
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).isTableTaken == true) {
                System.out.println("Table " + (i + 1));
            }
        }
        System.out.print("Enter: ");
        tableNum = sc.nextInt();


        for (int i = 0; i < tables.get(tableNum - 1).order.orderItems.size(); i++) {
            System.out.println(i + 1 + ") " + tables.get(tableNum - 1).order.orderItems.get(i).toString());
        }
        System.out.println();
        System.out.print("Enter which element to remove: ");
        int num = sc.nextInt();

        tables.get(tableNum - 1).order.orderItems.remove(num - 1);

        int lineOfTable = findLineInFile(tables.get(tableNum - 1));

        int lineOfDish = lineOfTable + num;
        removeDishFromTableFile(lineOfDish);
    }

    private void removeDishFromTableFile(int line) {
        try {
            List<String> lines = Files.readAllLines(path);

            if (line >= 1 && line <= lines.size()) {
                // Remove the specified line
                lines.remove(line - 1);

                // Write the updated content back to the file
                Files.write(path, lines);


            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void writeAddedDish(Tables table) {
        try {

            String newContent = table.order.orderItems.get(table.order.orderItems.size() - 1).toString();
            int size = (findLineInFile(table) + (table.order.orderItems.size())) - 1;

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            if (lines.size() >= size) {
                // Add text to the specified line
                lines.add(size, newContent);
            } else {
                // Append new lines until reaching the specified line number
                while (lines.size() < size + 1) {
                    lines.add("");
                    lines.add(newContent);
                }
            }

            // Write the modified content back to the file
            Files.write(path, lines, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findLineInFile(Tables table) {
        String searchText = "Table " + table.tableNum;

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

            // System.out.println("You added new dish to the menu!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void removeTableFromFile(int firstline, int lastLine) {
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(path);

            // Check if the range is valid
            if (firstline >= 1 && firstline <= lines.size() &&
                    lastLine >= 1 && lastLine <= lines.size() && lastLine >= firstline) {

                // Remove the specified range of lines
                lines.subList(firstline - 1, lastLine).clear();

                // Write the updated content back to the file
                Files.write(path, lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


