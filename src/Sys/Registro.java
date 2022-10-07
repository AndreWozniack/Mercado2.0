package Sys;

import DataBase.RegistroDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Registro extends ProdutoCarrinho{
    private String dataCompra;
    private Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setDate(Date date) {
        this.date = date;
        this.dataCompra = format.format(date);
    }



}
