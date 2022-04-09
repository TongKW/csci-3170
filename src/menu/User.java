package menu;

import java.sql.*;
import jdbc.Util;

public class User {
  private static Connection conn;
  public User() {
    Util jdbcUtil = new Util();
    conn = Util.get_conn();
  }
  
} 
