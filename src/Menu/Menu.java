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
        output.out("Hi...");
        while(!authenticated){
            this.authenticated = this.login();
        }
        initMenu();
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

    public void initMenu() {
        // main menu loop
        while(true){
            Integer input = mainMenu();
            switch(input){
                case 1:
                    output.blankLine();
//                    buyMenu();
                    break;
                case 2:
                    output.blankLine();
//                    rentMenu();
                    break;
                case 3:
                    output.blankLine();
//                    listPurchases();
                    break;
                case 4:
                    output.blankLine();
                    //listRentals();
                    break;
                case 5:
                    output.blankLine();
                    output.out("Thank you.");
                    System.exit(0);
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    private Integer mainMenu() {
        // root menu
        this.menuTitle("VixFix Store: ");
        output.out("(1). Purchase Movies");
        output.out("(2). Rent Movies");
        output.out("(3). List Purchases");
        output.out("(4). List Rentals");
        output.out("(5). Exit");
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