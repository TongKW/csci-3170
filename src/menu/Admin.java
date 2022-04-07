package menu;

import java.sql.*;
import jdbc.Util;

// Admin menu interface
public class Admin {
  private static Connection conn;
  // SQL commands for creating tables
  private static final String CREATE_USER_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS user_category ("
    + "ucid TINYINT UNSIGNED NOT NULL,"
    + "max TINYINT UNSIGNED NOT NULL,"
    + "period TINYINT UNSIGNED NOT NULL,"
    + "PRIMARY KEY(ucid));";
  private static final String CREATE_USER_SQL = "CREATE TABLE IF NOT EXISTS user ("
    + "uid CHAR(12) NOT NULL,"
    + "ucid TINYINT UNSIGNED NOT NULL,"
    + "uname VARCHAR(25) NOT NULL,"
    + "age TINYINT UNSIGNED NOT NULL,"
    + "cooupation VARCHAR(20) NOT NULL,"
    + "PRIMARY KEY(uid),"
    + "FOREIGN KEY (ucid) REFERENCES user_category(ucid));";
  private static final String CREATE_CAR_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS car_category ("
    + "ccid TINYINT UNSIGNED NOT NULL,"
    + "ccname VARCHAR(20) NOT NULL,"
    + "PRIMARY KEY(ccid));";
  private static final String CREATE_CAR_SQL = "CREATE TABLE IF NOT EXISTS car ("
    + "callnum CHAR(8) NOT NULL,"
    + "name VARCHAR(10) NOT NULL,"
    + "manufacture CHAR(10) NOT NULL,"
    + "time_rent TINYINT UNSIGNED NOT NULL,"
    + "ccid TINYINT UNSIGNED NOT NULL,"
    + "PRIMARY KEY (callnum),"
    + "FOREIGN KEY (ccid) REFERENCES car_category(ccid));";
  private static final String CREATE_COPY_SQL = "CREATE TABLE IF NOT EXISTS copy ("
    + "callnum CHAR(8) NOT NULL,"
    + "copynum TINYINT UNSIGNED NOT NULL,"
    + "PRIMARY KEY (callnum, copynum),"
    + "FOREIGN KEY (callnum) REFERENCES car(callnum) ON DELETE CASCADE);";
  private static final String CREATE_RENT_SQL = "CREATE TABLE IF NOT EXISTS rent ("
    + "uid CHAR(12) NOT NULL,"
    + "callnum CHAR(8) NOT NULL,"
    + "copynum TINYINT UNSIGNED NOT NULL,"
    + "checkout CHAR(10) NOT NULL,"
    + "return_date CHAR(10),"
    + "PRIMARY KEY (uid, callnum, copynum, checkout));";
  private static final String CREATE_PRODUCE_SQL = "CREATE TABLE IF NOT EXISTS produce ("
    + "cname VARCHAR(25) NOT NULL,"
    + "callnum CHAR(8) NOT NULL,"
    + "PRIMARY KEY (cname, callnum));";

  // SQL commands for deleting tables
  private static final String DELETE_TABLES_SQL = "DROP TABLE user_category, user, car_category, car, copy, rent, produce;";

  public Admin() {
    Util jdbcUtil = new Util();
    conn = Util.get_conn();
  }
  
  public static void create_tables() {
    try {
      Statement stmt = conn.createStatement();
      System.out.print("Processing... ");
      stmt.executeUpdate(CREATE_USER_CATEGORY_SQL);
      stmt.executeUpdate(CREATE_USER_SQL);
      stmt.executeUpdate(CREATE_CAR_CATEGORY_SQL);
      stmt.executeUpdate(CREATE_CAR_SQL);
      stmt.executeUpdate(CREATE_COPY_SQL);
      stmt.executeUpdate(CREATE_RENT_SQL);
      stmt.executeUpdate(CREATE_PRODUCE_SQL);
      System.out.println("Done. Database is initialized.");
      return;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return;
  }

  public static void delete_tables() {
    try {
      Statement stmt = conn.createStatement();
      System.out.print("Processing... ");
      stmt.executeUpdate("SET foreign_key_checks = 0;");
      stmt.executeUpdate(DELETE_TABLES_SQL);
      stmt.executeUpdate("SET foreign_key_checks = 1;");
      System.out.println("Done. Database is removed.");
      return;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return;
  }
}                     
