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

  public static void load_datasets() {
    System.out.print("Type in the Source Data Folder Path:");
    Scanner srcDataPathScanner = new Scanner(System.in);
    String srcDataPath = srcDataPathScanner.nextLine();
    String rootDir = "/uac/cprj/db030/bin/java_test/";
    //this rootDir is specific to db030, need to be changed
    String Dir = rootDir + srcDataPath;

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

    System.out.println(generateStmt(carList,1));
    System.out.println(generateStmt(carCatList,2));
    System.out.println(generateStmt(userList,3));
    System.out.println(generateStmt(userCatList,4));
    System.out.println(generateStmt(rentList,5));
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
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static String generateStmt(List list,int listType){
    String Stmt = "\nINSERT INTO ";
    switch(listType){
        case 1:
            Stmt += "\n     car(callum, name, manufacture, time_rent, ccid) \nVALUES";
            break;
        case 2:
            Stmt += "\n     car_category(ccid,ccname) \nVALUES";
            break;
        case 3:
            Stmt += "\n     user(uid, name, age, occupation, ucid) \nVALUES";
            break;
        case 4:
            Stmt += "\n     user_category(ucid, max, ucid) \nVALUES";
            break;
        case 5:
            Stmt += "\n     rent(callnum, copynum, uid, checkout, return_data) \nVALUES";
            break;
    }
    for (int i = 0; i < list.size(); i++){
        String tmpString = String.valueOf(list.get(i));
        tmpString = tmpString.replaceAll("\\s+","','");
        tmpString = "\n     ('" + tmpString;
        if(i == list.size() - 1){
            tmpString = tmpString + "');";
        }else{
            tmpString = tmpString + "'),";
        }
        Stmt = Stmt + tmpString;
    }
    return Stmt;
  }
}
