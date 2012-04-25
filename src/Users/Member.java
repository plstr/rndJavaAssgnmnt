package Users;
import Exceptions.InsufficientCredit;

public class Member extends User{
    // extra variables
    private double credit;

    public Member(String username, String password, String fullName,
                  String email, String address){
        super(username, password, fullName, email, address);
    }

    public double getCredit(){
        return credit;
    }

    public void addCredit(double amount){
        this.credit += amount;
    }

    public void deductCredit(double amount) throws InsufficientCredit{
        if(amount > credit){
            throw new InsufficientCredit();
        } else {
            this.credit -= amount;
        }
    }
}
