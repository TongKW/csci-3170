package jdbc;

import java.sql.*;
import util.Font;

public class Util {
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
