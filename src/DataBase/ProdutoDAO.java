package DataBase;

import Sys.Produto;
import Sys.User;
import Sys.erros.NotFoundProd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProdutoDAO implements DataAcessObject<Produto> {
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rset = null;

    @Override
    public void insert(Produto produto) {
        String sql = "INSERT INTO produto(cod, valor, estoque, descri) values(?,?,?,?)";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, produto.getCod());
            pstm.setDouble(2, produto.getValor());
            pstm.setInt(3, produto.getEstoque());
            pstm.setString(4, produto.getDescri());
            pstm.execute();
            System.out.println("Usuário Cadastrado!");
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
    public void delete(Produto produto) {
        String sql = "DELETE FROM produto WHERE cod = ?";
        try{
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, produto.getCod());
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
    public void update(Produto produto) {
        String sql = "UPDATE produto SET cod = ?, valor = ?, estoque = ?, descri = ? WHERE cod = ?";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, produto.getCod());
            pstm.setDouble(2, produto.getValor());
            pstm.setInt(3, produto.getEstoque());
            pstm.setString(4,produto.getDescri());
            pstm.setInt(5,produto.getCod());
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
    public List<Produto> getData() {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Produto produto = new Produto();
                produto.setCod(rset.getInt(1));
                produto.setValor(rset.getDouble(2));
                produto.setEstoque(rset.getInt(3));
                produto.setDescri(rset.getString(4));
                produtos.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conect.closeDB(rset, pstm, conn);
        }
        return produtos;
    }

    public Produto getProduto(int cod){
        Produto produto = new Produto();
        String sql = "SELECT * FROM produto WHERE cod = ?";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, cod);
            rset = pstm.executeQuery();
            while (rset.next()) {
                produto.setCod(rset.getInt(1));
                produto.setValor(rset.getDouble(2));
                produto.setEstoque(rset.getInt(3));
                produto.setDescri(rset.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conect.closeDB(rset, pstm, conn);
        }
        return produto;
    }
    public Produto pesquisa(String nome) throws NotFoundProd {
        Produto produto;
        var produtos = getData();
        for (Produto p: produtos){
            if (Objects.equals(nome, p.getDescri())){
                produto = p;
                return produto;
            }else {
                throw new NotFoundProd();
            }
        }
        return null;
    }
}