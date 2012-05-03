package Users;

import Library.DigitalMovie;
import Library.PhysicalMovie;

public class PremiumCart extends Cart{
    private double purchaseDiscount = 0.05;
    private double shippingDiscount = 0.1;
    private final int allowedOverdues = 10;

    public PremiumCart(Member user, StoreSettings settings){
        super(user, settings);
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
        if(packages > 1){
            this.totalShippingCost = this.singleShippingCost * packages *
                    this.shippingDiscount;
        } else {
            this.totalShippingCost = this.singleShippingCost * packages;
        }
    }

    public double getPhyPurchaseCost(){
        double total = 0.0;
        int counter = 0;
        for(PhysicalMovie each : physicalItems.keySet()){
            if(physicalItems.get(each).equals("buy")){
                counter++;
                total += purchaseCost;
            }
        }
        if(counter > 5){
            total = total * this.purchaseDiscount;
        }
        return total;
    }

    public double getDigiPurchaseCost(){
        double total = 0.0;
        int counter = 0;
        for(DigitalMovie each : digitalItems.keySet()){
            if(digitalItems.get(each).equals("buy")){
                counter++;
                total += purchaseCost;
            }
        }
        if(counter > 5){
            total = total * this.purchaseDiscount;
        }
        return total;
    }
}
