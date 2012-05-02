package Users;

public class PremiumCart extends Cart{
    private double purchaseDiscount = 0.05;
    private double shippingDiscount = 0.1;
    private final int allowedOverdues = 10;

    public PremiumCart(Member user, StoreSettings settings){
        super(user, settings);
    }
}
