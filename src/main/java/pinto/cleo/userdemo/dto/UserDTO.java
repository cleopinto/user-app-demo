package pinto.cleo.userdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by cleo on 5/5/18.
 */
@JsonIgnoreProperties(value={ "id" }, allowGetters=true)
public class UserDTO {

    public UserDTO(){

    }

    public UserDTO(Integer id, @NotEmpty String name, @Email String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
