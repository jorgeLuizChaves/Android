package lab.br.com.helloworld;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HelloWorld extends ActionBarActivity {

    private EditText txtName;
    private Button btnGreeting;
    private TextView txtGreeting;

    private static final String WHITE_SPACE = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        this.txtName = (EditText) findViewById(R.id.txt_name);
        this.btnGreeting = (Button) findViewById(R.id.btn_greeting);
        this.txtGreeting = (TextView) findViewById(R.id.lbl_greeting);

        this.btnGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = HelloWorld.this.txtName.getText();
                String greeting = getResources().getString(R.string.greeting);
                HelloWorld.this.txtGreeting.setText(greeting + HelloWorld.WHITE_SPACE + text);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hello_world, menu);
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
