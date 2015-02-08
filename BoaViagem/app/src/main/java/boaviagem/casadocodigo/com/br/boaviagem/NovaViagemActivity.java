package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.db.DatabaseHelper;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.db.ViagemDAO;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Constantes;
import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Viagem;


public class NovaViagemActivity extends Activity {

    private DatabaseHelper helper;
    private EditText destino, quantidadePessoas, orcamento, dataChegada, dataSaida;
    private RadioGroup radioGroup;
    private SQLiteDatabase db;
    private int dia, mes, ano;
    private String id;
    private ViagemDAO viagemDAO;

    private static final String DD_MM_YYYY = "%d/%d/%d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        bindFields();
        setData();
        Button btnNovaViagem = (Button) findViewById(R.id.btnNovaViagem);
        configureListener(btnNovaViagem);

        id = getIntent().getStringExtra(Constantes.VIAGEM_ID);
        helper = new DatabaseHelper(this);
        viagemDAO = new ViagemDAO(helper);
        if(id != null){
            prepararEdicao();
        }
    }

    private void prepararEdicao() {
        Viagem viagem = viagemDAO.findById(id);

        if(viagem.getTipoViagem() == Constantes.VIAGEM_LAZER){
            radioGroup.check(R.id.lazer);
        } else {
            radioGroup.check(R.id.negocios);
        }
        destino.setText(viagem.getDestino());
        dataChegada.setText(viagem.getDataChegada());
        dataSaida.setText(viagem.getDataSaida());
        quantidadePessoas.setText(viagem.getQuantidadePessoas());
        orcamento.setText(viagem.getOrcamento());
    }

    private void setData() {
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataChegada.setText(String.format(DD_MM_YYYY, dia, mes, ano));
        dataSaida.setText(String.format(DD_MM_YYYY, dia, mes, ano));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.viagem_menu, menu);
        return Boolean.TRUE;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_gasto:
                startActivity(new Intent(this, GastoActivity.class));
                return Boolean.TRUE;
            case R.id.remover:
                //remover viagem do banco de dados
                return Boolean.TRUE;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    private void bindFields() {
        dataChegada = (EditText) findViewById(R.id.data_chegada);
        dataSaida = (EditText) findViewById(R.id.data_retorno);
        destino = (EditText) findViewById(R.id.destino);
        quantidadePessoas = (EditText) findViewById(R.id.quantidade_pessoas);
        orcamento = (EditText) findViewById(R.id.orcamento);
        radioGroup = (RadioGroup) findViewById(R.id.tipoViagem);
    }

    private void configureListener(Button btnNovaViagem) {
        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("destino", destino.getText().toString());
                values.put("data_chegada", dataChegada.getText().toString());
                values.put("data_saida", dataSaida.getText().toString());
                values.put("orcamento", orcamento.getText().toString());
                values.put("quantidade_pessoas",
                        quantidadePessoas.getText().toString());
                int tipo = radioGroup.getCheckedRadioButtonId();

                if(tipo == R.id.lazer) {
                    values.put("tipo_viagem", Constantes.VIAGEM_LAZER);
                } else {
                    values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);
                }
                salvarViagem(v, values);
            }
        });
    }

    public void salvarViagem(View view, ContentValues values) {
        // prepara o ContentValues
        long resultado = 0;
        if(id == null) {
            resultado = db.insert("viagem", null, values);
            if (resultado != -1) {
                Toast.makeText(this, getString(R.string.registro_salvo),
                        Toast.LENGTH_SHORT).show();
                this.onBackPressed();
            } else {
                Toast.makeText(this, getString(R.string.erro_salvar),
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, getString(R.string.editado),
                    Toast.LENGTH_SHORT).show();
            resultado = db.update("viagem", values, "_id = ?",new String[]{ id });
            this.onBackPressed();
        }
    }
}
