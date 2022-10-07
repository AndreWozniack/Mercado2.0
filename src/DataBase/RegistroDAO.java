package DataBase;
import Sys.ProdutoCarrinho;
import Sys.Registro;
import Sys.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RegistroDAO implements DataAcessObject<Registro>{
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rset = null;
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void insert(ProdutoCarrinho produtoCarrinho) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        String currentDateTime = format.format(date);
        String sql = "INSERT INTO registro(cpf, valor, datahora, cod_produto, quantidade) VALUES (?,?,?,?,?)";
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, produtoCarrinho.getCod_user());
            pstm.setDouble(2, produtoCarrinho.getValor());
            pstm.setString(3,currentDateTime);
            pstm.setInt(4, produtoCarrinho.getCod());
            pstm.setInt(5, produtoCarrinho.getQuantidade());
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
    public void delete(ProdutoCarrinho produtoCarrinho) {
        String sql = "DELETE FROM registro WHERE cpf = ?";
        try{
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, produtoCarrinho.getCod_user());
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
    public void insert(Registro registro) {

    }
    @Override
    public void delete(Registro registro) {

    }
    @Override
    public void update(Registro registro) {

    }
    @Override
    public List<Registro> getData() {
        String sql = "SELECT * FROM registro";
        List<Registro> produtos = new ArrayList<>();
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Registro produto = new Registro();
                produto.setCod_user(rset.getString(1));
                produto.setValor(rset.getDouble(2));
                produto.setDate(rset.getDate(3));
                produto.setCod(rset.getInt(4));
                produto.setQuantidade(rset.getInt(5));
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

}
