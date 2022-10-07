package DataBase;

import java.sql.*;

public class Conect{
    public static String status = "Not connected";
    public Conect(){}
    public static java.sql.Connection getConection(){
        Connection connection = null;
        try{
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            String serverName = "localhost";
            String mydatabase = "mercado";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null){
                status = ("STATUS ----> Conectado com sucesso");
            }else {
                status = ("STATUS ----> Nao foi possivel conectar");
            }
            return connection;
        }catch (ClassNotFoundException e){
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
        }
    }
    public static String statusConection() {
        return status;
    }
    public static boolean closeConnection() {
        try {
            Conect.getConection().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static java.sql.Connection ResetConnection(){
        closeConnection();
        return Conect.getConection();
    }
    static void closeDB(ResultSet rset, PreparedStatement pstm, Connection conn) {
        try {
            if (rset != null) {
                rset.close();
            }
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