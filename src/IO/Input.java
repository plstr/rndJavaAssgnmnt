package IO;
import IO.Output;
import Exceptions.InvalidInput;
import java.util.Scanner;

public class Input{
    private Output output = new Output();
    public String getString(String message){
        output.outInline(message + " >");
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

    public Integer getInt(String message) throws Exception{
        return toInt(this.getString(message));
    }

    public Double getDouble() throws Exception{
        return toDouble(this.getString());
    }

    public Double getDouble(String message) throws Exception{
        return toDouble(this.getString(message));
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

    public Double toDouble(String input) throws InvalidInput{
        try{
            if(Double.parseDouble(input) < 0){
                throw new InvalidInput();
            }
            else {
                return Double.parseDouble(input);
            }
        }
        catch (Exception e){
            throw new InvalidInput();
        }
    }

    public void getNothing(){
        Scanner scanner = new Scanner(System.in);
        output.outInline("> Press enter to continue.");
        scanner.nextLine();
    }


}
