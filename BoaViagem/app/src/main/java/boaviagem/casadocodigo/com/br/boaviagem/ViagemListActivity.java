package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.SimpleAdapter.ViewBinder;

import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.db.DatabaseHelper;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Constantes;


public class ViagemListActivity extends ListActivity  implements OnItemClickListener, OnClickListener, ViewBinder {

    private static final  String DD_MM_YYYY = "dd/MM/yyyy" ;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;
    private int viagemSelecionada;
    private List<Map<String, Object>> viagens;
    private DatabaseHelper helper;
    private SimpleDateFormat dateFormat;
    private Double valorLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(this);
        dateFormat = new SimpleDateFormat(DD_MM_YYYY);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valor = sharedPreferences.getString("valor_limite", "-1");
        valorLimite = Double.valueOf(valor);

        String[] de = {"imagem", "destino", "data", "total","barraProgresso"};
        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor, R.id.barraProgresso};

        SimpleAdapter adapter =
                new SimpleAdapter(this, listarViagens2(),
                        R.layout.lista_viagem, de, para);
        setListAdapter(adapter);
        adapter.setViewBinder(this);
        getListView().setOnItemClickListener(this);
        this.alertDialog = criaAlertDialog();
        this.dialogConfirmacao = criaDialogConfirmacao();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.viagemSelecionada = position;
        Map<String, Object> map = viagens.get(position);
        alertDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                startActivity(new Intent(this, ViagemListActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, GastoActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GastoListActivity.class));
                break;
            case 3:
                dialogConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                viagens.remove(viagemSelecionada);
                getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;
        }
    }

    private List<Map<String, Object>> listarViagens3(){
        SQLiteDatabase db =  helper.getReadableDatabase();
        String tabela = "viagem";
        String[] colunas = new String[]{"_id", "tipo_viagem", "destino",
                "data_chegada","data_saida",
                "orcamento"};
        String selecao = null;
        String[] selecaoArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = db.query(tabela, colunas, selecao, selecaoArgs,
                groupBy, having, orderBy);
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("viagem");
        viagens = new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        iterateTravels(db, cursor, viagens);

        return viagens;
    }

    private List<Map<String, Object>> listarViagens2(){
        SQLiteDatabase db =  helper.getReadableDatabase();
        String tabela = "viagem";
        String[] colunas = new String[]{"_id", "tipo_viagem", "destino",
                "data_chegada","data_saida",
                "orcamento"};
        String selecao = null;
        String[] selecaoArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = db.query(tabela, colunas, selecao, selecaoArgs,
                groupBy, having, orderBy);
        viagens = new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        iterateTravels(db, cursor, viagens);

        return viagens;
    }

    private List<Map<String, Object>> listarViagens() {

        SQLiteDatabase db =  helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, data_chegada, data_saida, orcamento FROM viagem", null);
        cursor.moveToFirst();
        viagens = new ArrayList<Map<String,Object>>();

        iterateTravels(db, cursor, viagens);

        return viagens;
    }

    private void iterateTravels(SQLiteDatabase db, Cursor cursor, List<Map<String, Object>> viagens) {
        for (int i = 0; i < cursor.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            String id = cursor.getString(0);
            int tipoViagem = cursor.getInt(1);
            String destino = cursor.getString(2);
            long dataChegada = cursor.getLong(3);
            long dataSaida = cursor.getLong(4);
            double orcamento = cursor.getDouble(5);
            item.put("id", id);
            if (tipoViagem == Constantes.VIAGEM_LAZER) {
                item.put("imagem", R.drawable.lazer);
            } else {
                item.put("imagem", R.drawable.negocios);
            }
            item.put("destino", destino);
            Date dataChegadaDate = new Date(dataChegada);
            Date dataSaidaDate = new Date(dataSaida);
            String periodo = dateFormat.format(dataChegadaDate) + " a "
                    + dateFormat.format(dataSaidaDate);
            item.put("data", periodo);
            double totalGasto = calcularTotalGasto(db, id);
            item.put("total", "Gasto total R$ " + totalGasto);
            double alerta = orcamento * valorLimite / 100;
            Double [] valores =
                    new Double[] { orcamento, alerta, totalGasto };
            item.put("barraProgresso", valores);
            viagens.add(item);
            cursor.moveToNext();
        }
        cursor.close();
    }

    private double calcularTotalGasto(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery(
                "SELECT SUM(valor) FROM gasto WHERE viagem_id = ?",
                new String[]{ id });
        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;
    }


    private AlertDialog criaAlertDialog() {
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
                getString(R.string.remover) };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, this);
        return builder.create();
    }

    private AlertDialog criaDialogConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_viagem);
        builder.setPositiveButton(getString(R.string.sim), this);
        builder.setNegativeButton(getString(R.string.nao), this);
        return builder.create();
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        if (view.getId() == R.id.barraProgresso) {
            Double valores[] = (Double[]) data;
            ProgressBar progressBar = (ProgressBar) view;
            progressBar.setMax(valores[0].intValue());
            progressBar.setSecondaryProgress(valores[1].intValue());
            progressBar.setProgress(valores[2].intValue());
            return true;
        }
        return false;
    }
}
