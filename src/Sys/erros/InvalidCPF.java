package Sys.erros;

public class InvalidCPF extends RuntimeException {
    public InvalidCPF(String msg){
        super(msg);
    }
    public InvalidCPF(String msg, Throwable cause) {
        super(msg, cause);
    }
    public InvalidCPF(){}
}
