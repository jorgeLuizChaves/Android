package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class BoaViagem extends Activity {

    private EditText login;
    private EditText pwd;

    private static final String USUARIO = "leitor";
    private static final String SENHA = "123";

    private static final String MANTER_CONECTADO = "manter_conectado";
    private CheckBox manterConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (EditText) findViewById(R.id.txtLogin);
        pwd = (EditText) findViewById(R.id.txtPwd);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);

        Button btnSignup = (Button) findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioInformado = login.getText().toString();
                String senhaInformada = pwd.getText().toString();


                if(USUARIO.equals(usuarioInformado) &&
                        SENHA.equals(senhaInformada)) {

                    SharedPreferences preferencias =
                            getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putBoolean(MANTER_CONECTADO,
                            manterConectado.isChecked());
                    editor.commit();
                    // vai para outra activity
                    startActivity(new Intent(BoaViagem.this, DashboardActivity.class));
                } else{
                    // mostra uma mensagem de erro
                    String mensagemErro = getString(R.string.erro_autenticao);
                    Toast toast = Toast.makeText(BoaViagem.this, mensagemErro,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean conectado =
                preferencias.getBoolean(MANTER_CONECTADO, false);
        if(conectado){
            startActivity(new Intent(this, DashboardActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boa_viagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}