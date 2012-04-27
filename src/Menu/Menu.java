package Menu;

import Exceptions.InvalidLogin;
import Library.*;
import Users.*;
import IO.*;

import java.util.ArrayList;

public class Menu {

    private Library library = new Library();
    private Authentication auth = new Authentication();
    private User user;
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
            user = auth.checkUser(username, password);
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


}