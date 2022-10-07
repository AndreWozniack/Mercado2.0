package DataBase;

import java.util.List;

public interface DataAcessObject<T> {
    void insert(T t);
    void delete(T t);
    void update(T t);
    List<T> getData();
}