package Main;

import DataBase.Conect;
import DataBase.UserDAO;
import Sys.User;
import Sys.VerificadorCPF;
import Sys.erros.InvalidCPF;
import Sys.erros.InvalidPassword;
import Sys.erros.NotFoundUser;

import java.util.*;

public class Main {
    static Scanner in = new Scanner(System.in);
    static Compras compras = new Compras();
    public static void main(String[] args) throws NotFoundUser {
        System.out.println(Conect.statusConection());
        System.out.println(Conect.getConection());
        Conect.ResetConnection();
        System.out.println(Conect.statusConection());
        System.out.println();

        var txt = Arrays.asList("[1] - Menu de Compras\n", "[2] - Trocar usuário\n", "[3] - Sobre\n", "[4] - Relatório\n", "[0] - Sair\n");
        int i;

        //Login
        System.out.printf("Bem Vindo(a) ao Mercado!\nInforme seu CPF e senha para entrar\n\n");

        //Efetuando o Login
        User user = new User();
        inicio(user);
        compras = new Compras(user);
        System.out.println(" Acessando o menu");

        // Menu Principal
        do {
            if (user.isAdmin()) { // Imprimindo as opções
                for (String str : txt) {
                    System.out.printf("%s", str);
                }
            } else {
                for (int j = 0; j < 5; j++) {
                    if (j != 3) {
                        System.out.printf("%s", txt.get(j));
                    }
                }
            }
            i = Integer.parseInt(in.nextLine());
            switch (i) {
                case 1 -> {
                    compras = new Compras(user);
                    compras.menu();
                }
                case 2 -> { //trocar usuário;
                    System.out.println("Troca de usuário\n");
                    inicio(user);
                }
                case 3 -> { //Sobre
                    System.out.println("SOBRE:");
                    System.out.print(
                            """
                            Desenvolvedor: André Fabricio Wozniack
                            Data de lançamento: ??/??/2022
                            Versão: 2.0
                            Contexto: Refatoração do código de lojo de livros,
                            mudando o foco para um mercado, com atualizações
                            e muitas melhorias.
                            """
                    );
                }
                case 4 -> {
                    if (user.isAdmin()) {
                        compras.relatorio();
                    }
                }
            }
        } while (i != 0);
        System.out.println("SAINDO...");
        System.out.println("Obrigado, volte sempre!");
    }

    private static User inicio(User user) throws NotFoundUser {
        try {
            user.login();
            System.out.printf("\nBem Vindo(a) %s!",user.getNome());
            return user;
        } catch (InvalidPassword pass) {
            System.out.println("Senha Incorreta");
            System.out.println("Digite sua senha novamente: ");
            user.login();
            return user;
        } catch (InvalidCPF c) {
            System.out.println("CPF Inválido");
            System.out.println("Digite novamente: ");
            user.login();
            return user;
        } catch (NotFoundUser notFoundUser) {
            System.out.println("""
                    Usuário não encontrado.
                    [1] - Tentar novamente;
                    [2] - Cadastrar novo Usuário;
                    """);
            int cadastro = Integer.parseInt(in.nextLine());
            if (cadastro == 1) {
                user.login();
                return user;
            } else if (cadastro == 2) {
                user.cadastro();
                return user;
            }
        }
        return user;
    }
}