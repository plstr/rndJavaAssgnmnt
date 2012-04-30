package Menu;

public class CartMenu extends Menu{
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
}
