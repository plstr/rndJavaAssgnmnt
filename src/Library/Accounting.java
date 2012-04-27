package Library;

public class Accounting {
    // keeps tally of system funds (not user funds)
    private double digitalRentals = 0.0;
    private double digitalPurchases = 0.0;
    private double phyRentals = 0.0;
    private double phyPurchases = 0.0;
    private double feesCollected = 0.0;

    public void addDigitalRentals(double extra){
        this.digitalRentals += extra;
    }

    public double getDigitalRentals(){
        return this.digitalRentals;
    }

    public void addDigitalPurchases(double extra){
        this.digitalPurchases += extra;
    }

    public double getDigitalPurchases(){
        return this.digitalPurchases;
    }

    public void addPhyRentals(double extra){
        this.phyRentals += extra;
    }

    public double getPhyRentals(){
        return this.phyRentals;
    }

    public void addPhyPurchases(double extra){
        this.phyPurchases += extra;
    }

    public double getPhyPurchases(){
        return this.phyPurchases;
    }

    public void addFees(double extra){
        this.feesCollected += extra;
    }

    public double getFeesCollected(){
        return this.feesCollected;
    }

    public double getStoreTotal(){
        double total = 0.0;
        total += this.digitalPurchases;
        total += this.digitalRentals;
        total += this.phyPurchases;
        total += this.phyRentals;
        total += this.feesCollected;
        return total;
    }
}
