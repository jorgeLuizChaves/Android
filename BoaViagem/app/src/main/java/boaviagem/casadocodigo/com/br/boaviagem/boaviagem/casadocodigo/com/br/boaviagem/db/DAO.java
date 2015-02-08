package boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jorge on 2/8/15.
 */
public abstract class DAO<T> {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public abstract boolean save(T type);

    public abstract T findById(String id);
}
