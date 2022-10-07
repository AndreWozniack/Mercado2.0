package Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Carrinho implements Paginavel<ProdutoCarrinho>{
    static Scanner in = new Scanner(System.in);
    private List<ProdutoCarrinho> produtos;
    private int inicial;
    private int iFinal;
    private int pag = 1;


    public Carrinho() {
        this.produtos = new ArrayList<>();
    }
    public Carrinho(List<ProdutoCarrinho> produtos, int linha_inicial, int linha_final) {
        this.produtos = produtos;
        this.inicial = linha_inicial;
        this.iFinal = linha_final;
    }
    public Carrinho(List<ProdutoCarrinho> produtos){
        this.produtos = produtos;
    }
    public List<ProdutoCarrinho> getProdutos() {
        return produtos;
    }
    public int getPag() {
        return pag;
    }
    public List<ProdutoCarrinho> paginas() {
        if (iFinal <= produtos.size()) {
            return produtos.subList(inicial, iFinal);
        }
        else {
            return produtos.subList(inicial, produtos.size());
        }
    }
    public void avancar() {
        int quant = iFinal - inicial;
        inicial = iFinal;
        if (iFinal + quant > produtos.size()){
            iFinal = produtos.size();
        }else {
            iFinal += quant;
            pag++;
        }
    }
    public void voltar() {
        int quant = iFinal - inicial;
        iFinal = inicial;
        if (inicial - quant < 0){
            inicial = 0;
            pag = 1;
        }else {
            inicial -= quant;
            pag--;
        }
    }
}