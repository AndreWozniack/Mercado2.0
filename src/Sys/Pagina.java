package Sys;


import java.util.ArrayList;
import java.util.List;


public class Pagina implements Paginavel<Produto> {
    private List<Produto> produtos;
    private int inicial;
    private int iFinal;
    private int pag = 1;
    public Pagina() {
        this.produtos = new ArrayList<>();
    }

    public Pagina(List<Produto> produtos, int linha_inicial, int linha_final) {
        this.produtos = produtos;
        this.inicial = linha_inicial;
        this.iFinal = linha_final;
    }
    public Pagina(List<Produto> produtos){
        this.produtos = produtos;
    }
    public List<Produto> getProdutos() {
        return produtos;
    }
    public int getInicial() {
        return inicial;
    }
    public int getiFinal() {
        return iFinal;
    }
    public int getPag() {
        return pag;
    }
    public void setInicial(int inicial) {
        this.inicial = inicial;
    }
    public void setiFinal(int iFinal) {
        this.iFinal = iFinal;
    }
    public void addProduto(Produto produto) {
        this.produtos.add(produto);
    }
    public List<Produto> paginas() {
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