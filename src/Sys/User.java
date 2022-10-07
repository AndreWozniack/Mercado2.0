package Sys;

import DataBase.UserDAO;
import Sys.erros.InvalidCPF;
import Sys.erros.InvalidPassword;
import Sys.erros.NotFoundUser;

import java.util.Objects;
import java.util.Scanner;

public class User {
    static Scanner in = new Scanner(System.in);
    static UserDAO userDAO = new UserDAO();
    static VerificadorCPF verificadorCPF = null;
    private String CPF;
    private String senha;
    private String nome;


    public User(){}

    public User(String CPF, String senha) {
        this.CPF = CPF;
        this.senha = senha;
    }

    public User(String CPF, String senha, String nome){
        this.CPF = CPF;
        this.senha = senha;
        this.nome = nome;
    }
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public boolean isAdmin(){
        return Objects.equals(CPF, "admin");
    }
    @Override
    public String toString() {
        return "CPF='" + CPF + '\'' +
                ", nome='" + nome;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(CPF, user.getCPF()) && Objects.equals(senha, user.getSenha());
    }
    @Override
    public int hashCode() {
        return Objects.hash(CPF, senha, nome);
    }
    public void login() throws InvalidCPF, InvalidPassword, NotFoundUser {
        System.out.print("CPF: ");
        String cpf = in.nextLine();
        verificadorCPF = new VerificadorCPF(cpf);
        if (verificadorCPF.isCPF()){
            try{
                System.out.print("Senha: ");
                String senha = in.nextLine();
                User usuario = userDAO.getUser(cpf, senha);
                setCPF(usuario.getCPF());
                setNome(usuario.getNome());
                setSenha(usuario.getSenha());
            }catch (InvalidPassword pass){
                System.out.println("Senha incorreta, digite novamente");
                System.out.print("Senha: ");
                String senha = in.nextLine();
                User usuario = userDAO.getUser(cpf, senha);
                setCPF(usuario.getCPF());
                setNome(usuario.getNome());
                setSenha(usuario.getSenha());
            }
        }
    }
    public void cadastro() throws NotFoundUser {
        System.out.println("Digite as informações para cadastrar:");
        System.out.print("Nome: ");
        String nome = in.nextLine();
        System.out.print("CPF: ");
        String cpf = in.nextLine();
        verificadorCPF = new VerificadorCPF(cpf);
        if (verificadorCPF.isCPF()){
            System.out.print("Senha: ");
            String senha = in.nextLine();
            this.CPF = cpf;
            this.senha = senha;
            this.nome = nome;
            userDAO.insert(this);
        }
    }
}
