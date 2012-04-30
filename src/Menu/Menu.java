package Menu;

import Exceptions.InsufficientCredit;
import Exceptions.InvalidLogin;
import Library.*;
import Users.*;
import IO.*;

import java.util.ArrayList;
import java.util.Set;

public class Menu {
    // init system instances.
    private Library library = new Library();
    private Authentication auth = new Authentication();
    private Accounting systemAccount = new Accounting();
    private User user;
    private Cart userCart;
    private boolean authenticated = false;
    private Input input = new Input();
    private Output output = new Output();
    
    public Menu(){
        output.out("Initialising...");
        while(!authenticated){
            this.authenticated = this.login();
        }
        if(this.user instanceof Admin){
            this.initMenu("admin");
        } else {
            this.initMenu("");
        }
    }

    public boolean login(){
        String username;
        String password;
        output.out("Please login:");
        username = input.getString("E-mail");
        password = input.getString("Password");
        try {
            this.user = auth.checkUser(username, password);
            return true;
        } catch (InvalidLogin e){
            output.out("Invalid login.");
            return false;
        }
    }

    public void logout(){

    }

    private void initMenu(String userType) {
        // main menu loop
        if(userType.equals("admin")){
            while (true){
                Integer input = adminMenu();
                switch(input){
                    case 1:
                        //                    buyMenu();
                        break;
                    case 2:
                        //                    rentMenu();
                        break;
                    case 3:
                        //                    listPurchases();
                        break;
                    case 4:
                        //listRentals();
                        break;
                    case 0:
                        output.out("Thank you.");
                        System.exit(0);
                    default:
                        output.outInline("Please select from the menu.");
                }
            }
        } else {
            while(true){
                Integer input = mainMenu();
                switch(input){
                    case 1:
    //                    digiRent();
                        break;
                    case 2:
    //                    digiBuy();
                        break;
                    case 3:
    //                    DVD_Rent();
                        break;
                    case 4:
//                        DVD_Buy();
                        break;
                    case 5:
//                        shoppingCart();
                        break;
                    case 6:
//                        myAccount ();
                        break;
                    case 0:
                        output.out("Thank you.");
                        System.exit(0);
                    default:
                        output.outInline("Please select from the menu.");
                }
            }
        }
    }

    private Integer mainMenu() {
        // root menu
        this.menuTitle("VixFix Store: ");
        output.out("(1). Digital rent");
        output.out("(2). Digital purchase");
        output.out("(3). DVD rent");
        output.out("(4). DVD purchase");
        output.out("(5). My shopping cart");
        output.out("(6). My account");
        output.out("(0). Exit");
        output.blankLine();
        try {
            return input.getInt("Please enter selection");
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer adminMenu() {
        // admin menu
        this.menuTitle("VixFix Store Admin Console: ");
        output.out("(1). Edit Movies");
        output.out("(2). Edit Users");
        output.out("(3). Collect Membership Fee");
        output.out("(4). Rentals View & Return");
        output.out("(5). View Store Statistics");
        output.out("(6). Edit Store Settings");
        output.out("(0). Exit");
        output.blankLine();
        try {
            return input.getInt("Please enter selection");
        } catch (Exception e) {
            return 0;
        }
    }

    private void menuTitle(String title){
        output.line();
        output.out(title);
        output.line();
    }

    public void checkOut(){
        try{
            userCart.checkout();
            // stats collection
            if(userCart.getPhyPurchaseCost() > 0.0){
                systemAccount.addPhyPurchases(userCart.getPhyPurchaseCost());
            }
            if(userCart.getPhyRentalCost() > 0.0){
                systemAccount.addPhyRentals(userCart.getPhyRentalCost());
            }
            if(userCart.getDigiRentalCost() > 0.0){
                systemAccount.addDigitalRentals(userCart.getDigiRentalCost());
            }
            if(userCart.getDigiPurchaseCost() > 0.0){
                systemAccount.addDigitalPurchases(userCart.getDigiPurchaseCost());
            }
            // clear cart
            userCart.removeAllItems();
        } catch (InsufficientCredit e){
            output.out("Insufficient funds. Please add credit to continue.");
        }
    }

    public void myAccount(){
        this.menuTitle("My account | VixFix Store");
        output.out("(1). View current rentals");
        output.out("(3). Return digital rentals");
        output.out("(3). View rental history");
        output.out("(2). View purchase history");
        output.out("(4). Add credit");
        output.out("(5). Edit account details");
        output.out("(0). Exit");
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
        if(user.getPurchaseHistory.keySet().size() > 0){
            for(Object each : user.getPurchaseHistory.keySet()){
                output.out(each.toString());
            }
        } else {
            output.out("You are yet to purchase a movie.");
        }
    }

    public void viewRentalHistory(){
        this.menuTitle("Rental history | My account | VixFix Store");
        if(user.getRentalHistory.keySet().size() > 0){
            for(Object each : user.getRentalHistory.keySet()){
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
            user.addCredit(amount);
        } catch (Exception e){
            output.out("You have entered an invalid value.");
        }

    }

    public void editAccountDetails(){
        this.menuTitle("Edit account details | My account | VixFix Store");
        output.out("(1). Change your password");
        output.out("(3). Change your postal address");
        output.out("(3). Change your e-email address");
        output.out("(0). Exit");
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