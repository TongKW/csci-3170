import java.util.Scanner;

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
      // Valid inputs
      if (ops == 1) {
      } else if (ops == 2) {
      } else if (ops == 3) {
      } else if (ops == 4) {
      } else if (ops == 5) {
        main_menu();
	return;
      }
    }
    admin_menu();
  }  

  private static void user_menu() {
    System.out.println("\n-----Operaions for User Menu-----\n");
    System.out.println("1. Create all tables");
  }

  private static void manager_menu() {
    System.out.println("\n-----Operaions for Manager Menu-----\n");
    System.out.println("1. Create all tables");
  }
}
