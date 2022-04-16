package menu;

import java.sql.*;
import jdbc.Util;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.security.*; 
//import java.sql.Date;
import java.text.ParseException;


public class Manager {
  private static Connection conn;
  public Manager() {
    Util jdbcUtil = new Util();
    conn = Util.get_conn();
  }

  public static void CarRenting(){
  try {
    System.out.println(" Enter the User ID:");
    Scanner scannerM1 = new Scanner(System.in);
    String Input_UserID = scannerM1.next();
    
    // user id check
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user where uid= ?" );
    stmt.setString(1, Input_UserID);
    if (!stmt.executeQuery().next()) {
      System.out.println("Invalid input");
      return;
    }

    System.out.println(" Enter the Call Number:");
    String Input_CallNum = scannerM1.next();
    System.out.println(" Enter the Copy Number:");
    int Input_CopyNum = scannerM1.nextInt();
    
    //check whether the car exists
    stmt = conn.prepareStatement("SELECT * FROM copy WHERE callnum = ? AND copynum = ?");
    stmt.setString(1, Input_CallNum);
    stmt.setInt(2, Input_CopyNum);
    if (!stmt.executeQuery().next()) {
      System.out.println("Invalid input");
      return;
    }

    //check whether the car is rented
    stmt = conn.prepareStatement("SELECT * FROM rent WHERE callnum = ? AND copynum = ? AND return_date is NULL");
    stmt.setString(1, Input_CallNum );
    stmt.setInt(2, Input_CopyNum);
    if (stmt.executeQuery().next()) {
      System.out.println("This car is not available to be rented");
      return;
    }

    //add the new check out record
    stmt = conn.prepareStatement("INSERT INTO rent(callnum, copynum, uid, checkout, return_date) VALUES (?,?,?,?,NULL)");
    stmt.setString(1, Input_CallNum );
    stmt.setInt(2, Input_CopyNum);
    stmt.setString(3, Input_UserID );
    stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
    stmt.execute();
    System.out.println("car renting performed successfully.");
    } catch (SQLException e) {
            System.out.println("[Error] .\n");
        }
  }

  public static void CarReturning(){
  try {
    Scanner scannerM2 = new Scanner(System.in);
    System.out.println(" Enter the User ID:");
    String Input_UserID = scannerM2.next();
    
    // user id check
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user where uid= ?" );
    stmt.setString(1,  Input_UserID);
    if (!stmt.executeQuery().next()) {
      System.out.println("Invalid input");
      return;
    }

    System.out.println(" Enter the Call Number:");
    String Input_CallNum = scannerM2.next();
    System.out.println(" Enter the Copy Number:");
    int Input_CopyNum = scannerM2.nextInt();
    
    //check whether the car exists
    stmt = conn.prepareStatement("SELECT * FROM copy WHERE callnum = ? AND copynum = ?");
    stmt.setString(1, Input_CallNum);
    stmt.setInt(2, Input_CopyNum);
    if (!stmt.executeQuery().next()) {
      System.out.println("Invalid input");
      return;
    }

    // check whether the car was rented by this user
    stmt = conn.prepareStatement("SELECT * FROM rent WHERE callnum = ? AND copynum = ? AND uid =? AND return_date is NULL");
    stmt.setString(1, Input_CallNum);
    stmt.setInt(2, Input_CopyNum);
    stmt.setString(3, Input_UserID );
    if (!stmt.executeQuery().next()) {
      System.out.println("Invalid input");
      return;
    }

    // updated the return_date
    stmt = conn.prepareStatement("UPDATE rent SET return_date = ? WHERE callnum = ? AND copynum = ? AND uid =? AND return_date is NULL");
    stmt.setDate(1, new java.sql.Date(System.currentTimeMillis()) );
    stmt.setString(2, Input_CallNum);
    stmt.setInt(3, Input_CopyNum);
    stmt.setString(4, Input_UserID);
    stmt.execute();
    System.out.println("car returning performed successfully.");
     } catch (SQLException e) {
            System.out.println("[Error] \n");
        }
  }

  public static void list_all_unreturned_car_copies() {
    
  try {
    Scanner scannerM3 = new Scanner(System.in);
    System.out.println(" Type in the starting date [dd/mm/yyyy]:");
    String Input_Start_date = scannerM3.next();
    System.out.println(" Type in the ending date [dd/mm/yyyy]:");
    String Input_end_date = scannerM3.next();

    // util.Date to sql.Date
    java.sql.Date SqlDate_Start_date =new java.sql.Date(dMy_string_to_Utildate(Input_Start_date).getTime());
    java.sql.Date SqlDate_end_date =new java.sql.Date(dMy_string_to_Utildate(Input_end_date).getTime());

    PreparedStatement stmt = conn.prepareStatement("SELECT callnum, copynum, uid, checkout FROM rent WHERE  checkout >= ? AND checkout <= ? AND return_date IS NULL ORDER BY checkout DESC");
    stmt.setDate(1, SqlDate_Start_date );
    stmt.setDate(2, SqlDate_end_date );
    ResultSet rs = stmt.executeQuery();

    System.out.println("|UID|CallNum|CopyNum|Checkout|");
    while ( rs.next() ){
      System.out.println("|" + rs.getString("uid") + "|" + rs.getString("copynum") + "|" + rs.getString("callnum") + "|" +  rs.getString("checkout")+ "|");
    }
    
    System.out.println("End of Query\n");
    
    } catch ( SQLException e ) {
      System.out.println("[Error] \n");
    }
  }
  
  public static Date dMy_string_to_Utildate(String dMy_string){
    java.util.Date Udate = new java.util.Date();
    
    SimpleDateFormat dMy = new SimpleDateFormat("dd/MM/yy");
       try{
         Udate=dMy.parse(dMy_string)  ;
          
       }catch(java.text.ParseException e)
        {
          e.printStackTrace();
          
        }
        return Udate;
  }


}