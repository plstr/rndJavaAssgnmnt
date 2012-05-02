package Users;

import Exceptions.InvalidLogin;
import java.util.Map;
import java.util.HashMap;

public class Authentication {
    // Authentication functionality. Email == username
    //init the lists.. hardcoded :(
    private Map passwd = new HashMap<String, String>();
    private Map userDB = new HashMap<String, User>();
    private String[] userDBList = new String[]{
        // uid, password, name, email, address, accn_type
            "M000001;1234;John Doe;john.doe@somedomain.com;RMIT University, GPO Box 2476 Melbourne VIC 3001,Australia;standard",
            "M000002;1234;Jane Doe;jane.doe@somedomain.com;RMIT University, GPO Box 2476 Melbourne VIC 3001,Australia;premium",
            "A000001;1234;Daniel Macias;daniel.macias@student.rmit.edu.au;RMIT University, City Campus, Building 14, Level 8, GPO Box 2476, Melbourne VIC 3001, Australia",
            "A000002;1234;John Thangarajah;john.thangarajah@rmit.edu.au;RMIT University, City Campus, Building 14, Level 8, GPO Box 2476, Melbourne VIC 3001, Australia"
    };

    public Authentication(){
        // temp list to check login attempts against.
        for(String each : userDBList){
            String[] user = each.split(";");
            this.passwd.put(user[3], user[1]);
        }
        this.buildUserDB();

    }

    public void buildUserDB(){
        for(String each : userDBList){
            String[] user = each.split(";");
            if(user[0].contains("A")){
                userDB.put(user[3], new Admin(user[0], // userid
                            user[1], // password
                            user[2], // full name
                            user[3], // email
                            user[4]));// address
            } else{
                if(user[5].contains("premium")){
                    userDB.put(user[3], new PremiumMember(user[0], // userid
                                user[1], // password
                                user[2], // full name
                                user[3], // email
                                user[4]));// address
                } else {
                    userDB.put(user[3], new Member(user[0], // userid
                                user[1], // password
                                user[2], // full name
                                user[3], // email
                                user[4]));// address
                    }
                }
        }
    }

    public User checkUser(String username, String password) throws InvalidLogin{
        if(userDB.containsKey(username)){
            User user = (User)userDB.get(username);
            if (user.checkPassword(password)){
                return user;
            } else {
                throw new InvalidLogin();
            }
        } else {
            throw new InvalidLogin();
        }
    }

    public void addUser(String uid, String password, String fullname,
                        String email, String address){
        this.userDB.put(email, new Admin(uid, password, fullname, email, address));
    }

    public void addUser(String uid, String password, String fullname,
                        String email, String address, String type){
        if(type.equals("premium")){
            this.userDB.put(email, new PremiumMember(uid, password, fullname, email, address));
        } else {
            this.userDB.put(email, new Member(uid, password, fullname, email, address));
        }
    }

    public boolean isUser(String email){
        return this.userDB.containsKey(email);
    }

    public User getUser(String email){
        return (User)this.userDB.get(email);
    }

    public boolean deleteUser(String email){
        if (isUser(email)){
            this.userDB.remove(email);
            return true;
        } else {
            return false;
        }
    }
}
