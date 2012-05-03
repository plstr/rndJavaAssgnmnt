package Users;

import Exceptions.InsufficientCredit;
import Exceptions.InvalidMenuSelection;
import Library.*;
import java.util.HashMap;

public class Cart {
    HashMap<DigitalMovie, String> digitalItems = new HashMap<DigitalMovie, String>();
    HashMap<PhysicalMovie, String> physicalItems = new HashMap<PhysicalMovie, String>();
    protected Member currentUser;

    // default cart variables
    double purchaseCost = 17.0;
    private double rentalCost = 5.0;
    private double lateFee = 1.0;
    protected double singleShippingCost = 5.0;

    private int lateFines = 0;
    protected double totalShippingCost;
    private double purchaseDiscount = 0.0;
    private double shippingDiscount = 0.0;
    private double cartTotal = 0.0;

    public Cart(Member user, StoreSettings settings){
        this.currentUser = user;
    }

    public void clear(){
        digitalItems.clear();
        physicalItems.clear();
    }

    public double constRentalCost(){
        return this.rentalCost;
    }

    public double constPurchaseCost(){
        return this.purchaseCost;
    }

    public HashMap getAllItems(){
        HashMap<Movie, String> allItems = new HashMap<Movie, String>();
        allItems.putAll(digitalItems);
        allItems.putAll(physicalItems);
        return allItems;
    }

    public HashMap getDigitalItems(){
        return digitalItems;
    }

    public HashMap getPhysicalItems(){
        return physicalItems;
    }

    public void addDigitalItem(DigitalMovie title, String type){
        this.digitalItems.put(title, type);
    }
    public void addItem(Movie title, String type){

        if(title instanceof DigitalMovie){
            digitalItems.put((DigitalMovie)title, type);
        }
        if(title instanceof PhysicalMovie){
            physicalItems.put((PhysicalMovie)title, type);
        }
    }

    public void calculateShipping(){
        int items = physicalItems.size();
        int packages;
        if(items == 0){
            packages = 0;
        } else if(items > 3){
            packages = items / 3;
            if(items % 3 != 0){
                packages += 1;
            }
            } else {
            packages = 1;
        }
        this.totalShippingCost = this.singleShippingCost * packages;
    }

    public double getTotalShippingCost(){
        calculateShipping();
        return this.totalShippingCost;
    }

    public double getPhyRentalCost(){
        double total = 0.0;
        for(PhysicalMovie each : physicalItems.keySet()){
            if(physicalItems.get(each).equals("rent")){
                total += rentalCost;
            }
        }
        return total;
    }

    public double getPhyPurchaseCost(){
        double total = 0.0;
        for(PhysicalMovie each : physicalItems.keySet()){
            if(physicalItems.get(each).equals("buy")){
                total += purchaseCost;
            }
        }
        return total;
    }

    public double getDigiRentalCost(){
        double total = 0.0;
        for(DigitalMovie each : digitalItems.keySet()){
            if(digitalItems.get(each).equals("rent")){
                total += rentalCost;
            }
        }
        return total;
    }

    public double getDigiPurchaseCost(){
        double total = 0.0;
        for(DigitalMovie each : digitalItems.keySet()){
            if(digitalItems.get(each).equals("buy")){
                total += purchaseCost;
            }
        }
        return total;
    }

    public double getTotalCost(){
        double total = 0.0;
        total += getPhyPurchaseCost();
        total += getPhyRentalCost();
        total += getDigiPurchaseCost();
        total += getDigiRentalCost();
        total += getTotalShippingCost();
        this.cartTotal = total;

        return total;
    }

    public void removeItem(String title) throws InvalidMenuSelection{
        if(this.digitalItems.containsKey(title)){
            this.digitalItems.remove(title);
        } else if(this.physicalItems.containsKey(title)){
            this.physicalItems.remove(title);
        } else {
            throw new InvalidMenuSelection();
        }
    }

    public void removeAllItems(){
        this.digitalItems.clear();
        this.physicalItems.clear();
    }

    public void checkout() throws InsufficientCredit{
        if(this.cartTotal > currentUser.getCredit()){
            throw new InsufficientCredit();
        } else {
            // nothing
        }
    }

    public HashMap getAllSettings(){
        HashMap settings = new HashMap();
        settings.put("Movie purchase cost", purchaseCost);
        settings.put("Movie rental cost", rentalCost);
        settings.put("Shipping flat fee", singleShippingCost);

        return settings;
    }

    public void setPurchaseCost(double newCost){
        this.purchaseCost = newCost;
    }

    public void setRentalCost(double newCost){
        this.rentalCost = newCost;
    }

    public void setSingleShippingCost(double newCost){
        this.singleShippingCost = newCost;
    }
}
