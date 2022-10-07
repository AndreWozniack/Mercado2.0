package Sys;

public class ProdutoCarrinho extends Produto {
    private String cod_user;
    private int quantidade;
    public ProdutoCarrinho(){}
    public String getCod_user() {
        return cod_user;
    }
    public void setCod_user(String cod_user) {
        this.cod_user = cod_user;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return """
               [%d]: %s (Quantidade: %d)
               Preço: R$%.2f""".formatted(getCod(),getDescri(),quantidade ,getValor());
    }
}
