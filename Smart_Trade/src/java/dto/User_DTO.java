package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 *
 * @author prabu
 */
public class User_DTO implements Serializable {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;

    @Expose(deserialize = true, serialize = false)
    private String password;

    public User_DTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
