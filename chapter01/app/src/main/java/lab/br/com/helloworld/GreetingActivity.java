package lab.br.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import lab.br.com.helloworld.R;

/**
 * Created by Jorge on 1/3/15.
 */
public class GreetingActivity extends Activity {

    public static final String EXTRA_NOME_USUARIO = "helloandroid.EXTRA_NOME_USUARIO";
    public static final String CATEGORIA_SAUDACAO = "helloandroid.CATEGORIA_SAUDACAO";
    public static final String ACAO_EXIBIR_SAUDACAO = "helloandroid.ACAO_EXIBIR_SAUDACAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saudacao);

        TextView saudacaoTextView = (TextView) findViewById(R.id.saudacao_text);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_NOME_USUARIO)){
            String saudacao = getResources().getString(R.string.greeting_message);

            saudacaoTextView.setText(saudacao + " " +
                    intent.getStringExtra(EXTRA_NOME_USUARIO));
        }else{
            saudacaoTextView.setText("O nome do usuário não foi informado");
        }
    }

    public static Intent buildIntent(String name){
        Intent intent = new Intent(ACAO_EXIBIR_SAUDACAO);
        intent.addCategory(CATEGORIA_SAUDACAO);
        intent.putExtra(EXTRA_NOME_USUARIO, name);
        return intent;
    }
}