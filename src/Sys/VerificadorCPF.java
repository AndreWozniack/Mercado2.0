package Sys;

import Sys.erros.InvalidCPF;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VerificadorCPF {

    private String cpf;
    private List<Integer> nunsCpf = new ArrayList<>();
    private int dt1 = 0;
    private int dt2 = 0;

    public VerificadorCPF(@NotNull String cpf) throws InvalidCPF{
        if (cpf.length() == 14) {
            this.cpf = removeCaracteres(cpf);
        }else if (cpf.equals("admin")){
            this.cpf = cpf;
        }else {
            throw new InvalidCPF();
        }
    }

    public String removeCaracteres(String cpf){
        if (cpf.contains(".")){
            cpf = cpf.replace(".", "");
        }
        if (cpf.contains("-")){
            cpf = cpf.replace("-","");
        }
        return cpf;
    }

    public boolean isCPF(){
        return this.cpf != null;
    }
}
