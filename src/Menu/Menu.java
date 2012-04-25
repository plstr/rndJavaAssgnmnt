package Menu;

import Library.Library;

import java.util.ArrayList;

public class Menu {

    private IO IOType;
    private Library library = new Library();
    private User user = new User();
    
    public Menu(IO IOType, Library library, User user){
        this.IOType = IOType;
        this.library = library;
        this.user = user;
    }

    public void initMenu() {
        // main menu loop
        while(true){
            Integer input = mainMenu();
            switch(input){
                case 1:
                    IOType.blankLine();
                    buyMenu();
                    break;
                case 2:
                    IOType.blankLine();
                    rentMenu();
                    break;
                case 3:
                    IOType.blankLine();
                    listPurchases();
                    break;
                case 4:
                    IOType.blankLine();
                    listRentals();
                    break;
                case 5:
                    IOType.blankLine();
                    IOType.out("Thank you.");
                    System.exit(0);
                default:
                    IOType.outInline("Please select from the menu.");
            }
        }
    }

    private Integer mainMenu() {
        // root menu
        this.menuTitle("VixFix Store: ");
        IOType.out("(1). Purchase Movies");
        IOType.out("(2). Rent Movies");
        IOType.out("(3). List Purchases");
        IOType.out("(4). List Rentals");
        IOType.out("(5). Exit");
        IOType.blankLine();
        try {
            return IOType.getInt("Please enter selection");
        } catch (Exception e) {
            return 0;
        }
    }

    private void menuTitle(String title){
        IOType.line();
        IOType.out(title);
        IOType.line();
    }
    
    private void buildSubMenu(ArrayList<Library.Movie> results, String itemType){
        // needs to build buy, rent, lists.
        if(itemType.matches("buy")){
            for(int i=0; i<= results.size()-1; i++){
                IOType.outInline("(" + (i + 1) + "). " +
                        results.get(i).getTitle());
                IOType.out(results.get(i).getStatus());
            }
            IOType.blankLine();
            IOType.out("(0). Cancel");
            IOType.blankLine();

            int userInput = IOType.getLimitedInt(results.size(),
                    "Select movie to purchase");
            if(userInput > 0){
                user.buyMovie(results.get(userInput - 1));
                IOType.out("Successfully bought " +
                        results.get(userInput - 1).getTitle());
            } else {
                IOType.out("Cancelled.");
            }
            IOType.blankLine();
            IOType.getNothing();
        }

        if(itemType.matches("rent")){
            for(int i=0; i<= results.size()-1; i++){
                IOType.outInline("(" + (i + 1) + "). " +
                        results.get(i).getTitle());
                IOType.out(results.get(i).getStatus());
            }
            IOType.blankLine();
            IOType.out("(0). Cancel");
            IOType.blankLine();
            int userInput = IOType.getLimitedInt(results.size(),
                    "Select movie to rent");
            if(userInput > 0){
                if(user.listRentedMovies().size() > 1){
                    IOType.out("You have reached maximum rentals amount.");
                    this.returnMovie();
                }
                // assume if you get here with rental list of 2+
                // you cancelled the previous step.
                if(!(user.listRentedMovies().size() > 1)){
                    IOType.out(user.rentMovie(results.get(userInput - 1)));
                }
            } else {
                IOType.out("Cancelled.");
            }
            IOType.blankLine();
            IOType.getNothing();
        }
    }

    private void returnMovie(){
        while(user.listRentedMovies().size() > 0){
            IOType.out("Currently renting:");
            int i = 1;
            for(String each : user.listRentedMovies()){
                IOType.out("(" + i++ + "). " + each);
            }
            IOType.out("(0). Cancel");
            IOType.blankLine();
            int rmUserInput = IOType.getLimitedInt(user.listRentedMovies().size(),
                    "Please select which title you would like to return");
            if(rmUserInput > 0){
                IOType.out("Removed \"" + user.listRentedMovies().get(rmUserInput - 1) +
                        "\" from your rental list.");
                user.rmRentedMovie(rmUserInput - 1);

            } else {
                IOType.blankLine();
                IOType.out("Cancelled.");
                break;
            }
        }
    }
    
    private void buyMenu(){
        menuTitle("Purchase movies");
        String search = IOType.getString("Enter title of movie you wish to purchase");
        ArrayList<Library.Movie> results = library.getMovies(search, "buy");
        if(results.size() > 0){
            IOType.blankLine();
            IOType.out("The following title(s) matches \"" + search +"\": ");
            buildSubMenu(results, "buy");
        } else {
            IOType.out("No such title found.");
            IOType.blankLine();
            IOType.getNothing();
        }
    }

    private void rentMenu(){
        menuTitle("Rent movies");
        String search = IOType.getString("Enter title of movie you wish to rent");
        ArrayList<Library.Movie> results = library.getMovies(search, "rent");
        if(results.size() > 0){
            IOType.blankLine();
            IOType.out("The following title(s) matches \"" + search +"\": ");
            buildSubMenu(results, "rent");
        } else {
            IOType.out("No such title found.");
            IOType.blankLine();
            IOType.getNothing();
        }

    }
    
    private void listPurchases(){
        menuTitle("Your purchases");
        if(user.listBoughtMovies().size() > 0){
            IOType.out("You have bought the following titles: ");
            int i = 1;
            for(String each : user.listBoughtMovies()){
                IOType.out("("+ i++ +"). " + each);
            }
            IOType.blankLine();
        } else {
            IOType.out("No bought movies in your account.");
            IOType.blankLine();
        }
        IOType.getNothing();
    }

    private void listRentals(){
        menuTitle("Your Rentals");
        if(user.listRentedMovies().size() > 0){
            IOType.blankLine();
            this.returnMovie();
        } else {
            IOType.out("No rented movies in your account.");
            IOType.blankLine();
        }
        IOType.getNothing();
    }
}