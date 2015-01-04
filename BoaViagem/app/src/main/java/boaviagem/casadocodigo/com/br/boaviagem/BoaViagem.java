package boaviagem.casadocodigo.com.br.boaviagem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BoaViagem extends ActionBarActivity {

    private EditText login;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (EditText) findViewById(R.id.txtLogin);
        pwd = (EditText) findViewById(R.id.txtPwd);

        Button btnSignup = (Button) findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioInformado = login.getText().toString();
                String senhaInformada = login.getText().toString();
                if("leitor".equals(usuarioInformado) &&
                        "123".equals(senhaInformada)) {
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
