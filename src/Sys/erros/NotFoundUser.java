package Sys.erros;

public class NotFoundUser extends Exception {
    public NotFoundUser(String msg){
        super(msg);
    }
    public NotFoundUser(String msg, Throwable cause){
        super(msg, cause);
    }
    public NotFoundUser(){}
}
