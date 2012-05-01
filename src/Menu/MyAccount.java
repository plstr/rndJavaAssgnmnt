package Menu;
import IO.*;
public class MyAccount extends Menu{
    public MyAccount(){
        this.myAccount();
    }

    public void myAccount(){
        Integer selection = 9;
        this.menuTitle("My account | VixFix Store");
        output.out("[Account info]");
        output.out("Full name: " + member.getUserInfo().get("fullname"));
        output.out("Account ID: " + member.getUserInfo().get("username"));
        output.out("E-mail: " + member.getUserInfo().get("email"));
        output.out("Postal address: " + member.getUserInfo().get("address"));
        output.line();
        output.out("(1). View current rentals");
        output.out("(2). Return digital rentals");
        output.out("(3). View rental history");
        output.out("(4). View purchase history");
        output.out("(5). Add credit");
        output.out("(6). Edit account details");
        output.out("(0). Exit");

        while(selection != 0){
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid input. Please try again.");
                selection = -1;
            }
            switch(selection){
                case 1:
                    this.viewAllRentals();
                    break;
                case 2:
                    this.returnDigitalRentals();
                    break;
                case 3:
                    this.viewRentalHistory();
                    break;
                case 4:
                    this.viewPurchaseHistory();
                    break;
                case 5:
                    this.addCredit();
                    break;
                case 6:
                    this.editAccountDetails();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void editAccountDetails(){
        Integer selection = 9;
        this.menuTitle("Edit account details | My account | VixFix Store");
        output.out("(1). Change your password");
        output.out("(2). Change your postal address");
        output.out("(3). Change your e-email address");
        output.out("(0). Exit");
        while(selection != 0){
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid input. Please try again.");
                selection = -1;
            }
            switch(selection){
                case 1:
                    this.editPassword();
                    break;
                case 2:
                    this.editAddress();
                    break;
                case 3:
                    this.editEmail();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void viewAllRentals(){
        this.menuTitle("Current rentals | My account | VixFix Store");
        output.out("Digital rentals");
        output.line();
        if(userCart.getDigitalItems().keySet().size() > 0){
            int count = 1;
            for(Object each : userCart.getDigitalItems().keySet()){
                output.out(count++ + " " + each.toString());
            }
        } else {
            output.out("No current rentals found.");
        }
        output.out("DVD rentals");
        output.line();
        if(userCart.getPhysicalItems().keySet().size() > 0){
            int count = 1;
            for(Object each : userCart.getPhysicalItems().keySet()){
                output.out(count++ + " " + each.toString());
            }
        } else {
            output.out("No current rentals found.");
        }
        output.out("(0). Exit");
    }

    public void viewPurchaseHistory(){
        this.menuTitle("Purchase history | My account | VixFix Store");
        if(member.getPurchaseHistory().keySet().size() > 0){
            for(Object each : member.getPurchaseHistory().keySet()){
                output.out(each.toString());
            }
        } else {
            output.out("You are yet to purchase a movie.");
        }
    }

    public void viewRentalHistory(){
        this.menuTitle("Rental history | My account | VixFix Store");
        if(member.getRentalHistory().keySet().size() > 0){
            for(Object each : member.getRentalHistory().keySet()){
                output.out(each.toString());
            }
        } else {
            output.out("You are yet to rent a movie.");
        }
    }

    public void addCredit(){
        this.menuTitle("Add credit | My account | VixFix Store");
        try{
            double amount = input.getDouble("How much credit would you like to add?");
            member.addCredit(amount);
        } catch (Exception e){
            output.out("You have entered an invalid value.");
        }
    }

    public void editPassword(){
        String first;
        String second;
        first = input.getString("Enter new password");
        second = input.getString("Confirm new password");
        if(first.equals(second)){
            this.user.setAddress(first);
        } else {
            output.out("Entered passwords do not match.");
        }
    }

    public void editEmail(){
        String email;
        email = input.getString("Enter new e-mail address");
        if(email.matches(".*@.*")){
            this.user.setEmail(email);
        } else {
            output.out("Invalid e-mail address entered.");
        }
    }

    public void editAddress(){
        String address;
        address = input.getString("Enter new postal address");
        if(address.length() > 5){
            this.user.setEmail(address);
        } else {
            output.out("Invalid postal address entered.");
        }
    }

    public void returnDigitalRentals(){
        // do stuff
    }
    
}
