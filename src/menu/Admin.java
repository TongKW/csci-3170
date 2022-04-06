package menu;

import java.sql.*;
import jdbc.Util;

// Admin menu interface
public class Admin {
  private static Connection conn;
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

  public Admin() {
    JDBCUtil jdbcUtil = new JDBCUtil();
    conn = jdbcUtil.get_conn();
  }

  public static void create_tables() {
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

  public static void delete_tables() {
    Statement stmt = conn.createStatement();
    System.out.print("Processing... ");
    stmt.executeUpdate("DROP TABLE user_category;");
    stmt.executeUpdate("DROP TABLE user;");
    stmt.executeUpdate("DROP TABLE car_category;");
    stmt.executeUpdate("DROP TABLE car;");
    stmt.executeUpdate("DROP TABLE copy;");
    stmt.executeUpdate("DROP TABLE rent;");
    stmt.executeUpdate("DROP TABLE produce;");
    System.out.println("Done. Database is initialized.");
    return;
  }
}
  
