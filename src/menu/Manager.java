package menu;

import java.sql.*;
import jdbc.Util;

public class Manager {
  private static Connection conn;
  public Manager() {
    Util jdbcUtil = new Util();
    conn = Util.get_conn();
  }

}
