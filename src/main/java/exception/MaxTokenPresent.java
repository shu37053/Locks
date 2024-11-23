package exception;

public class MaxTokenPresent extends Exception{
    public MaxTokenPresent() {
        super("Received more token then generated");
    }
}
