package DataBase;

import Sys.User;
import Sys.erros.InvalidPassword;
import Sys.erros.NotFoundUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO implements DataAcessObject<User> {

    @Override
    public void insert(User t) {
        String sql = "INSERT INTO user(cpf, nome, senha) values(?,?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, t.getCPF());
            pstm.setString(2, t.getNome());
            pstm.setString(3, t.getSenha());
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
    public void delete(User user) {
        String sql = "DELETE FROM user WHERE cpf = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try{
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getCPF());
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
    public void update(User user) {
        String sql = "UPDATE user SET cpf = ?, nome = ?, senha = ? WHERE cpf = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getCPF());
            pstm.setString(2, user.getSenha());
            pstm.setString(3, user.getNome());
            pstm.setString(4, user.getCPF());
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
    public List<User> getData() {
        String sql = "SELECT * FROM user";
        List<User> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            conn = Conect.getConection();
            assert conn != null;
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                User user = new User();
                user.setCPF(rset.getString(1));
                user.setNome(rset.getString(2));
                user.setSenha(rset.getString(3));
                usuarios.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conect.closeDB(rset, pstm, conn);
        }
        return usuarios;
    }

    public User getUser(String cpf, String senha) throws NotFoundUser, InvalidPassword {
        User user = new User(cpf, senha);
        List<User> users = getData();
        if (users.contains(user)){
            for (User u : users){
                if (Objects.equals(u.getCPF(), cpf)) {
                    if (Objects.equals(u.getSenha(), senha)){
                        return u;
                    }else {
                        throw new InvalidPassword();
                    }
                }
            }
        }else {
            throw new NotFoundUser();
        }
        return null;
    }
}
