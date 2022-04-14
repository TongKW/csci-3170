package menu;

import java.sql.*;
import java.util.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
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
    + "occupation VARCHAR(20) NOT NULL,"
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
    + "checkout DATE NOT NULL,"
    + "return_date DATE,"
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
    } catch (SQLException e) {
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return;
  }

  public static void load_datasets() {
    System.out.print("Type in the Source Data Folder Path:");
    Scanner srcDataPathScanner = new Scanner(System.in);
    String Dir = srcDataPathScanner.nextLine();

    //for debug
    //System.out.println("your input data source is " + srcDataPath + " \n and this folder contains \n");
    //File folder = new File(rootDir + srcDataPath);
    //for (File f : folder.listFiles()){System.out.println(f.getName());}

    String carFilePath = Dir + "/car.txt";
    String carCatorgoryFilePath = Dir + "/car_category.txt";
    String userFilePath = Dir + "/user.txt";
    String userCategoryFilePath = Dir + "/user_category.txt";
    String rentFilePath = Dir + "/rent.txt";

    List carList = readUsingFileReader(carFilePath);
    List carCatList = readUsingFileReader(carCatorgoryFilePath);
    List userList = readUsingFileReader(userFilePath);
    List userCatList = readUsingFileReader(userCategoryFilePath);
    List rentList = readUsingFileReader(rentFilePath);

    //for debug
    //System.out.println(carList.size());
    //System.out.println(carCatList.get(1));
    //System.out.println(rentList.get(1));

    generateStmt(carCatList,2);
    generateStmt(userCatList,4);
    generateStmt(carList,1);
    generateStmt(userList,3);
    generateStmt(rentList,5);
    generateStmt(carList,6);
    generateStmt(carList,7);
    return;
  }

  private static List readUsingFileReader(String fileName) {
    try {
      File file = new File(fileName);
      List<String> lines = new ArrayList<String>();
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while((line = br.readLine()) != null){
        lines.add(line);
      }
      br.close();
      fr.close();
      return lines;
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static void generateStmt(List list,int listType) {
    try {
      Statement stmt = conn.createStatement();
      for (int i = 0; i < list.size(); i++) {
        String tmpString = String.valueOf(list.get(i));
        String[] values = tmpString.split("\t");
	switch(listType) {
          case 1: {
	    String query = "INSERT INTO car(callnum, name, manufacture, time_rent, ccid) VALUES (?,?,?,?,?);";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setString(1, values[0]); else pstmt.setNull(1, Types.CHAR);
	    if (!values[2].equals("NULL")) pstmt.setString(2, values[2]); else pstmt.setNull(2, Types.VARCHAR);
	    if (!values[4].equals("NULL")) pstmt.setString(3, values[4]); else pstmt.setNull(3, Types.CHAR);
	    if (!values[5].equals("NULL")) pstmt.setInt(4, Integer.parseInt(values[5])); else pstmt.setNull(4, Types.TINYINT);
	    if (!values[6].equals("NULL")) pstmt.setInt(5, Integer.parseInt(values[6])); else pstmt.setNull(5, Types.TINYINT);
            pstmt.executeUpdate();
	    break;
	  } case 2: {
	    String query = "INSERT INTO car_category(ccid,ccname) VALUES (?,?);";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setInt(1, Integer.parseInt(values[0])); else pstmt.setNull(1, Types.TINYINT);
	    if (!values[1].equals("NULL")) pstmt.setString(2, values[1]); else pstmt.setNull(2, Types.VARCHAR);
            pstmt.executeUpdate();
	    break;
	  } case 3: {
	    String query = "INSERT INTO user(uid, ucid, uname, age, occupation) VALUES (?,?,?,?,?);";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setString(1, values[0]); else pstmt.setNull(1, Types.CHAR);
	    if (!values[4].equals("NULL")) pstmt.setInt(2, Integer.parseInt(values[4])); else pstmt.setNull(2, Types.TINYINT);
	    if (!values[1].equals("NULL")) pstmt.setString(3, values[1]); else pstmt.setNull(3, Types.VARCHAR);
	    if (!values[2].equals("NULL")) pstmt.setInt(4, Integer.parseInt(values[2])); else pstmt.setNull(4, Types.TINYINT);
	    if (!values[3].equals("NULL")) pstmt.setString(5, values[3]); else pstmt.setNull(5, Types.VARCHAR);
            pstmt.executeUpdate();
	    break;
	  } case 4: {
	    String query = "INSERT INTO user_category(ucid, max, period) VALUES (?,?,?)";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setInt(1, Integer.parseInt(values[0])); else pstmt.setNull(1, Types.TINYINT);
	    if (!values[1].equals("NULL")) pstmt.setInt(2, Integer.parseInt(values[1])); else pstmt.setNull(2, Types.TINYINT);
	    if (!values[2].equals("NULL")) pstmt.setInt(3, Integer.parseInt(values[2])); else pstmt.setNull(3, Types.TINYINT);
            pstmt.executeUpdate();
	    break;
	  } case 5: {
	    String query = "INSERT INTO rent(callnum, copynum, uid, checkout, return_date) VALUES (?,?,?,?,?);";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setString(1, values[0]); else pstmt.setNull(1, Types.CHAR);
	    if (!values[1].equals("NULL")) pstmt.setInt(2, Integer.parseInt(values[1])); else pstmt.setNull(2, Types.TINYINT);
            if (!values[2].equals("NULL")) pstmt.setString(3, values[2]); else pstmt.setNull(3, Types.CHAR); 
	    if (!values[3].equals("NULL")) pstmt.setDate(4, java.sql.Date.valueOf(values[3])); else pstmt.setNull(4, Types.DATE);
	    if (!values[4].equals("NULL")) pstmt.setDate(5, java.sql.Date.valueOf(values[4])); else pstmt.setNull(5, Types.DATE);
            pstmt.executeUpdate();
	    break;
	  } case 6: {
	    String query = "INSERT INTO copy(callnum, copynum) VALUES (?,?);";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    if (!values[0].equals("NULL")) pstmt.setString(1, values[0]); else pstmt.setNull(1, Types.CHAR);
	    if (!values[1].equals("NULL")) pstmt.setInt(2, Integer.parseInt(values[1])); else pstmt.setNull(2, Types.TINYINT);
            pstmt.executeUpdate();
            break;
	  } case 7: {
            String query = "INSERT INTO produce(callnum, cname) VALUES (?,?);";
            PreparedStatement pstmt = conn.prepareStatement(query);
            if (!values[0].equals("NULL")) pstmt.setString(1, values[0]); else pstmt.setNull(1, Types.CHAR);
            if (!values[3].equals("NULL")) pstmt.setString(2, values[3]); else pstmt.setNull(2, Types.VARCHAR);
	    pstmt.executeUpdate();
            break;
          }
	}
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return;
  }

  public static void show_all() {
    show_one("car");
    show_one("car_category");
    show_one("user");
    show_one("user_category");
    show_one("rent");
    show_one("copy");
    show_one("produce");
  }

  private static void show_one(String table_name) {
    try {
      System.out.print(table_name + ": ");
      { 
	Statement stmt = conn.createStatement();
        ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM " + table_name + ";");
        r.next();
        System.out.println(r.getInt(1));
        r.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
