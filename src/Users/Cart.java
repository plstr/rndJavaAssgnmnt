package Users;
import Library.*;

import java.util.HashMap;

public class Cart {
    private HashMap<DigitalMovie, Integer> digitalItems;
    private HashMap<PhysicalMovie, Integer> physicalItems;
    // cart variables
    private final double purchaseCost = 17.0;
    private final double rentalCost = 5.0;
    private final double lateFee = 1.0;
    private final double singleShippingCost = 5.0;

    private int lateFines = 0;
    private double totalShippingCost;
    private double purchaseDiscount = 0.0;
    private double shippingDiscount = 0.0;

    public HashMap getAllItems(){
        HashMap<Movie, Integer> allItems = new HashMap();
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

    public void addItem(Movie title, int quantity){
        if(title instanceof DigitalMovie){
            digitalItems.put((DigitalMovie)title, quantity);
        }
        if(title instanceof PhysicalMovie){
            physicalItems.put((PhysicalMovie)title, quantity);
        }
    }
}
