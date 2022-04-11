package menu;

import java.sql.*;
import jdbc.Util;
import java.util.*;

public class User {
  private static Connection conn;
  public User() {
    Util jdbcUtil = new Util();
    conn = Util.get_conn();
  }

  public static void search_car() {
    System.out.println("Choose the search criterion:");
    System.out.println("1. call number");
    System.out.println("2. name");
    System.out.println("3. company");
    System.out.println("Choose the search criterion:");
    Scanner scanner = new Scanner(System.in);
    Integer option = scanner.nextInt();
    switch (option) {
      case 1:
        System.out.println("Type in the search keyword (call number):");
        String callnum = scanner.next();
        search_call_num(callnum);
        break;
      case 2:
        System.out.println("Type in the search keyword (name):");
        String name = scanner.next();
        search_car_name(name);
        break;
      case 3:
        System.out.println("Type in the search keyword (company):");
        String company = scanner.next();
        search_car_company(company);
        break;
    }
  }

  private static void search_call_num(String callnum) {
    System.out.println("|Call Num|Name|CCID(Car Cat)|Company|Available No. of Copy|");
    String query = "select name, company, copynum, ccid from car where callnum = '" + callnum + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        //String callnum = rs.getString("callnum");
        String company = rs.getString("company");
        String name = rs.getString("name");
        int ccid = rs.getInt("ccid");
        int copynum = rs.getInt("copynum");
        System.out.println("|" + callnum + "|" + name +
                           "|" + ccid + "|" + company + "|" + copynum + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private static void search_car_name(String name) {
    System.out.println("|Call Num|Name|CCID(Car Cat)|Company|Available No. of Copy|");
    String query = "select callnum, company, copynum, ccid from car where name = '" + name + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        String company = rs.getString("company");
        //String name = rs.getString("name");
        int ccid = rs.getInt("ccid");
        int copynum = rs.getInt("copynum");
        System.out.println("|" + callnum + "|" + name +
                           "|" + ccid + "|" + company + "|" + copynum + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private static void search_car_company(String company) {
    System.out.println("|Call Num|Name|CCID(Car Cat)|Company|Available No. of Copy|");
    String query = "select callnum, name, copynum, ccid from car where company = '" + company + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        //String company = rs.getString("company");
        String name = rs.getString("name");
        int ccid = rs.getInt("ccid");
        int copynum = rs.getInt("copynum");
        System.out.println("|" + callnum + "|" + name +
                           "|" + ccid + "|" + company + "|" + copynum + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public static void show_loan() {
    System.out.print("Enter The User ID:");
    Scanner scanner = new Scanner(System.in);
    String userID = scanner.next();
    show_rent_record(userID);
  }

  private static void show_rent_record(String userID) {
    System.out.println("Loan Record:");
    System.out.println("|CallNum|CopyNum|Checkout|ReturnDate|");
    String query = "select uid, callnum, copynum, checkout, return_date from rent where uid = '" + userID + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        int copynum = rs.getInt("copynum");
        String checkout = rs.getString("checkout");
        String return_date = rs.getString("return_date");

        System.out.println("|" + callnum + "|" + copynum +
                           "|" + checkout + "|" + return_date + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
} 
