package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserBean {
  private String email = "";
  private String password1 = "";
  private String password2 = "";
  private String first_name = "";
  private String last_name = "";
  private int birthdate = 0;
  private char gender = 'x';
  private String home_city = "";
  private String home_state = "";
  private String home_country = "";
  private String cur_city = "";
  private String cur_state = "";
  private String cur_country = "";
  private String university = "";

  public String saySomething() {
    System.out.println("Hello!");
    return "Test";
  }
  
  public String getEmail() {
    return email;
  }

  public String getPassword1() {
    return password1;
  }

  public String getPassword2() {
    return password2;
  }
  
  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }
  
  public int getBirthdate() {
    return birthdate;
  }

  public char getGender() {
    return gender;
  }
  
  public String getHomeCity() {
    return home_city;
  }

  public String getHomeState() {
    return home_state;
  }

  public String getHomeCountry() {
    return home_country;
  }
  
  public String getCurCity() {
    return cur_city;
  }
  
  public String getCurState() {
    return cur_state;
  }
  
  public String getCurCountry() {
    return cur_country;
  }
  
  public String getUniversity() {
    return university;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public void setFirstName(String first_name) {
    this.first_name = first_name;
  }

  public void setLastName(String last_name) {
    this.last_name = last_name;
  }
  
  public void setBirthdate(int birthdate) {
    this.birthdate = birthdate;
  }
  
  public void setGender(char gender) {
    this.gender = gender;
  }

  public void setHomeCity() {
    this.home_city = home_city;
  }

  public void setHomeState() {
    this.home_state = home_state;
  }

  public void setHomeCountry() {
    this.home_country = home_country;
  }
  
  public void setCurCity() {
    this.cur_city = cur_city;
  }
  
  public void setCurState() {
    this.cur_state = cur_state;
  }
  
  public void setCurCountry() {
    this.cur_country = cur_country;
  }
  
  public void setUniversity() {
    this.university = university;
  }
}
