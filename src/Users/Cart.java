package Users;

import Exceptions.InsufficientCredit;
import Exceptions.InvalidMenuSelection;
import Library.*;
import java.util.HashMap;

public class Cart {
    private HashMap<DigitalMovie, String> digitalItems;
    private HashMap<PhysicalMovie, String> physicalItems;
    // cart variables
    private final double purchaseCost = 17.0;
    private final double rentalCost = 5.0;
    private final double lateFee = 1.0;
    private final double singleShippingCost = 5.0;

    private int lateFines = 0;
    private double totalShippingCost;
    private double purchaseDiscount = 0.0;
    private double shippingDiscount = 0.0;
    private double cartTotal = 0.0;

    public HashMap getAllItems(){
        HashMap<Movie, String> allItems = new HashMap();
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

    public void addItem(Movie title, String type){
        if(title instanceof DigitalMovie){
            digitalItems.put((DigitalMovie)title, type);
        }
        if(title instanceof PhysicalMovie){
            physicalItems.put((PhysicalMovie)title, type);
        }
    }

    private void calculateShipping(){
        int items = physicalItems.size();
        int packages;
        if(items > 3){
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
        this.calculateShipping();
        return this.totalShippingCost;
    }

    public double getRentalCost(){
        double total = 0.0;
        for(PhysicalMovie each : physicalItems.keySet()){
            if(physicalItems.get(each).equals("rent")){
                total += rentalCost;
            }
        }
        return total;
    }

    public double getPurchaseCost(){
        double total = 0.0;
        for(PhysicalMovie each : physicalItems.keySet()){
            if(physicalItems.get(each).equals("buy")){
                total += purchaseCost;
            }
        }
        return total;
    }

    public double getTotalCost(){
        double total = 0.0;
        total += getPurchaseCost();
        total += getRentalCost();
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

    public void checkout() throws InsufficientCredit{
        if(this.cartTotal > user.getAvailableFunds()){
            throw new InsufficientCredit();
        } else {
            user.deductCredit(this.cartTotal);
        }
    }
}
