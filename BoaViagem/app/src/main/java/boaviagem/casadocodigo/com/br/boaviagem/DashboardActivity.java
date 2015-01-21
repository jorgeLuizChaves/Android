package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jorge on 1/4/15.
 */
public class DashboardActivity extends Activity {

    private static List<Integer> buttons = Arrays.asList(R.id.novo_gasto,
            R.id.configuracoes,
            R.id.minhas_viagens,
            R.id.nova_viagem);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        configureActionButtons();
    }

    private void configureActionButtons() {
        for(int buttonId: buttons){
            final TextView button = (TextView) findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String opcao = String.format("Opção: %s", button.getText().toString());
                    Toast.makeText(DashboardActivity.this, opcao, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}