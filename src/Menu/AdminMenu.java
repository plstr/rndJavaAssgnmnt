package Menu;

public class AdminMenu extends Menu{

    public AdminMenu(){
        this.initMenu();
    }

    public void initMenu() {
        // admin menu
        int selection = -1;

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
        while (selection != 0){
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
                    editSettings();
                    break;
                case 0:
                    output.out("Thank you.");
                    System.exit(0);
                default:
                    output.outInline("Please select from the menu.");
            }
        }
    }

    public void adminMovies(){
        // CRUD movies
        menuTitle("Admin movies | VixFix Store Admin Console");
        output.out("(1). View movie");
        output.out("(2). Edit movie");
        output.out("(3). Delete movie");
        output.out("(0). Exit");
        output.blankLine();
    }

    public void adminUsers(){
        // CRUD users
        menuTitle("Admin users | VixFix Store Admin Console");
        output.out("(1). View users");
        output.out("(2). Edit users");
        output.out("(3). Delete users");
        output.out("(0). Exit");
        output.blankLine();
    }

    public void adminRentals(){
        // CRUD rentals
        menuTitle("Admin rentals | VixFix Store Admin Console");
        output.out("(1). View rentals");
        output.out("(2). Process rental returns");
        output.out("(0). Exit");
        output.blankLine();
    }

    public void collectFees(){
        menuTitle("Collect membership fees | VixFix Store Admin Console");
        double collected = 0;
        // collection functionality
        output.out("" + collected + " has been collected in membership fees.");
        output.blankLine();
    }

    public void viewStats(){
        menuTitle("View stats | VixFix Store Admin Console");
    }

    public void editSettings(){
        menuTitle("Store settings | VixFix Store Admin Console");
        output.out("(1). View settings");
        output.out("(2). Edit settings");
        output.out("(0). Exit");
        output.blankLine();
    }
}
