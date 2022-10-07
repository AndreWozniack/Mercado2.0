package Sys.erros;

public class NotFoundProd extends Exception{
    public NotFoundProd(String msg){
        super(msg);
    }
    public NotFoundProd(String msg, Throwable cause){
        super(msg, cause);
    }
    public NotFoundProd(){}
}

