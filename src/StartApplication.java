import Library.Library;
import Menu.Menu;

public class StartApplication{
    // start the application
    public static void main(String[] args){
        Menu application = new Menu();
        while(true){
            application.start();
        }
    }
}