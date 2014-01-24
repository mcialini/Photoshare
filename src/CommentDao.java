
package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*  
 *     */
public class CommentDao {

	private static final String GET_COMMENTS = "SELECT \"comment_id\" FROM Comments WHERE \"picture_id\" = ? ORDER BY \"comment_id\" DESC";
  
	private static final String GET_USER_ID = "SELECT \"user_id\" FROM Comments WHERE \"comment_id\" = ?";

	private static final String GET_TEXT = "SELECT \"text\" FROM Comments WHERE \"comment_id\" = ?";

	public int getUserID(int comment_id) {
        PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
        int id = 0;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_USER_ID);
			stmt.setInt(1, comment_id);
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
	
	public List<Integer> getComments(int pictureID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> comments = new ArrayList<Integer>();
	
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_COMMENTS);
			stmt.setInt(1, pictureID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				comments.add(rs.getInt(1));
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
      

		return comments;
	}
	
	public String getText(int comment_id) {
        PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
        String text = "";

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TEXT);
			stmt.setInt(1, comment_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				text = rs.getString(1);
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

		return text;
	}
	
}
