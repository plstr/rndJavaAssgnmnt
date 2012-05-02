package Users;

public class StoreSettings {
    private double purchaseCost = 17.0;
    private double rentalCost = 5.0;
    private double lateFee = 1.0;
    private double singleShippingCost = 5.0;

    public double getPurchaseCost(){
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost){
        this.purchaseCost = purchaseCost;
    }

    public double getRentalCost(){
        return rentalCost;
    }

    public void setRentalCost(double rentalCost){
        this.rentalCost = rentalCost;
    }

    public double getLateFee(){
        return lateFee;
    }

    public void setLateFee(double lateFee){
        this.lateFee = lateFee;
    }

    public double getSingleShippingCost(){
        return singleShippingCost;
    }

    public void setSingleShippingCost(double singleShippingCost){
        this.singleShippingCost = singleShippingCost;
    }

    public String toString(){
        return "Movie purchase cost: " + this.getPurchaseCost() + "\n" +
                "Movie rental cost: " + this.getRentalCost() + "\n" +
                "Rental late fee: " + this.getLateFee() + "\n" +
                "Single package shipping flat-rate: " + this.getSingleShippingCost();
    }
}
