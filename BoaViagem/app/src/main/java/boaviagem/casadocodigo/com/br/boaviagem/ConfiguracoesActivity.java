package boaviagem.casadocodigo.com.br.boaviagem;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import boaviagem.casadocodigo.com.br.boaviagem.R;

/**
 * Created by Jorge on 2/7/15.
 */
public class ConfiguracoesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
