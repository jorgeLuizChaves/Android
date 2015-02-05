package boaviagem.casadocodigo.com.br.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import android.widget.AdapterView;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import java.util.Map;
import java.util.TreeMap;

import boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model.Gasto;


public class GastoListActivity extends ListActivity implements OnItemClickListener {


    private String dataAnterior = "";
    private List<Map<String, Object>> listaGastos;


    private static final String DATA = "data";
    private static final String VALOR = "valor";
    private static final String DESCRICAO = "descrição";
    private static final String CATEGORIA = "categoria";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = listaGastos.get(position);
        String descricao = (String) map.get(DESCRICAO);
        String mensagem = "GastoActivity selecionada: " + descricao;
        Toast.makeText(this, mensagem,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] de = new String[]{DATA, DESCRICAO, VALOR, CATEGORIA};
        int[] para = new int[]{R.id.data, R.id.descricao, R.id.valor, R.id.categoria};

        List<Gasto> gastos = getListaGastos();

        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(gastos), R.layout.lista_gasto ,de, para);

        adapter.setViewBinder(new GastoViewBinder());

        setListAdapter(adapter);

        setTitle(R.string.app_name);
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gasto_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.menu.gasto_menu){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            listaGastos.remove(menuInfo.position);
            getListView().invalidateViews();
            dataAnterior = "";
            return Boolean.TRUE;
        }

        return super.onContextItemSelected(item);
    }

    private List<Gasto> getListaGastos() {
        List<Gasto> gastos = new ArrayList<Gasto>();
        Gasto gasto1 = new Gasto();
        gasto1.data = "22/02/2015";
        gasto1.categoria = R.color.categoria_hospedagem;
        gasto1.descricao = "vegas";
        gasto1.valor = "10.00";

        Gasto gasto2 = new Gasto();
        gasto2.data = "22/02/2015";
        gasto2.categoria = R.color.categoria_alimentacao;
        gasto2.descricao = "vegas";
        gasto2.valor = "10.00";

        Gasto gasto3 = new Gasto();
        gasto3.data = "22/02/2015";
        gasto3.categoria = R.color.categoria_outros;
        gasto3.descricao = "vegas";
        gasto3.valor = "10.00";

        Gasto gasto4 = new Gasto();
        gasto4.data = "23/02/2015";
        gasto4.categoria = R.color.categoria_transporte;
        gasto4.descricao = "vegas";
        gasto4.valor = "10.00";

        gastos.add(gasto1);
        gastos.add(gasto2);
        gastos.add(gasto3);
        gastos.add(gasto4);
        return gastos;
    }

    private List<Map<String, Object>> listarGastos(List<Gasto> gastos){
        listaGastos = new ArrayList<Map<String, Object>>();
        //String[] de = new String[]{"data", "descrição", "valor", "categoria"};
        for(Gasto g: gastos){
            Map<String, Object> gastoMap = new TreeMap<String, Object>();
            gastoMap.put(DESCRICAO, g.descricao);
            gastoMap.put(DATA, g.data);
            gastoMap.put(CATEGORIA, g.categoria);
            gastoMap.put(VALOR, g.valor);

            listaGastos.add(gastoMap);

        }
        return listaGastos;
    }

    private class GastoViewBinder implements ViewBinder {

        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {

            if (view.getId() == R.id.data) {
                if (!dataAnterior.equals(data)) {
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if (view.getId() == R.id.categoria) {
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }
}