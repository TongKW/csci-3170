import java.util.Scanner;
import java.sql.*;

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
	admin.load_data();
      } else if (ops == 4) {
	admin.show_records();
      } else if (ops == 5) {
        main_menu();
	return;
      }
    }
    admin_menu();
  }  

  private static void user_menu() {
    System.out.println("\n-----Operaions for User Menu-----\n");
    System.out.println("What kind of operations would you like to perform?");
    System.out.println("1. Search for Cars");
    System.out.println("2. Show loan record of a user");
    System.out.println("3. Return to the main menu");
    if (sc.hasNextInt()) {
      int ops = sc.nextInt();
      // Valid inputs
      if (ops == 1) {
      } else if (ops == 2) {
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
      // Valid inputs
      if (ops == 1) {
      } else if (ops == 2) {
      } else if (ops == 3) {
      } else if (ops == 4) {
        main_menu();
        return;
      }
    }
    manager_menu();
  }
}

// Admin menu interface
class Admin {
  private Connection conn;
  // SQL commands for creating tables
  private static final String CREATE_USER_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS user_category ("
    + "ucid NUMBER(1,0) UNSIGNED NOT NULL,"
    + "max NUMBER(1,0) UNSIGNED NOT NULL,"
    + "period NUMBER(2,0) UNSIGNED NOT NULL);";
  private static final String CREATE_USER_SQL = "CREATE TABLE IF NOT EXISTS user ("
    + "uid CHAR(12) NOT NULL,"
    + "ucid NUMBER(1,0) UNSIGNED NOT NULL,"
    + "uname VARCHAR(25) NOT NULL,"
    + "age NUMBER(2,0) UNSIGNED NOT NULL,"
    + "cooupation VARCHAR(20) NOT NULL,"
    + "PRIMARY KEY(uid),"
    + "FOREIGN KEY (ucid) REFERENCES user_category(ucid));";
  private static final String CREATE_CAR_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS car_category ("
    + "ccid NUMBER(1,0) UNSIGNED NOT NULL,"
    + "ccname VARCHAR(20) NOT NULL);";
  private static final String CREATE_CAR_SQL = "CREATE TABLE IF NOT EXISTS car ("
    + "callnum CHAR(8) NOT NULL,"
    + "name VARCHAR(10) NOT NULL,"
    + "manufacture CHAR(10) NOT NULL,"
    + "time_rent NUMBER(2,0) UNSIGNED NOT NULL,"
    + "ccid NUMBER(1,0) UNSIGNED NOT NULL,"
    + "PRIMARY KEY (callnum),"
    + "FOREIGN KEY (ccid) REFERENCES car_category(ccid));";
  private static final String CREATE_COPY_SQL = "CREATE TABLE IF NOT EXISTS copy ("
    + "callnum CHAR(8) NOT NULL,"
    + "copynum NUMBER(1,0) UNSIGNED NOT NULL,"
    + "PRIMARY KEY (callnum, copynum),"
    + "FOREIGN KEY (callnum) REFERENCES car(callnum) ON DELETE CASCADE);";
  private static final String CREATE_RENT_SQL = "CREATE TABLE IF NOT EXISTS rent ("
    + "uid CHAR(12) NOT NULL,"
    + "callnum CHAR(8) NOT NULL,"
    + "copynum NUMBER(1,0) UNSIGNED NOT NULL,"
    + "checkout CHAR(10) NOT NULL,"
    + "return CHAR(10),"
    + "PRIMARY KEY (uid, callnum, copynum, checkout));";
  private static final String CREATE_PRODUCE_SQL = "CREATE TABLE IF NOT EXISTS produce ("
    + "cname VARCHAR(25) NOT NULL,"
    + "callnum CHAR(8) NOT NULL,"
    + "PRIMARY KEY (cname, callnum));";

  public Admin {
    JDBCUtil jdbcUtil = new JDBCUtil();
    conn = jdbcUtil.get_conn();
  }
  public static void create_table() {
     
  }
}


class JDBCUtil {
  private static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db16";
  private static String dbUsername = "Group16";
  private static String dbPassword = "gp16as#Pm";

  public Connection get_conn() {
    // Get connection
    Connection conn = null;
    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
    } catch (ClassNotFoundException e) {
        System.out.println(e);
        System.out.println(Font.ANSI_BOLD + Font.ANSI_RED + "[Error]" + Font.ANSI_RESET+ Font.ANSI_BOLD_RESET + ": Java MySQL DB Driver not found");
        System.exit(0);
    } catch (SQLException e) {
        System.out.println(e);
    }
    return conn;
  }
}

class Font {
   public static final String ANSI_BOLD = "\033[1m";
   public static final String ANSI_BOLD_RESET = "\033[0m";
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_WHITE_UNDERLINED = "\033[4;37m";
   public static final String ANSI_BLACK_UNDERLINED = "\033[4;30m";
   public static final String ANSI_UNDERLINE_RESET = "\033[0;0m";
}
