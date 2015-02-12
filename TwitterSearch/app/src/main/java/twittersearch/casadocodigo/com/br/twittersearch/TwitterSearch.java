package twittersearch.casadocodigo.com.br.twittersearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;


public class TwitterSearch extends Activity {

    private ListView tweets;
    private EditText searchText;
    private Button searchTweetsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = (ListView) findViewById(R.id.lista);
        searchText = (EditText) findViewById(R.id.texto);
        searchTweetsButton = (Button) findViewById(R.id.searchTweetsButton);

        configureListeners();
    }

    private void configureListeners() {
        searchTweetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filtro = searchText.getText().toString();
                new TwitterTask().execute(filtro);
            }
        });
    }

    private class TwitterTask extends AsyncTask<String, Void, String[]>{

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(TwitterSearch.this);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                String filtro = params[0];
                if(TextUtils.isEmpty(filtro)){
                    return null;
                }
                String urlTwitter = "http://search.twitter.com/search.json?q=";
                String url = Uri.parse(urlTwitter + filtro).toString();
                String conteudo = HTTPUtils.acessar(url);
                JSONObject jsonObject = new JSONObject(conteudo);
                JSONArray resultados = jsonObject.getJSONArray("results");
                String[] tweets = new String[resultados.length()];

                for (int i = 0; i < resultados.length(); i++) {
                    JSONObject tweet = resultados.getJSONObject(i);
                    String texto = tweet.getString("text");
                    String usuario = tweet.getString("from_user");
                    tweets[i] = usuario + " - " + texto;
                }
                return tweets;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            if(result != null){
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_list_item_1, result);
                tweets.setAdapter(adapter);
            }
            dialog.dismiss();
        }
    }
}
