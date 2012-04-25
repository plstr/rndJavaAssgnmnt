package Menu;
import IO.*;
public class Access {
    public boolean Authenticate(){
        Input input = new Input();
        Output output = new Output();
        boolean success = false;
        output.out("Please login");
        // put back the optional message input on Input methods, 2 constructors
        input.getString("Username");
        return success;
    }
}
