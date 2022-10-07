package Sys;

import java.util.List;

public interface Paginavel<T> {

    List<T> paginas();
    void avancar();
    void voltar();
}
