package com.edatwhite.smkd.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
 
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    private String fio;
 
    @NotBlank
    @Size(max = 50)
    private String username;
    
    private Set<String> role;

    private Set<Long> divisions;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public Set<Long> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<Long> divisions) {
        this.divisions = divisions;
    }
}
