package Users;

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

    public Map<String, String> getUserInfo(){
        Map<String, String> userInfo = null;
        userInfo.put("username", this.username);
        userInfo.put("fullname", this.fullName);
        userInfo.put("email", this.email);
        userInfo.put("address", this.address);
        return userInfo;
    }
}
