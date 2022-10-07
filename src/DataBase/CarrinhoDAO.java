package DataBase;

import Sys.Produto;
import Sys.ProdutoCarrinho;
import Sys.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CarrinhoDAO implements DataAcessObject<ProdutoCarrinho> {
    static Scanner in = new Scanner(System.in);
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rset = null;
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoCarrinho completaProd(ProdutoCarrinho produtoCarrinho){
        for (Produto produto : produtoDAO.getData()){
            if (Objects.equals(produto.getCod(), produtoCarrinho.getCod())){
                produtoCarrinho.setValor(produto.getValor());
                produtoCarrinho.setEstoque(produto.getEstoque());
                produtoCarrinho.setDescri(produto.getDescri());
            }
        }
        return produtoCarrinho;
    }
    @Override
    public void insert(ProdutoCarrinho produto) {
        String sql = "INSERT INTO carrinho(cod_user, cod_produto, quantidade) VALUES (?,?,?)";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, produto.getCod_user());
            pstm.setInt(2, produto.getCod());
            pstm.setInt(3, produto.getQuantidade());
            pstm.execute();
            System.out.println("Produto dicionado ao crrinho!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delete(ProdutoCarrinho produto) {
        String sql = "DELETE FROM carrinho WHERE cod_user = ?";
        try{
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, produto.getCod_user());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void update(ProdutoCarrinho produto) {
        String sql = "UPDATE carrinho SET cod_user = ?, cod_produto = ?, quantidade = ? WHERE cod_produto = ?";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,produto.getCod_user());
            pstm.setInt(2, produto.getCod());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setInt(4,produto.getCod());
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public List<ProdutoCarrinho> getData() {
        String sql = "SELECT * FROM carrinho";
        List<ProdutoCarrinho> produtos = new ArrayList<>();
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                ProdutoCarrinho produto = new ProdutoCarrinho();
                produto.setCod_user(rset.getString(1));
                produto.setCod(rset.getInt(2));
                produto.setQuantidade(rset.getInt(3));
                produto.setValor(produtoDAO.getProduto(produto.getCod()).getValor());
                produto.setEstoque(produtoDAO.getProduto(produto.getCod()).getEstoque());
                produto.setDescri(produtoDAO.getProduto(produto.getCod()).getDescri());
                produtos.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conect.closeDB(rset, pstm, conn);
        }
        return produtos;
    }
    public List<ProdutoCarrinho> carrinho(User user){
        var produtos = getData().stream()
                .filter(a -> Objects.equals(a.getCod_user(), user.getCPF()))
                .toList();
        produtos.forEach(this::completaProd);
        return produtos;
    }
    public ProdutoCarrinho pesquisa(int cod){
        ProdutoCarrinho produtoCarrinho = null;
        var produtos = getData();
        for (ProdutoCarrinho p : produtos){
            if (Objects.equals(p.getCod(), cod)){
                produtoCarrinho = p;
            }
        }
        return produtoCarrinho;
    }
    public void editar(){
        System.out.println("""
                Escolha a opção desejada:
                [1] Remover item
                [2] Alterar quantidade
                [0] Sair""");
        int editar = Integer.parseInt(in.nextLine());
        do {
            switch (editar){
                case 1 -> {
                    System.out.println("Digite o código do produto que deseja remover:");
                    int cod= Integer.parseInt(in.nextLine());
                    ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
                    produtoCarrinho.setCod(cod);
                    delete(produtoCarrinho);
                }
                case 2 -> {
                    System.out.println("Digite o código do produto que deseja editar:");
                    int cod= Integer.parseInt(in.nextLine());
                    var produtoCarrinho = pesquisa(cod);
                    System.out.println("Digite a quantidade:");
                    int quant = Integer.parseInt(in.nextLine());
                    produtoCarrinho.setQuantidade(quant);
                    update(produtoCarrinho);
                }
            }
            System.out.println("Deseja editar novamente?");
            System.out.println("""
                [1] Continuar
                [0] Sair""");
            editar = Integer.parseInt(in.nextLine());
        }while (editar != 0);
    }
}