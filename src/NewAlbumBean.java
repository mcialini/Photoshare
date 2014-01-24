package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new album data
 *
 * 
 */
public class NewAlbumBean {
  private String album_name = "";
  private int created_on = 0;  

  public String getName() {
    return album_name;
  }

  public int getDate() {
    return created_on;
  }

  public void setName(String name) {
	this.album_name = name;
  }
  
  public void setDate(int date) {
	this.created_on = date;
  }
 
}
  