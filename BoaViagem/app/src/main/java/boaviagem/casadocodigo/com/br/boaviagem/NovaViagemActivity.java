package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class NovaViagemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Button btnNovaViagem = (Button) findViewById(R.id.btnNovaViagem);

        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
}
