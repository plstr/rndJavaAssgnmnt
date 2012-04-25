package Menu;
import IO.*;
public class Access {
    public boolean Authenticate(){
        Input input = new Input();
        boolean success = false;
        Output.out("Please login");
        Output.outInline("Username >");
        // put back the optional message input on Input methods, 2 constructors
        input.getString();
        return success;
    }
}
