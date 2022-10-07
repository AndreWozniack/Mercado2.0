package Sys.erros;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(){}
    public InvalidPassword(String msg){
        super(msg);
    }
    public InvalidPassword(String msg, Throwable cause) {
        super(msg, cause);
    }
}
