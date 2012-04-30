package Menu;

import Exceptions.InsufficientCredit;
import Exceptions.InvalidInput;
import Exceptions.InvalidLogin;
import Library.*;
import Users.*;
import IO.*;

import java.util.ArrayList;
import java.util.Map;

public class Menu {
    // init system instances.
    private Library library = new Library();
    private Authentication auth = new Authentication();
    private Accounting systemAccount = new Accounting();
    private User user;
    private Member member;
    Cart userCart;
    private boolean authenticated = false;
    Input input = new Input();
    Output output = new Output();

    private ArrayList<Movie> tempResults = new ArrayList<Movie>();
    
    public Menu(){
        output.out("Initialising...");
        while(!authenticated){
            this.authenticated = this.login();
        }
        if(this.user instanceof Admin){
            this.initMenu("admin");
        } else {
            this.member = (Member)this.user;
            if(this.user instanceof PremiumMember){
                this.userCart = new PremiumCart((Member) user);
            }else{
                this.userCart = new Cart((Member) user);
            }
            this.initMenu("");
        }
    }

    private boolean login(){
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
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.initMenu();

        } else {
            while(true){
                Integer input = mainMenu();
                switch(input){
                    case 1:
                        this.digiRent();
                        break;
                    case 2:
                        this.digiBuy();
                        break;
                    case 3:
                        this.DVD_Rent();
                        break;
                    case 4:
                        this.DVD_Buy();
                        break;
                    case 5:
                        this.shoppingCart();
                        break;
                    case 6:
                        this.myAccount();
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
            output.out("Invalid input. Please try again.");
            return -1;

        }
    }



    void menuTitle(String title){
        output.line();
        output.out(title);
        output.line();
    }

    private void shoppingCart(){
        this.menuTitle("My shopping cart | VixFix Store");
        output.out("[Digital movies]");
        output.line();
        if(userCart.getDigitalItems().keySet().size() > 0){
            int count = 1;
            for(Object each : userCart.getDigitalItems().keySet()){
                if(userCart.getDigitalItems().get(each).equals("rent")){
                    output.out(count++ + " " + each.toString() + ": " + userCart.constRentalCost());
                } else {
                    output.out(userCart.getDigitalItems().get(each).toString());
                }
            }
            output.out("Digital total: ");
        } else {
            output.out("No digital items in cart.");
        }
        output.out("[DVD movies]");
        output.line();
        if(userCart.getPhysicalItems().keySet().size() > 0){
            int count = 1;
            for(Object each : userCart.getPhysicalItems().keySet()){
                output.out(count++ + " " + each.toString() + ": " + userCart.constPurchaseCost());
            }
        } else {
            output.out("No DVD titles items in cart.");
        }

        Integer selection = 9;
        output.out("(1). Checkout cart");
        output.out("(2). Remove a digital item");
        output.out("(3). Remove a DVD item");
        output.out("(4). Reset cart");
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
                    this.checkOut();
                    break;
                case 2:
                    this.cartRemoveDigi();
                    break;
                case 3:
//                    this.cartRemoveDVD();
                    break;
                case 4:
//                    this.cartReset();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void cartRemoveDigi(){
        if(userCart.getDigitalItems().size() > 0){
            int selection = Integer.MAX_VALUE;
            while(!(selection <= userCart.getDigitalItems().size())){
                try {
                    selection = input.getInt("Please select item to remove");
                } catch (Exception e) {
                    output.out("Invalid input. Please try again.");
                    selection = -1;
                }
//                userCart.removeItem(userCart.getDigitalItems().keySet()[select]);
            }


        } else {
            output.out("No digital items in cart.");
        }
    }

    private void digiRent(){
        String search;
        search = input.getString("Search for movie title");
        this.tempResults = library.getDigiMovies(search, "rent");
        if(tempResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(Movie each : tempResults){
                output.out("(" + counter++ + ") " + each.getTitle());
            }
            while(!(selection <= tempResults.size() - 1)){
                try{
                    selection = input.getInt("Select digital title you wish to rent");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            userCart.addDigitalItem((DigitalMovie)tempResults.get(selection), "rent");
            output.out("\"" + tempResults.get(selection).getTitle() + "\" added to cart");
        } else{
            output.out("No matching movies found. :(");
        }
    }

    private void digiBuy(){
        String search;
        search = input.getString("Search for movie title");
        this.tempResults = library.getDigiMovies(search, "buy");
        if(tempResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(Movie each : tempResults){
                output.out("(" + counter++ + ") " + each.getTitle());
            }
            while(!(selection <= tempResults.size() - 1)){
                try{
                    selection = input.getInt("Select digital title you wish to purchase");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            userCart.addDigitalItem((DigitalMovie)tempResults.get(selection), "buy");
            output.out("\"" + tempResults.get(selection).getTitle() + "\" added to cart");
        } else{
            output.out("No matching movies found. :(");
        }
    }

    private void DVD_Rent(){
        String search;
        search = input.getString("Search for movie title");
        this.tempResults = library.getPhyMovies(search, "rent");
        if(tempResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(Movie each : tempResults){
                output.out("(" + counter++ + ") " + each.getTitle());
            }
            while(!(selection <= tempResults.size() - 1)){
                try{
                    selection = input.getInt("Select DVD title you wish to rent");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            userCart.addItem(tempResults.get(selection), "rent");
            output.out("\"" + tempResults.get(selection).getTitle() + "\" added to cart");
        } else{
            output.out("No matching movies found. :(");
        }
    }

    private void DVD_Buy(){
        String search;
        search = input.getString("Search for movie title");
        this.tempResults = library.getPhyMovies(search, "buy");
        if(tempResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(Movie each : tempResults){
                output.out("(" + counter++ + ") " + each.getTitle());
            }
            while(!(selection <= tempResults.size() - 1)){
                try{
                    selection = input.getInt("Select DVD title you wish to purchase");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            userCart.addItem(tempResults.get(selection), "buy");
            output.out("\"" + tempResults.get(selection).getTitle() + "\" added to cart");
        } else{
            output.out("No matching movies found. :(");
        }
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