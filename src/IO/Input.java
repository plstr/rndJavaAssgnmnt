package IO;
import IO.Output;
import Exceptions.InvalidInput;
import java.util.Scanner;

public class Input{

    public String getString(String message){
        IO.Output.outInline(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
    public String getString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public Integer getInt() throws Exception{
        return toInt(this.getString());
    }

    public Integer toInt(String input) throws InvalidInput{
        try{
            if(Integer.parseInt(input) < 0){
                throw new InvalidInput();
            }
            else {
                return Integer.parseInt(input);
            }
        }
        catch (Exception e){
            throw new InvalidInput();
        }
    }

    public void getNothing(){
        Scanner scanner = new Scanner(System.in);
        Output.outInline("> Press enter to continue.");
        scanner.nextLine();
    }


}
