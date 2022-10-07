package Sys;

import java.util.Objects;

public class Produto {
    private int cod;
    private double valor;
    private int estoque;
    private String descri;

    public Produto(double valor, String descri) {
        this.valor = valor;
        this.descri = descri;
    }
    public Produto(){}
    public void setCod(int cod) {
        this.cod = cod;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public void setDescri(String descri) {
        this.descri = descri;
    }
    public int getCod() {
        return cod;
    }
    public double getValor() {
        return valor;
    }
    public int getEstoque() {
        return estoque;
    }
    public String getDescri() {
        return descri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return cod == produto.cod && Double.compare(produto.valor, valor) == 0 && Objects.equals(descri, produto.descri);
    }
    @Override
    public int hashCode() {
        return Objects.hash(cod, valor, descri);
    }
    @Override
    public String toString() {
        return """
               [%d]: %s
               Preço: R$%.2f""".formatted(cod,descri, valor);
    }
}
