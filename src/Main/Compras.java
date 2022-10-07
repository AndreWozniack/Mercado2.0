package Main;

import DataBase.CarrinhoDAO;
import DataBase.RegistroDAO;
import DataBase.ProdutoDAO;
import Sys.*;
import Sys.erros.NotFoundProd;

import java.util.List;
import java.util.Scanner;

public class Compras {
    static Scanner in = new Scanner(System.in);
    private static User user = new User();

    private static CarrinhoDAO carrinho = new CarrinhoDAO();
    private static ProdutoDAO produtoDAO = new ProdutoDAO();
    private static RegistroDAO registroDAO = new RegistroDAO();
    private static List<Produto> produtos = produtoDAO.getData();
    private static Registro registro = new Registro();
    public Compras(User user) {
        Compras.user = user;
    }
    public Compras(){
    }
    public void add(Produto produto){
        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
        produtoCarrinho.setCod_user(user.getCPF());
        System.out.print("Qual a quantia deseja adicionar?: ");
        int quant = Integer.parseInt(in.nextLine());
        produtoCarrinho.setQuantidade(quant);
        produtoCarrinho.setCod(produto.getCod());
        produtoCarrinho.setValor(produto.getValor());
        produtoCarrinho.setEstoque(produto.getEstoque());
        produtoCarrinho.setDescri(produto.getDescri());
        carrinho.insert(produtoCarrinho);
    }
    public void menu() {
        int escolha;
        do {
            System.out.println(" --- COMPRAS ---\n");
            System.out.println("""
                    [1] Listar todos os produtos
                    [2] Buscar produto por nome
                    [3] Buscar produto por código
                    [4] Exibir Carrinho
                    [5] Finalizar compra
                    [0] Voltar ao menu principal
                    """);
            escolha = Integer.parseInt(in.nextLine());
            switch (escolha) {
                case 1 -> {
                    Pagina pagina = new Pagina(produtos, 0, 6);
                    int naveg;
                    do{
                        pagina.paginas().forEach(System.out::println);
                        System.out.println("Deseja adicionar ao carrinho?\n[1] Sim [2] Não");
                        int adicionar = Integer.parseInt(in.nextLine());
                        if (adicionar == 1){
                            System.out.print("Digite o código do produto: ");
                            int cod = Integer.parseInt(in.nextLine());
                            if (produtoDAO.getProduto(cod) == null) {
                                System.out.println("Produto não encontrado!");
                            }else {
                                System.out.println(produtoDAO.getProduto(cod));
                                add(produtoDAO.getProduto(cod));
                            }
                        }
                        pagina.paginas().forEach(System.out::println);
                        System.out.printf("""
                                Pag [%d]
                            [<-] 1 : 2 [->]
                                [0]-Sair
                            """, pagina.getPag());
                        System.out.println(": ");
                        naveg = Integer.parseInt(in.nextLine());
                        switch (naveg) {
                            case 1: pagina.voltar();
                            case 2: pagina.avancar();
                        }
                    }while (naveg != 0);
                }
                case 2 -> {
                    System.out.print("Digite o nome do produto: ");
                    String nome = in.nextLine().toLowerCase();
                    try {
                        System.out.println(produtoDAO.pesquisa(nome));
                        System.out.println("Deseja adicionar ao carrinho?\n[1] Sim [2] Não");
                        int adicionar = Integer.parseInt(in.nextLine());
                        if (adicionar == 1) add(produtoDAO.pesquisa(nome));
                    } catch (NotFoundProd p) {
                        System.out.println("Produto não encontrado!");
                    }
                }
                case 3 -> {
                    System.out.print("Digite o código do produto: ");
                    int cod = Integer.parseInt(in.nextLine());
                    if (produtoDAO.getProduto(cod) == null) {
                        System.out.println("Produto não encontrado!");
                    }else {
                        System.out.println(produtoDAO.getProduto(cod));
                        System.out.println("Deseja adicionar ao carrinho?\n[1] Sim [2] Não");
                        int adicionar = Integer.parseInt(in.nextLine());
                        if (adicionar == 1) add(produtoDAO.getProduto(cod));
                    }
                }
                case 4 -> {
                    Carrinho exCarrinho = new Carrinho(carrinho.carrinho(user),0,3);
                    int naveg;
                    do{
                        exCarrinho.paginas().forEach(System.out::println);
                        System.out.printf("""
                                Pag [%d]
                            [<-] 1 : 2 [->]
                           [3] Editar [0] Sair
                            """, exCarrinho.getPag());
                        System.out.print(": ");
                        naveg = Integer.parseInt(in.nextLine());
                        switch (naveg) {
                            case 1: exCarrinho.voltar();
                            case 2: exCarrinho.avancar();
                            case 3: carrinho.editar();
                        }
                    }while (naveg != 0);
                }
                case 5 -> finalizarCompra(carrinho.carrinho(user));
            }
        } while (escolha != 0);
    }
    public void finalizarCompra(List<ProdutoCarrinho> produtoCarrinhoList){
        double valor = 0;
        for (ProdutoCarrinho p : produtoCarrinhoList) {
            valor += p.getValor() * p.getQuantidade();
        }
        System.out.printf("O valor total da compra foi: R$%.2f\n", valor);
        System.out.println("Deseja finalizar a compra?\n[1] Sim\n[2] Nao");
        int finalizar = Integer.parseInt(in.nextLine());
        if (finalizar == 1) {
            produtoCarrinhoList.forEach(p -> registroDAO.insert(p));
            for (ProdutoCarrinho p: produtoCarrinhoList){
                carrinho.delete(p);
            }
        }
    }
    public void relatorio(){
        double valor = 0;
        StringBuilder produtos = new StringBuilder();
        for (Registro p : registroDAO.getData()) {
            valor += p.getValor() * p.getQuantidade();
            produtos.append(p);
        }
        for (ProdutoCarrinho p: carrinho.carrinho(user)){
            System.out.printf("""
                    User: %s
                    Valor da compra: R$%.2f
                    Produtos:
                    %s
                   
                    """,p.getCod_user(),valor, produtos);
        }
    }
}