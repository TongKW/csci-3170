import java.util.Scanner;
import java.sql.*;
import jdbc.Util;
import util.Font;
import menu.Admin;
import menu.Manager;
import menu.User;

class Main {
  public static void main(String[] args) {
    main_menu();
  }

  private static void main_menu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\nWelcome to Car Renting System:\n");
    System.out.println("----- Main Menu -----");
    System.out.println("What kind of operations would you like to perform?");
    System.out.println("1. Operations for Administrator");
    System.out.println("2. Operations for User");
    System.out.println("3. Operations for Manager");
    System.out.println("4. Exit this program");
    System.out.print("Enter your choice: ");
    if (sc.hasNextInt()) {
      int ops = sc.nextInt();
      // Valid inputs
      if (ops == 1) {
        admin_menu();
	return;
      } else if (ops == 2) {
	user_menu();
	return;
      } else if (ops == 3) {
        manager_menu();
	return;
      } else if (ops == 4) {
        return;
      }
    }
    main_menu();
  }

  private static void admin_menu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\n-----Operaions for Administrator Menu-----\n");
    System.out.println("1. Create all tables");
    System.out.println("2. Delete all tables");
    System.out.println("3. Load from data file");
    System.out.println("4. Show number of records in each table");
    System.out.println("5. Return to the main menu");
    System.out.print("Enter your choice: ");
    if (sc.hasNextInt()) {
      int ops = sc.nextInt();
      Admin admin = new Admin();
      // Valid inputs
      if (ops == 1) {
        admin.create_tables();
      } else if (ops == 2) {
	admin.delete_tables();
      } else if (ops == 3) {
	admin.load_datasets();
      } else if (ops == 4) {
	admin.show_all();
      } else if (ops == 5) {
        main_menu();
	return;
      }
    }
    admin_menu();
  }  

  private static void user_menu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\n-----Operaions for User Menu-----\n");
    System.out.println("What kind of operations would you like to perform?");
    System.out.println("1. Search for Cars");
    System.out.println("2. Show loan record of a user");
    System.out.println("3. Return to the main menu");
    if (sc.hasNextInt()) {
      int ops = sc.nextInt();
      User user = new User();
      // Valid inputs
      if (ops == 1) {
        user.search_car();
      } else if (ops == 2) {
        user.show_loan();
      } else if (ops == 3) {
        main_menu();
        return;
      }
    }
    user_menu();
  }
  
  private static void manager_menu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\n-----Operaions for Manager Menu-----\n");
    System.out.println("1. Car Renting");
    System.out.println("2. Car Returning");
    System.out.println("3. List all un-returned car copies which are checked-out within a period");
    System.out.println("4. Return to the main menu");
    System.out.print("Enter your choice: ");
    if (sc.hasNextInt()) {
      int ops = sc.nextInt();
      Manager manager = new Manager();
      // Valid inputs
      if (ops == 1) {
        manager.CarRenting();
      } else if (ops == 2) {
        manager.CarReturning();
      } else if (ops == 3) {
        //manager.list_all_unreturned_car_copies()
      } else if (ops == 4) {
        main_menu();
        return;
      }
    }
    manager_menu();
  }
}

