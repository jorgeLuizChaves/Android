package boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import boaviagem.casadocodigo.com.br.boaviagem.R;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Constantes;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Viagem;

/**
 * Created by Jorge on 2/8/15.
 */
public class ViagemDAO extends DAO<Viagem> {

    private static final String FIND_BY_ID = "SELECT tipo_viagem, destino, data_chegada, " +
            "data_saida, quantidade_pessoas, orcamento " +
            "FROM viagem WHERE _id = ?";

    private DatabaseHelper helper;

    public ViagemDAO(DatabaseHelper helper){
        this.helper = helper;
    }

    @Override
    public boolean save(Viagem viagem) {
        return false;
    }

    @Override
    public Viagem findById(String id) {
        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor cursor = readDB.rawQuery(FIND_BY_ID, new String[]{id});
        cursor.moveToFirst();
        Viagem viagem = new Viagem();
        viagem.setTipoViagem(cursor.getInt(0));
        viagem.setDestino(cursor.getString(1));
        viagem.setDataChegada(cursor.getString(2));
        viagem.setDataSaida(cursor.getString(3));
        viagem.setQuantidadePessoas(cursor.getString(4));
        viagem.setOrcamento(cursor.getString(5));
        cursor.close();
        return viagem;
    }
}