package Users;

import Exceptions.InvalidLogin;
import java.util.Map;
import java.util.HashMap;

public class Authentication {
    // Authentication functionality. Email == username
    //init the lists.. hardcoded :(
    private Map passwd = new HashMap<String, String>();
    private String[] userDB = new String[]{
        // uid, password, name, email, address, accn_type
            "M000001;1234;John Doe;john.doe@somedomain.com;RMIT University, GPO Box 2476 Melbourne VIC 3001,Australia;standard",
            "M000002;1234;Jane Doe;jane.doe@somedomain.com;RMIT University, GPO Box 2476 Melbourne VIC 3001,Australia;premium",
            "A000001;1234;Daniel Macias;daniel.macias@student.rmit.edu.au;RMIT University, City Campus, Building 14, Level 8, GPO Box 2476, Melbourne VIC 3001, Australia",
            "A000002;1234;John Thangarajah;john.thangarajah@rmit.edu.au;RMIT University, City Campus, Building 14, Level 8, GPO Box 2476, Melbourne VIC 3001, Australia"
    };

    public Authentication(){
        // temp list to check login attempts against.
        for(String each : userDB){
            String[] user = each.split(";");
            this.passwd.put(user[3], user[1]);
        }
    }

    public User instantiateUser(String username) throws InvalidLogin{
        for(String each : userDB){
            String[] user = each.split(";");
            if(user[3].contentEquals(username)){
                if(user[0].contains("A")){
                    return new Admin(user[0], // userid
                            user[1], // password
                            user[2], // full name
                            user[3], // email
                            user[4]);// address
                } else{
                    if(user[5].contains("premium")){
                        return new PremiumMember(user[0], // userid
                                user[1], // password
                                user[2], // full name
                                user[3], // email
                                user[4]);// address
                    } else {
                        return new Member(user[0], // userid
                                user[1], // password
                                user[2], // full name
                                user[3], // email
                                user[4]);// address
                    }
                }
            }
        }
        // if things got this far... should've have been called..
        throw new InvalidLogin();
    }

    public User checkUser(String username, String password) throws InvalidLogin{
        if(passwd.containsKey(username)){
            if (passwd.get(username).equals(password)){
                return instantiateUser(username);
            } else {
                throw new InvalidLogin();
            }
        } else {
            throw new InvalidLogin();
        }
    }


}
