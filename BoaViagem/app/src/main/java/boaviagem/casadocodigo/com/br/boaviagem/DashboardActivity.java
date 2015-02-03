package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Jorge on 1/4/15.
 */
public class DashboardActivity extends Activity {

    private static List<Integer> buttons = Arrays.asList(R.id.novo_gasto,
            R.id.configuracoes,
            R.id.minhas_viagens,
            R.id.nova_viagem);

    private static final Map<Integer, Class<? extends Activity>> ACTIVITIES;

    static{
        ACTIVITIES = new TreeMap<Integer, Class<? extends Activity>>();

        ACTIVITIES.put(R.id.nova_viagem, NovaViagemActivity.class);
        ACTIVITIES.put(R.id.minhas_viagens, GastoActivity.class);
        ACTIVITIES.put(R.id.configuracoes, GastoActivity.class);
        ACTIVITIES.put(R.id.novo_gasto, GastoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        configureActionButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashbord_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return Boolean.TRUE;
    }

    private void configureActionButtons() {
        for(int buttonId: buttons){
            final TextView button = (TextView) findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    Class<? extends Activity> activityClass = ACTIVITIES.get(id);
                    startActivity(new Intent(DashboardActivity.this, activityClass));
                }
            });
        }
    }
}