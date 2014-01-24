package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\" FROM Pictures WHERE \"picture_id\" = ?";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\") VALUES (?, ?, ?, ?, ?, ?)";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

  private static final String ALBUM_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures WHERE \"album_id\" = ? ORDER BY \"picture_id\" DESC";
  
  private static final String GET_ALBUM = "SELECT \"album_id\" FROM Pictures WHERE \"picture_id\" = ?";
  private static final String GET_TAGS = "SELECT \"tag\" FROM Tags WHERE \"picture_id\" = ?";
  private static final String GET_TAGGED_PICS = "SELECT \"picture_id\" FROM Tags NATURAL JOIN Pictures NATURAL JOIN ALBUMS WHERE \"tag\" = ? AND \"user_id\" = ?";
  private static final String GET_PICS_WITH_TAG = "SELECT \"picture_id\" FROM Tags WHERE \"tag\" = ?";
  private static final String GET_CAPTION = "SELECT \"caption\" FROM Pictures WHERE \"picture_id\" = ?";
  
  private static final String GET_POPULAR_TAGS = "SELECT \"tag\", COUNT(\"tag\") AS count FROM Tags GROUP BY tag ORDER BY count DESC LIMIT 5";
  private static final String GET_LIKES = "SELECT \"user_id\" FROM Likes WHERE \"picture_id\" = ?";
  private static final String GET_COMMENTS = "SELECT \"comment_id\" FROM Comments WHERE \"picture_id\" = ?";
  private static final String SET_TAGS = "INSERT INTO " + 
	"Tags (\"tag\",\"picture_id\") VALUES (?, ?)";
  private static final String DELETE_PICTURE = "DELETE FROM Pictures WHERE \"picture_id\" = ?";
  
  
  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
		picture.setAlbumID(rs.getInt(6));
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

		return picture;
  }

	public void save(Picture picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setString(1, picture.getCaption());
			stmt.setBytes(2, picture.getData());
			stmt.setBytes(3, picture.getThumbdata());
			stmt.setLong(4, picture.getSize());
			stmt.setString(5, picture.getContentType());
			stmt.setInt(6, picture.getAlbumID());
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
	
	public void addTags(String[] tags, int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();			
			for (int i = 0; i < tags.length; i++) {
				stmt = conn.prepareStatement(SET_TAGS);
				stmt.setString(1, tags[i]);
				stmt.setInt(2, id);
				stmt.executeUpdate();
			}			
			
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
	

	public List<Integer> allPicturesIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
	}
	
	public List<Integer> albumPictureIds(int albumID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALBUM_PICTURE_IDS_STMT);
			stmt.setInt(1, albumID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
	}
	
	public List<Integer> tagSearch(String[] tags) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> picturesIds = new ArrayList<Integer>();
		
		try {
			conn = DbConnection.getConnection();
			String TAG_SEARCH = "";
			
			
			for (int i = 0; i < tags.length-1; i++) {
				TAG_SEARCH += "SELECT \"picture_id\" FROM Tags WHERE \"tag\" = '" + tags[i];
				TAG_SEARCH += "' INTERSECT ";
			}
			TAG_SEARCH += "SELECT \"picture_id\" FROM Tags WHERE \"tag\" = '" + tags[tags.length-1] + "'";
			
			stmt = conn.prepareStatement(TAG_SEARCH);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
	}
	
	public List<String> getSuggested(String[] tags) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> suggestions = new ArrayList<String>();
		
		try {
			conn = DbConnection.getConnection();
			String TAG_SEARCH = "SELECT \"tag\", COUNT(*) as \"count\" FROM Tags WHERE \"picture_id\" IN (";

			for (int i = 0; i < tags.length-1; i++) {
				TAG_SEARCH += "SELECT \"picture_id\" FROM Tags WHERE \"tag\" = '" + tags[i];
				TAG_SEARCH += "' INTERSECT ";
			}
			TAG_SEARCH += "SELECT \"picture_id\" FROM Tags WHERE \"tag\" = '" + tags[tags.length-1] + "'";
			TAG_SEARCH += " ) GROUP BY \"tag\" ORDER BY \"count\" DESC LIMIT 5";
			stmt = conn.prepareStatement(TAG_SEARCH);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				suggestions.add(rs.getString(1));
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

		return suggestions;
	}
	
	public List<Integer> userTagSearch(String[] tags, int userID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> picturesIds = new ArrayList<Integer>();
		
		try {
			conn = DbConnection.getConnection();
			String TAG_SEARCH = "";
			
			
			for (int i = 0; i < tags.length-1; i++) {
				TAG_SEARCH += "SELECT \"picture_id\" FROM Tags NATURAL JOIN Pictures NATURAL JOIN ALBUMS WHERE \"tag\" = '" + tags[i] + "' AND \"user_id\" = " + userID;
				TAG_SEARCH += " INTERSECT ";
			}
			TAG_SEARCH += "SELECT \"picture_id\" FROM Tags NATURAL JOIN Pictures NATURAL JOIN ALBUMS WHERE \"tag\" = '" + tags[tags.length-1] + "' AND \"user_id\" = " + userID;
			
			stmt = conn.prepareStatement(TAG_SEARCH);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
	}
	
	public int getAlbum(int pictureID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int album = 0;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_ALBUM);
			stmt.setInt(1, pictureID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				album = rs.getInt(1);
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

		return album;
	}
	
	public List<String> getPopularTags() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> popular = new ArrayList<String>();
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_POPULAR_TAGS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				popular.add(rs.getString(1));
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

		return popular;
	}
	
	public List<String> getTags(int pictureID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> tags = new ArrayList<String>();
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TAGS);
			stmt.setInt(1, pictureID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tags.add(rs.getString(1));
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

		return tags;
	}
	
	public List<Integer> getLikes(int pictureID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> likes = new ArrayList<Integer>();
	
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_LIKES);
			stmt.setInt(1, pictureID);
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
	
	public void remove(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();			
			stmt = conn.prepareStatement(DELETE_PICTURE);
			stmt.setInt(1, id);
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
	
	
}
