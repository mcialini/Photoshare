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
 * A data access object (DAO) to handle the Users table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class UserDao {

  private static final String GET_USER_ID = "SELECT \"user_id\" FROM Users WHERE \"email\" = ?";
  private static final String GET_NAME = "SELECT \"first_name\", \"last_name\" FROM Users WHERE \"user_id\" = ?";
  private static final String GET_EMAIL = "SELECT \"email\" FROM Users WHERE \"user_id\" = ?";
  private static final String GET_ALBUMS = "SELECT \"album_id\" FROM Albums WHERE \"user_id\" = ?";
  private static final String GET_ALBUM_NAMES = "SELECT \"album_name\" FROM Albums WHERE \"user_id\" = ?";
  private static final String GET_LIKES = "SELECT \"picture_id\" FROM Likes WHERE \"user_id\" = ?";
  private static final String GET_PICS = "SELECT \"picture_id\" FROM Albums NATURAL JOIN Pictures WHERE \"user_id\" = ?";
  private static final String GET_FRIENDS = "SELECT \"user2\" FROM Friends WHERE \"user1\" = ?";
  private static final String ADD_FRIEND = "INSERT INTO Friends (\"user1\", \"user2\") VALUES (?, ?), (?, ?)";
  private static final String REMOVE_FRIEND = "DELETE FROM Friends WHERE \"user1\" = ? AND \"user2\" = ?";
  private static final String CHECK_IF_FRIENDS = "SELECT " +
      "COUNT(*) FROM Friends WHERE \"user1\" = ? AND \"user2\" = ?";
  private static final String CHECK_IF_LIKED = "SELECT " +
      "COUNT(*) FROM Likes WHERE \"user_id\" = ? AND \"picture_id\" = ?";
  private static final String ADD_LIKE = "INSERT INTO Likes (\"user_id\", \"picture_id\") VALUES (?, ?)";
  private static final String ADD_COMMENT = "INSERT INTO Comments (\"user_id\", \"picture_id\", \"text\", \"posted_on\") VALUES (?, ?, ?, ?)";
  private static final String GET_USERS = "SELECT \"user_id\" FROM Users";
  private static final String GET_TOP_USERS = "SELECT \"user_id\", SUM(\"total\") as \"sum\" FROM (SELECT \"user_id\", COUNT(*) as \"total\" FROM Comments GROUP BY \"user_id\" UNION SELECT \"user_id\", COUNT(*) as \"total\" FROM Pictures NATURAL JOIN Albums GROUP BY \"user_id\") as \"foo\" GROUP BY \"user_id\" ORDER BY \"sum\" DESC LIMIT 10";
  private static final String GET_MOST_COMMON_TAGS = "SELECT \"tag\", COUNT(*) as \"c\" FROM Tags NATURAL JOIN Pictures NATURAL JOIN Albums WHERE \"user_id\" = ? ORDER BY \"c\" DESC LIMIT 5";


  
  public int getUserID(String email) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	int id = 0;
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_USER_ID);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (rs.next()) {
        id = rs.getInt(1);
      } else {
		id = 0;
	  }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
      

	  return id;
    }
	
  public String getEmail(int id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	String email = "";
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_EMAIL);
      stmt.setInt(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        email = rs.getString(1);
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
      

	  return email;
    }
	
  public String getFullName(int id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	String name = "";
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_NAME);
      stmt.setInt(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
		name = rs.getString(1) + " " + rs.getString(2);
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return name;
  }
  
  public List<Integer> getLikes(int user_id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	List<Integer> likes = new ArrayList<Integer>();
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_LIKES);
      stmt.setInt(1, user_id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        likes.add(rs.getInt(1));
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return likes;
  }
  
  public List<Integer> getAlbums(int user_id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	List<Integer> albums = new ArrayList<Integer>();
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_ALBUMS);
      stmt.setInt(1, user_id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        albums.add(rs.getInt(1));
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return albums;
  }
  
  public List<Integer> getPics(int user_id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	List<Integer> pics = new ArrayList<Integer>();
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_PICS);
      stmt.setInt(1, user_id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        pics.add(rs.getInt(1));
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return pics;
  }
  
  public List<Integer> getFriends(int user_id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	List<Integer> friends = new ArrayList<Integer>();
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_FRIENDS);
      stmt.setInt(1, user_id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        friends.add(rs.getInt(1));
      }
      
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return friends;
  }
  
  public boolean addFriendship(int user1, int user2) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_IF_FRIENDS);
			stmt.setInt(1, user1);
			stmt.setInt(2, user2);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
			// Theoretically this can't happen, but just in case...
				return false;
			}
			int result = rs.getInt(1);
			if (result > 0) {
			// Users are already friends
				return false; 
			}
      
			try { stmt.close(); }
			catch (Exception e) { }
			
			stmt = conn.prepareStatement(ADD_FRIEND);
			stmt.setInt(1, user1);
			stmt.setInt(2, user2);
			stmt.setInt(3, user2);
			stmt.setInt(4, user1);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public void removeFriendship(int user1, int user2) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(REMOVE_FRIEND);
			stmt.setInt(1, user1);
			stmt.setInt(2, user2);
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement(REMOVE_FRIEND);
			stmt.setInt(1, user2);
			stmt.setInt(2, user1);
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
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public boolean alreadyFriends(int user1, int user2) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_IF_FRIENDS);
			stmt.setInt(1, user1);
			stmt.setInt(2, user2);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
			// Theoretically this can't happen, but just in case...
				return false;
			}
			int result = rs.getInt(1);
			if (result > 0) {
			// Already friends
				return true; 
			}
      
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public boolean alreadyLikes(int user, int picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_IF_LIKED);
			stmt.setInt(1, user);
			stmt.setInt(2, picture);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
			// Theoretically this can't happen, but just in case...
				return false;
			}
			int result = rs.getInt(1);
			if (result > 0) {
			// User already likes picture
				return true; 
			}
      
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public void addLike(int user, int picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_LIKE);
			stmt.setInt(1, user);
			stmt.setInt(2, picture);
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
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public void addComment(int user, int picture, String text) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(now);
		
	
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_COMMENT);
			stmt.setInt(1, user);
			stmt.setInt(2, picture);
			stmt.setString(3, text);
			stmt.setString(4, dateString);
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
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public List<Integer> allUserIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_USERS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return userIds;
	}
	
	public List<Integer> getTopUsers() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TOP_USERS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userIds.add(rs.getInt(1));
			}
	
			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return userIds;
	}
	
	public List<Integer> getRecommendedPictures(int user_id) {
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
	List<Integer> pics = new ArrayList<Integer>();
	List<String> tags = new ArrayList<String>();
	
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_MOST_COMMON_TAGS);
      stmt.setInt(1, user_id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        tags.add(rs.getString(1));
      }
      
	  /*
		Conjuctive Search part
	  */
	
	  
	  rs.close();
	  rs = null;
		
	  stmt.close();
	  stmt = null;
			
	  conn.close();
	  conn = null;
	  
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
	} finally {
		if (rs != null) {
			try { rs.close(); } catch (SQLException e) { ; }
			rs = null;
		}
		if (stmt != null) {
			try { stmt.close(); } catch (SQLException e) { ; }
			stmt = null;
		}
		if (conn != null) {
			try { conn.close(); } catch (SQLException e) { ; }
			conn = null;
		}
	}
      

    return pics;
  }
	
}
  

