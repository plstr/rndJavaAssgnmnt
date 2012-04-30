package Users;
import Exceptions.InsufficientCredit;

import java.util.HashMap;

public class Member extends User{
    // extra variables
    private double credit;
    private HashMap purchaseHistory;
    private HashMap rentalHistory;

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

    public void addRentalHistory(String title, String type){
        this.rentalHistory.put(title, type);
    }

    public void addPurchaseHistory(String title, String type){
        this.purchaseHistory.put(title, type);
    }

    public HashMap getRentalHistory(){
        return this.rentalHistory;
    }

    public HashMap getPurchaseHistory(){
        return this.purchaseHistory;
    }
}
