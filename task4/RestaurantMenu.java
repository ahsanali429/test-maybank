package task4;

import java.util.*;
public class RestaurantMenu {
    private List<MenuItem> menuItems;

    public RestaurantMenu() {
        menuItems = new ArrayList<>();
    }

    // Method to add menu items
    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    // Natural sorting by category
    public void sortByCategory() {
        Collections.sort(menuItems, Comparator.comparing(MenuItem::getCategory));
    }

    // Sorting by price
    public void sortByPrice() {
        Collections.sort(menuItems, Comparator.comparingDouble(MenuItem::getPrice));
    }

    // Sorting by name
    public void sortByName() {
        Collections.sort(menuItems, Comparator.comparing(MenuItem::getName));
    }

    // Display the menu items
    public void displayMenu() {
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantMenu menu = new RestaurantMenu();

        // Adding items to the menu
        System.out.println("Enter number of menu items:");
        int itemCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < itemCount; i++) {
            System.out.println("Enter item name:");
            String name = scanner.nextLine();
            System.out.println("Enter item category:");
            String category = scanner.nextLine();
            System.out.println("Enter item price:");
            double price = Double.parseDouble(scanner.nextLine());

            MenuItem item = new MenuItem(name, category, price);
            menu.addItem(item);
        }

        // Sorting options
        System.out.println("\nHow would you like to sort the menu? Select a number from 1,2,3");
        System.out.println("1. By Category");
        System.out.println("2. By Price");
        System.out.println("3. By Name");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                menu.sortByCategory();
                break;
            case 2:
                menu.sortByPrice();
                break;
            case 3:
                menu.sortByName();
                break;
            default:
                System.out.println("Invalid choice.");
        }

        // Display the sorted menu
        System.out.println("\nRestaurant Menu:");
        menu.displayMenu();

        scanner.close();
    }
}
