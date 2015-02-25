package twittersearch.casadocodigo.com.br.twittersearch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jorge on 2/19/15.
 */
public class NotificacaoService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ScheduledThreadPoolExecutor pool =
                new ScheduledThreadPoolExecutor(1);
        long delayInicial = 0;
        long periodo = 10;
        TimeUnit unit = TimeUnit.MINUTES;
        pool.scheduleAtFixedRate(new NotificacaoTask(),delayInicial, periodo,unit);
        return START_STICKY;
    }

    private class NotificacaoTask implements Runnable {

        private String baseUrl = "https://api.twitter.com/1.1/search/tweets.json";

        @Override
        public void run() {
            if (!estaConectado()) {
                return;
            }
            try {
                HTTPUtils httpUtils = new HTTPUtils();
                Authenticated authenticated = httpUtils.authenticateApp();

                String json = httpUtils.getTwitterStream(authenticated, "android");
                JSONObject jsonObject = new JSONObject(json);

                JSONArray resultados = jsonObject.getJSONArray("statuses");
                for (int i = 0; i < resultados.length(); i++) {
                    JSONObject tweet = resultados.getJSONObject(i);
                    String texto = tweet.getString("text");
                    String usuario = tweet.getJSONObject("user").getString("screen_name");
                    criarNotificacao(usuario, texto, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void criarNotificacao(String usuario, String texto, int id) {

            int icone = R.drawable.ic_launcher;
            String aviso = getString(R.string.aviso);
            long data = System.currentTimeMillis();
            String titulo = usuario + " " + getString(R.string.titulo);
            Context context = getApplicationContext();
            Intent intent = new Intent(context, TweetActivity.class);
            intent.putExtra(TweetActivity.USUARIO, usuario.toString());
            intent.putExtra(TweetActivity.TEXTO, texto.toString());
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, id, intent,
                            Intent.FLAG_ACTIVITY_NEW_TASK);


            Notification notification = new Notification(icone, aviso, data);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.setLatestEventInfo(context, titulo,
                    texto, pendingIntent);

            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(ns);
            notificationManager.notify(id, notification);
        }

        private boolean estaConectado() {
            ConnectivityManager manager =
                    (ConnectivityManager) getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();

            return info.isConnected();
        }
    }
}
