package Users;
import Exceptions.InsufficientCredit;
import Library.DigitalMovie;
import Users.StoreSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class Member extends User{
    // extra variables
    StoreSettings settings = new StoreSettings();

    protected double credit = 20.0;
    protected double overdueCharged = 0.0;
    private double overdueFee = settings.getLateFee();

    private HashMap<String, String> currentRentals = new HashMap<String, String>();
    private HashMap purchaseHistory = new HashMap();
    private HashMap rentalHistory = new HashMap();

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

    public boolean checkRentalHistory(String title){
        return this.rentalHistory.containsKey(title);
    }

    public void addPurchaseHistory(String title, String type){
        this.purchaseHistory.put(title, type);
    }

    public boolean checkPurchaseHistory(String title){
        return this.purchaseHistory.containsKey(title);
    }

    public HashMap getRentalHistory(){
        return this.rentalHistory;
    }

    public HashMap getPurchaseHistory(){
        return this.purchaseHistory;
    }

    public void chargeOverdueFee(int days){
        double total = days * this.overdueFee;
        this.credit -= total;
        overdueCharged += total;
    }

    public void addCurrentRentals(String title, String type){
        this.currentRentals.put(title, type);
    }

    public void removeCurrentRentals(String title){
        this.currentRentals.remove(title);
    }

    public ArrayList getCurrentDigiRentals(){
        ArrayList<String> result = new ArrayList<String>();
        for(String each : this.currentRentals.keySet()){
            if(this.currentRentals.get(each).equals("digital")){
                result.add(each);
            }
        }
        return result;
    }

    public ArrayList getCurrentDVDRentals(){
        ArrayList<String> result = new ArrayList<String>();
        for(String each : this.currentRentals.keySet()){
            if(this.currentRentals.get(each).equals("DVD")){
                result.add(each);
            }
        }
        return result;
    }
}
