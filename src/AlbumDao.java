
package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*  A data access object (DAO) to handle the Albums table
 * 
 *  @author G. Zervas <cs460tf@bu.edu>
 *     */
public class AlbumDao {

  private static final String GET_ALBUM_NAME = "SELECT \"album_name\" FROM Albums WHERE \"album_id\" = ?";
  
  private static final String GET_USER_ID = "SELECT \"user_id\" FROM Albums WHERE \"album_id\" = ?";
  
  private static final String ALBUM_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures WHERE \"album_id\" = ? ORDER BY \"picture_id\" DESC";
	private static final String DELETE_ALBUM = "DELETE FROM Albums WHERE \"album_id\" = ?";
  

  public String getAlbumName(int albumID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
		String name = "";
		
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALBUM_NAME);
            stmt.setInt(1, albumID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
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

	public void remove(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();			
			stmt = conn.prepareStatement(DELETE_ALBUM);
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

	public int getUserID(int album_id) {
        PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
        int id = 0;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_USER_ID);
			stmt.setInt(1, album_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
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
 
	
}
