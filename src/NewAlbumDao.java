package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
/**
 * A data access object (DAO) to handle the Albums table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewAlbumDao {
  private static final String NEW_ALBUM_STMT = "INSERT INTO " +
      "Albums (album_name, created_on, user_id) VALUES (?, ?, ?)";

  public void create(String album_name, int user_id) {
    PreparedStatement stmt = null;
	PreparedStatement getid = null;
    Connection conn = null;
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(NEW_ALBUM_STMT);
      stmt.setString(1, album_name);
	  Date now = new Date();
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = format.format(now);
      stmt.setString(2, dateString);
	  stmt.setInt(3, user_id);	  
	  stmt.executeUpdate();
	  
	  stmt.close();
	  stmt = null;
	  
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
		if (stmt != null) {
			try { stmt.close(); }
			catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); }
			catch (SQLException e) { ; }
			conn = null;
		}
    }
  }
}
