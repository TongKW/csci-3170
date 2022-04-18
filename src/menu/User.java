package menu;

import java.sql.*;
import jdbc.Util;
import java.util.*;
import java.time.*;
import java.time.format.*;

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
    System.out.print("Choose the search criterion: ");
    Scanner scanner = new Scanner(System.in);
    Integer option = scanner.nextInt();
    switch (option) {
      case 1:
        System.out.print("Type in the search keyword (call number): ");
        String callnum = scanner.next();
        System.out.println("|Call Num|Name|Car Category|Company|Available No. of Copy|");
        search_call_num(callnum);
        break;
      case 2:
        System.out.println("Type in the search keyword (name):");
        String name = scanner.next();
        System.out.println("|Call Num|Name|Car Category|Company|Available No. of Copy|");
        search_car_name(name);
        break;
      case 3:
        System.out.println("Type in the search keyword (company):");
        String company = scanner.next();
        System.out.println("|Call Num|Name|Car Category|Company|Available No. of Copy|");
        search_car_company(company);
        break;
    }
  }

  private static void search_call_num(String callnum) {
    String query = "select name, ccid from car where callnum = '" + callnum + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        //String callnum = rs.getString("callnum");
        String name = rs.getString("name");
        int ccid = rs.getInt("ccid");
        String ccname = ccid_to_ccname(ccid);
        String company = callnum_to_cname(callnum);
        int copynum = callnum_to_copynum(callnum);
	int rented_copy = get_rent_copy(callnum);
        System.out.println("|" + callnum + "|" + name +
                           "|" + ccname + "|" + company + "|" + (copynum - rented_copy) + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private static void search_car_name(String name) {
    String query = "select callnum, ccid from car where name = '" + name + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        //String name = rs.getString("name");
        int ccid = rs.getInt("ccid");
        String ccname = ccid_to_ccname(ccid);
        String company = callnum_to_cname(callnum);
        int copynum = callnum_to_copynum(callnum);
	int rented_copy = get_rent_copy(callnum);
        System.out.println("|" + callnum + "|" + name +
                           "|" + ccname + "|" + company + "|" + (copynum - rented_copy) + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private static void search_car_company(String cname) {
    String query = "select callnum from produce where cname = '" + cname + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        search_call_num(callnum);
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
    System.out.println("|CallNum|CopyNum|Name|Company|Check-out|Return?|");
    String query = "select callnum, copynum, checkout, return_date from rent where uid = '" + userID + "';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String callnum = rs.getString("callnum");
        int copynum = rs.getInt("copynum");
        String name = callnum_to_name(callnum);
        String cname = callnum_to_cname(callnum);
        java.sql.Date checkout = rs.getDate("checkout");
        java.sql.Date return_date = rs.getDate("return_date");
	String checkout_str = (checkout == null) ? "NULL" : checkout.toString();
	String return_date_str = (return_date == null) ? "NULL" : return_date.toString();
        System.out.println("|" + callnum + "|" + copynum + "|" + name +
                           "|" + cname + "|" + checkout_str + "|" + return_date_str + "|");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private static String callnum_to_cname(String callnum){ //cname is company name
    String cname = new String();
    String query = "select cname from produce where callnum = '" + callnum +"';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        cname = rs.getString("cname");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cname;
  }

  private static Integer callnum_to_copynum(String callnum){ //copynum is number of copies of a car
    Integer copynum = 0;
    String query = "select copynum from copy where callnum = '" + callnum +"';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        copynum = rs.getInt("copynum");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return copynum;
  }

  private static Integer get_rent_copy(String callnum) {
    Integer copy = 0;
    String query = "SELECT SUM(copynum) FROM rent WHERE (callnum = '" + callnum +"' AND return_date IS NULL);";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      rs.last();
      copy = rs.getInt("SUM(copynum)");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return copy;
  }

  private static String ccid_to_ccname(Integer ccid){ //ccname is type of vehicle, i.e truck
    String ccname = new String();
    String query = "select ccname from car_category where ccid = '" + ccid +"';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        ccname = rs.getString("ccname");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ccname;
  }

  private static String callnum_to_name(String callnum){ //name is name of vehicle, i.e car2
    String name = new String();
    String query = "select name from car where callnum = '" + callnum +"';";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        name = rs.getString("name");
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return name;
  }

  private static String return_check(String return_date){
    String result = new String();
    try{
      DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate start = LocalDate.parse(return_date,f);
      LocalDate today = LocalDate.now();
      // result = start.format(f); //for debug
      boolean isBefore = start.isBefore( today ) ;
      if (isBefore == true){
        result = "Yes";
      }else{
        result =  "No";
      }
    }catch(DateTimeParseException e){
      e.printStackTrace();
    }
    return result;  
  }
} 
