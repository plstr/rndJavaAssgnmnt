package Menu;

import Exceptions.InsufficientCredit;
import Exceptions.InvalidInput;
import Exceptions.InvalidLogin;
import Library.*;
import Users.*;
import IO.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class Menu {
    // init system instances.
    private Library library = new Library();
    private Authentication auth = new Authentication();
    private Accounting systemAccount = new Accounting();
    private StoreSettings settings = new StoreSettings();

    private User user;
    private Member member;
    protected Cart userCart;
    private boolean authenticated = false;

    final Input input = new Input();
    final Output output = new Output();

    private ArrayList<Movie> tempResults = new ArrayList<Movie>();
    private ArrayList<PhysicalMovie> tempDVDResults = new ArrayList<PhysicalMovie>();
    
    public Menu(){
        output.out("Initialising store...");

    }

    public void start(){
        while(!authenticated){
            this.authenticated = this.login();
        }
        if(this.user instanceof Admin){
            this.initMenu("admin");
        } else {
            this.member = (Member)this.user;
            if(this.user instanceof PremiumMember){
                this.userCart = new PremiumCart((Member) user, this.settings);
            }else{
                this.userCart = new Cart((Member) user, this.settings);
            }
            this.initMenu("");
        }
    }

    private boolean login(){
        String username;
        String password;
        this.menuTitle("Welcome to VixFix Store");
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
        this.authenticated = false;

    }

    private void initMenu(String userType) {
        // main menu loop
        if(userType.equals("admin")){
            this.adminMenu();

        } else {
            this.mainMenu();
        }
    }

    // -----------------------
    // Member menu section
    // -----------------------

    private void mainMenu() {
        // root Member menu
        while(this.authenticated){
            this.menuTitle("VixFix Store: ");
            output.out("(1). Digital rent");
            output.out("(2). Digital purchase");
            output.out("(3). DVD rent");
            output.out("(4). DVD purchase");
            output.out("(5). My shopping cart");
            output.out("(6). My account");
            output.out("(0). Exit");
            output.blankLine();
            Integer selection = 9;
            while(selection > 6)
            try {
                selection =  input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid input. Please try again.");
            }

            switch(selection){
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
                    output.blankLine();
                    this.logout();
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    void menuTitle(String title){
        output.line();
        output.out(title);
        output.line();
    }

    private void shoppingCart(){
        // Shopping cart sub-menu
        this.menuTitle("My shopping cart | VixFix Store");
        output.out("[Digital movies]");
        output.line();
        if(userCart.getDigitalItems().keySet().size() > 0){
            int count = 1;
            for(Object each : userCart.getDigitalItems().keySet()){
                if(userCart.getDigitalItems().get(each).equals("rent")){
                    output.out(count++ + " " + each.toString() + ": " +
                            userCart.constRentalCost());
                } else {
                    output.out(count++ + " " + each.toString() + ": " +
                            userCart.constPurchaseCost());
                }
            }
            output.out("Digital total: " + (userCart.getDigiPurchaseCost() +
                    userCart.getDigiRentalCost()));
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
            output.out("DVD movies total: " + (userCart.getPhyPurchaseCost() +
                    userCart.getPhyRentalCost()));
        } else {
            output.out("No DVD titles items in cart.");
        }
        output.out("Shipping cost: " + userCart.getTotalShippingCost());
        output.out("Cart total: " + userCart.getTotalCost());
        output.blankLine();
        Integer selection = 9;
        output.blankLine();
        output.out("(1). Checkout cart");
        output.out("(2). Reset cart");
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
                    this.cartReset();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void cartReset(){
        userCart.clear();
        output.out("Your shopping cart has been reset.");
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
        this.tempDVDResults = library.getPhyMovies(search, "rent");
        if(tempDVDResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(PhysicalMovie each : tempDVDResults){
                output.out("(" + counter++ + ") " + each.getTitle() + each.checkAvailability());
            }
            while(!(selection <= tempDVDResults.size() - 1)){
                try{
                    selection = input.getInt("Select DVD title you wish to rent");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            PhysicalMovie movie = tempDVDResults.get(selection);
            if(movie.getQuantity() > 0){
                userCart.addItem(tempDVDResults.get(selection), "rent");
                output.out("\"" + tempDVDResults.get(selection).getTitle() + "\" added to cart");
            } else {
                output.out("Sorry, requested item is currently out of stock.");
            }

        } else{
            output.out("No matching movies found. :(");
        }
    }

    private void DVD_Buy(){
        String search;
        search = input.getString("Search for movie title");
        this.tempDVDResults = library.getPhyMovies(search, "buy");
        if(tempDVDResults.size() > 0){
            int counter = 1;
            int selection = Integer.MAX_VALUE;
            for(PhysicalMovie each : tempDVDResults){
                output.out("(" + counter++ + ") " + each.getTitle());
            }
            while(!(selection <= tempDVDResults.size() - 1)){
                try{
                    selection = input.getInt("Select DVD title you wish to purchase");
                } catch (InvalidInput e){
                    output.out("Invalid selection.");
                }
            }
            userCart.addItem(tempDVDResults.get(selection), "buy");
            output.out("\"" + tempDVDResults.get(selection).getTitle() + "\" added to cart");
        } else{
            output.out("No matching movies found. :(");
        }
    }

    public void checkOut(){
        try{
            userCart.checkout();
            // general stats collection
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
            Map digitalItems = userCart.getDigitalItems();
            for(Object each : digitalItems.keySet()){
                DigitalMovie movie = (DigitalMovie)each;
                if(digitalItems.get(each).equals("rent")){
                    movie.increaseRentCount();
                    library.addDigiRental(movie.getID(), user.getUID());
                } else if(digitalItems.get(each).equals("buy")){
                    movie.increasePurchaseCount();
                }
            }

            Map DVDItems = userCart.getPhysicalItems();
            for(Object each : DVDItems.keySet()){
                PhysicalMovie movie = (PhysicalMovie)each;
                if(DVDItems.get(each).equals("rent")){
                    movie.increaseRentCount();
                    movie.decreaseQuantity();
                    library.addDigiRental(movie.getBarcode(), user.getUID());
                } else if(DVDItems.get(each).equals("buy")){
                    movie.increasePurchaseCount();
                    movie.decreaseQuantity();
                }
            }
            // clear cart
            member.deductCredit(userCart.getTotalCost());
            userCart.removeAllItems();
            output.out("Thank you, your purchase has been successful.");
            output.blankLine();
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
        output.out("Current balance: " + member.getCredit());
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
        output.out("[Digital rentals]");
        output.line();
        if(member.getCurrentDigiRentals().size() > 0){
            int count = 1;
            for(Object each : member.getCurrentDigiRentals()){
                output.out(count++ + " " + each.toString());
            }
        } else {
            output.out("No current rentals found.");
        }
        output.out("[DVD rentals]");
        output.line();
        if(member.getCurrentDVDRentals().size() > 0){
            int count = 1;
            for(Object each : member.getCurrentDVDRentals()){
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

    // ---------------------------
    // Admin Menu Section
    // ---------------------------

    public void adminMenu() {
        // admin menu
        int selection = -1;
        while (authenticated){
            menuTitle("VixFix Store Admin Console");
            output.out("(1). Movies");
            output.out("(2). Users");
            output.out("(3). Collect Membership Fee");
            output.out("(4). Rentals View & Return");
            output.out("(5). View Store Statistics");
            output.out("(6). Edit Store Settings");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    adminMovies();
                    break;
                case 2:
                    adminUsers();
                    break;
                case 3:
                    collectFees();
                    break;
                case 4:
                    adminRentals();
                    break;
                case 5:
                    viewStats();
                    break;
                case 6:
                    settingsMenu();
                    break;
                case 0:
                    output.out("Thank you.");
                    output.blankLine();
                    this.logout();
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void adminMovies(){
        // CRUD movies
        int selection = -1;
        while (selection != 0){
            menuTitle("Admin movies | VixFix Store Admin Console");
            output.out("(1). Add digital movie");
            output.out("(2). Add DVD movie");
            output.out("(3). Edit digital movie");
            output.out("(4). Edit DVD movie");
            output.out("(5). Delete digital movie");
            output.out("(6). Delete DVD movie");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    addDigiMovie();
                    break;
                case 2:
                    addDVDMovie();
                    break;
                case 3:
                    editDigiMovie();
                    break;
                case 4:
                    editDVDMovie();
                    break;
                case 5:
                    deleteDigiMovie();
                    break;
                case 6:
                    deleteDVDMovie();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void addDigiMovie(){
        menuTitle("Add digital movie | Admin movies | VixFix Store Admin Console");
        String title = input.getString("Enter digital movie title");
        String director = input.getString("Enter movie director");
        String genre = input.getString("Enter movie genre (e.g sci-fi,comedy");
        String year = input.getString("Enter movie release year");
        String length = input.getString("Enter movie length (e.g. 120)");
        String cast = input.getString("Enter movie cast (e.g. Tom Hanks,Ed Murphy)");
        String fileSize = input.getString("Enter movie file size in MB (e.g. 512)");
        String fileFormat = input.getString("Enter movie file format (e.g. xvid)");
        String rent = input.getString("Is movie available for rent? (Y/N)");
        String buy = input.getString("Is movie available for purchase (Y/N)");
        Boolean bRent = false;
        Boolean bBuy = false;

        if(rent.matches("(Y|y)")){
            bRent = true;
        }

        if(buy.matches("(Y|y)")){
            bBuy = true;
        }

        library.addDigiMovie(title, director, genre,
                year, length, cast, fileSize, fileFormat, bRent, bBuy);

        output.out(title + " successfully added.");
    }

    public void addDVDMovie(){
        menuTitle("Add DVD movie | Admin movies | VixFix Store Admin Console");
        String title = input.getString("Enter movie title");
        String director = input.getString("Enter movie director");
        String genre = input.getString("Enter movie genre (e.g sci-fi,comedy");
        String year = input.getString("Enter movie release year");
        String length = input.getString("Enter movie length (e.g. 120)");
        String cast = input.getString("Enter movie cast (e.g. Tom Hanks,Ed Murphy)");
        String quantity = input.getString("Enter quantity of movie copies in stock");
        String rent = input.getString("Is movie available for rent? (Y/N)");
        String buy = input.getString("Is movie available for purchase (Y/N)");

        Boolean bRent = false;
        Boolean bBuy = false;

        if(rent.matches("(Y|y)")){
            bRent = true;
        }

        if(buy.matches("(Y|y)")){
            bBuy = true;
        }

        library.addDVDMovie(title, director, genre,
                year, length, cast, Integer.parseInt(quantity), bRent, bBuy);

        output.out(title + " successfully added.");
    }

    public void editDigiMovie(){
        menuTitle("Edit digital movie | Admin movies | VixFix Store Admin Console");
        String id = input.getString("Enter ID of digital movie you wish to edit");
        boolean check = library.checkDigiMovie(Integer.parseInt(id));
        if(check){
            DigitalMovie movie = library.getDigiMovie(Integer.parseInt(id));
            output.out(movie.toString());
            int selection = -1;
            while (selection != 0){
                menuTitle("Edit Digital Movie | Admin movies | VixFix Store Admin Console");
                output.out("(1). Edit title");
                output.out("(2). Edit director");
                output.out("(3). Edit cast");
                output.out("(4). Edit file size");
                output.out("(5). Edit file format");
                output.out("(0). Exit");
                output.blankLine();
                try {
                    selection = input.getInt("Please enter selection");
                } catch (Exception e) {
                    output.out("Invalid selection.");
                }
                switch(selection){
                    case 1:
                        String title = input.getString("Enter new title");
                        movie.setTitle(title);
                        break;
                    case 2:
                        String director = input.getString("Enter new director");
                        movie.setDirector(director);
                        break;
                    case 3:
                        String cast = input.getString("Enter new cast");
                        movie.setCast(cast);
                        break;
                    case 4:
                        String filesize = input.getString("Enter new file size");
                        movie.setFileSize(filesize);
                        break;
                    case 5:
                        String fileformat = input.getString("Enter new file format");
                        movie.setFileFormat(fileformat);
                        break;
                    case 0:
                        break;
                    default:
                        output.outInline("Please select from the menu.");
                }
            }

        } else {
            output.out("No movie item with ID: " + id + " found in library.");
        }
    }


    public void editDVDMovie(){
        menuTitle("Edit digital movie | Admin movies | VixFix Store Admin Console");
        String id = input.getString("Enter barcode of DVD movie you wish to edit");
        boolean check = library.checkDVDMovie(Integer.parseInt(id));
        if(check){
            PhysicalMovie movie = library.getDVDMovie(Integer.parseInt(id));
            output.out(movie.toString());
            int selection = -1;
            while (selection != 0){
                menuTitle("Edit DVD Movie | Admin movies | VixFix Store Admin Console");
                output.out("(1). Edit title");
                output.out("(2). Edit director");
                output.out("(3). Edit cast");
                output.out("(4). Edit quantity");
                output.out("(5). Edit package weight");
                output.out("(0). Exit");
                output.blankLine();
                try {
                    selection = input.getInt("Please enter selection");
                } catch (Exception e) {
                    output.out("Invalid selection.");
                }
                switch(selection){
                    case 1:
                        String title = input.getString("Enter new title");
                        movie.setTitle(title);
                        break;
                    case 2:
                        String director = input.getString("Enter new director");
                        movie.setDirector(director);
                        break;
                    case 3:
                        String cast = input.getString("Enter new cast");
                        movie.setCast(cast);
                        break;
                    case 4:
                        String qnt = input.getString("Enter new quantity");
                        movie.setQnt(qnt);
                        break;
                    case 5:
                        String weight = input.getString("Enter new weight");
                        movie.setWeight(weight);
                        break;
                    case 0:
                        break;
                    default:
                        output.outInline("Please select from the menu.");
                }
            }

        } else {
            output.out("No movie item with ID: " + id + " found in library.");
        }
    }

    public void deleteDigiMovie(){
        menuTitle("Delete digital movie | Admin movies | VixFix Store Admin Console");
        String id = input.getString("Enter digital movie ID you wish to delete");
        boolean check = library.deleteDigiMovie(Integer.parseInt(id));
        if(check){
            output.out("Movie item - \"" + id + "\" successfully deleted.");
        } else {
            output.out("No movie item with ID: " + id + " found in library.");
        }
    }

    public void deleteDVDMovie(){
        menuTitle("Delete DVD movie | Admin movies | VixFix Store Admin Console");
        String barcode = input.getString("Enter DVD movie barcode you wish to delete");
        boolean check = library.deleteDVDMovie(Integer.parseInt(barcode));
        if(check){
            output.out("Movie item - \"" + barcode + "\" successfully deleted.");
        } else {
            output.out("No movie item with barcode: " + barcode + " found in library.");
        }
    }

    public void adminUsers(){
        // CRUD users
        int selection = -1;
        while (selection != 0){
            Boolean admin = false;
            menuTitle("Admin users | VixFix Store Admin Console");
            output.out("(1). Add member");
            output.out("(2). Add administrator");
            output.out("(3). Edit user");
            output.out("(4). Delete user");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    this.addUser(admin);
                    break;
                case 2:
                    admin = true;
                    this.addUser(admin);
                    break;
                case 3:
                    this.editUser();
                    break;
                case 4:
                    this.deleteUser();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void addUser(Boolean admin){
        String type = "";
        String title = "Add user";
        if(admin){
            title = "Add administrator";
        }
        menuTitle(title + " | Admin users | VixFix Store Admin Console");
        String uid = input.getString("Enter user ID");
        String password = input.getString("Enter password");
        String fullname = input.getString("Enter full name");
        String email = input.getString("Enter e-mail address");
        String address = input.getString("Enter postal address");
        if(admin) {
            type = input.getString("Enter membership type (standard/premium)");
        }
        if(admin){
            auth.addUser(uid, password, fullname, email, address);
        } else {
            auth.addUser(uid, password, fullname, email, address, type);
        }

    }

    public void editUser(){
        menuTitle("Edit user | Admin users | VixFix Store Admin Console");
        String email = input.getString("Enter e-mail address of user you wish to edit");
        boolean check = auth.isUser(email);
        if(check){
            User user = auth.getUser(email);
            output.out(user.toString());
            int selection = -1;
            while (selection != 0){
                menuTitle("Edit user | Admin users | VixFix Store Admin Console");
                output.out("(1). Edit e-mail address");
                output.out("(2). Edit password");
                output.out("(3). Edit address");
                output.out("(4). Edit full name");
                output.out("(0). Exit");
                output.blankLine();
                try {
                    selection = input.getInt("Please enter selection");
                } catch (Exception e) {
                    output.out("Invalid selection.");
                }
                switch(selection){
                    case 1:
                        String newEmail = input.getString("Enter new e-mail address");
                        user.setAddress(newEmail);
                        break;
                    case 2:
                        String password = input.getString("Enter new password");
                        user.setPassword(password);
                        break;
                    case 3:
                        String address = input.getString("Enter new address");
                        user.setAddress(address);
                        break;
                    case 4:
                        String fullname = input.getString("Enter new full name");
                        user.setFullName(fullname);
                        break;
                    case 0:
                        break;
                    default:
                        output.outInline("Please select from the menu.");
                }
            }

        } else {
            output.out("No user with email: " + email + " found in system.");
        }
    }

    public void deleteUser(){
        menuTitle("Delete user | Admin users | VixFix Store Admin Console");
        String email = input.getString("Enter email of user you wish to delete");
        boolean check = auth.deleteUser(email);
        if(check){
            output.out("User - \"" + email + "\" successfully deleted.");
        } else {
            output.out("No user with e-mail: " + email + " found in system.");
        }
    }

    public void adminRentals(){
        int selection = -1;
        while (selection != 0){
            menuTitle("Admin rentals | VixFix Store Admin Console");
            output.out("(1). View rentals");
            output.out("(2). Process DVD rental return");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    this.viewCurrentRentals();
                    break;
                case 2:
                    this.processReturns();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void viewCurrentRentals(){
        menuTitle("Current rentals | Admin rentals | VixFix Store Admin Console");
        Map digiRentals = library.getDigiRentals();
        Map DVDRentals = library.getDVDRentals();


    }

    public void processReturns(){
        menuTitle("Process returns | Admin rentals | VixFix Store Admin Console");
        try{
            int barcode = input.getInt("Enter barcode of DVD return");
            if(library.checkDVDMovie(barcode)){
                int daysLate = input.getInt("Enter number of late days");
                library.returnDVDRental(barcode, daysLate);
            } else {
                output.out("No such barcode found in library.");
            }
        } catch (InvalidInput e){
            output.out("Invalid barcode entered.");
        }
    }

    public void collectFees(){
        menuTitle("Collect membership fees | VixFix Store Admin Console");
        double collected = auth.deductPremiumFees();
        output.out("" + collected + " has been collected in membership fees.");
        output.blankLine();
    }

    public void viewStats(){
        int selection = -1;
        while (selection != 0){
            menuTitle("View stats | VixFix Store Admin Console");
            output.out("(1). View store account");
            output.out("(2). View individual movie stats");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    this.viewStoreAccount();
                    break;
                case 2:
                    this.viewMovieStats();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void viewStoreAccount(){
        menuTitle("View store account | View stats | VixFix Store Admin Console");
        output.out(systemAccount.toString());
    }

    public void viewMovieStats(){
        menuTitle("Movie stats | View stats | VixFix Store Admin Console");
        try{
            Movie movie = this.library.getDVDMovie(input.getInt("Enter movie barcode"));
            output.out(movie.getStats());
        } catch (InvalidInput e){
            output.out("Invalid input.");
        }
    }


    public void settingsMenu(){
        int selection = -1;
        while (selection != 0){
            menuTitle("Store settings | VixFix Store Admin Console");
            output.out("(1). View settings");
            output.out("(2). Edit settings");
            output.out("(0). Exit");
            output.blankLine();
            try {
                selection = input.getInt("Please enter selection");
            } catch (Exception e) {
                output.out("Invalid selection.");
            }
            switch(selection){
                case 1:
                    this.viewSettings();
                    break;
                case 2:
                    this.editSettings();
                    break;
                case 0:
                    break;
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void viewSettings(){
        menuTitle("View settings | VixFix Store Admin Console");
        output.out(this.settings.toString());
    }

    public void editSettings(){
        try{
            this.settings.setPurchaseCost(input.getDouble("Enter purchase cost"));
            this.settings.setRentalCost(input.getDouble("Enter rental cost"));
            this.settings.setLateFee(input.getDouble("Enter late fee cost"));
            this.settings.setSingleShippingCost(input.getDouble("Enter shipping cost"));
        }catch (Exception e){
            output.out("Invalid input.");
        }

    }

}