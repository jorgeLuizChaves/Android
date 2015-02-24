package twittersearch.casadocodigo.com.br.twittersearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jorge on 2/20/15.
 */
public class StartupReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context, NotificacaoService.class);
        context.startService(it);
    }
}
