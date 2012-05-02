package Users;

import java.util.HashMap;
import java.util.Map;
import Exceptions.NonUniqueInput;

public abstract class User {
    // shared variables
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String address;

    public User(String username, String password, String fullName,
                String email, String address){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public void setFullName(String fullname){
        this.fullName = fullname;
    }

    public Map<String, String> getUserInfo(){
        HashMap<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("username", this.username);
        userInfo.put("fullname", this.fullName);
        userInfo.put("email", this.email);
        userInfo.put("address", this.address);
        return userInfo;
    }

    public String toString(){
        return "Username: " + this.username +
                "\nFull Name: " + this.fullName +
                "\nE-mail address: " + this.email +
                "\nPostal address: " + this.address;
    }

    public boolean checkPassword(String pass){
        return this.password.equals(pass);
    }

}
