package Users;
import Exceptions.InsufficientCredit;

public class PremiumMember extends Member{
    private double credit;
    private final double OVERDRAFT = 20.0;

    public PremiumMember(String username, String password, String fullName,
                  String email, String address){
        super(username, password, fullName, email, address);
    }

    public void deductCredit(double amount) throws InsufficientCredit{
        if(amount > (credit + OVERDRAFT)) {
            throw new InsufficientCredit();
        } else {
            this.credit -= amount;
        }
    }
}
