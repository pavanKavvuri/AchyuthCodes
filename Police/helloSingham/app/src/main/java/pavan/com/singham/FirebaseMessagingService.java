package pavan.com.singham;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;

import static android.support.v4.app.NotificationCompat.BigTextStyle;
import static android.support.v4.app.NotificationCompat.Builder;

/**
 * Created by pavan on 5/8/16.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {

    public final static String EXTRA_MESSAGE = "pavan.com.singham.MESSAGE";


    int p;

    String[] array = new String[10];

   // ArrayList<String> Notif_List;

    Context mContext;
    ArrayHelper pavan;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        pavan = new ArrayHelper(this);

       pavan.getMessage(remoteMessage.getData().get("message"));

      /*  SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString("Notif_Array" + "_" + i, array[i]);
       editor.commit();
*/

        showNotification(remoteMessage.getData().get("message"));


    }






    private void showNotification(String message) {

        try {



            //pavan.getMessage(message);

           // pavan.saveArray("Notif_Array", String.valueOf(message));

   /*         if(p==0){
                Notif_List = new ArrayList<String>();
            }


        Notif_List.add(message);
            p= Notif_List.size();



        pavan.saveArray("Notif_Array",Notif_List);


*/

            Intent i = new Intent(this, Notify.class);
          //  i.putExtra(EXTRA_MESSAGE,message);


            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            Builder builder = new Builder(this)
                    .setAutoCancel(false)
                    .setContentTitle("Notification from Control Room")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.third_i)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)

                    .setStyle(new BigTextStyle().bigText(message));

        //  Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //  builder.setSound(alarmSound);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());

        }catch (Exception e){e.printStackTrace();}

        /*.setStyle(new BigTextStyle()
                            .bigText(message));*/
    }

}