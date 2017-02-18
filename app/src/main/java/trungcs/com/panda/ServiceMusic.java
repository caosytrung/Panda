package trungcs.com.panda;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

import trungcs.com.panda.manager.ManagerMedidaPlayer;
import trungcs.com.panda.models.AudioPlayer;
import trungcs.com.panda.models.ParseLinkListen;

/**
 * Created by caotr on 20/09/2016.
 */
public class ServiceMusic extends Service  implements MediaPlayer.OnCompletionListener{

    private static final String POP = "PLAY_OR_PAUSE";
    private static final String PLAY_PAUSE = "PLAY_PAUSE";
    private static final String PRE_MUSIC = "PRE_MUSIC";
    private static final String NEXT_MUSIC = "NEXT_MUSIC";
    private static final String SENT_NAME_ARTIS = "SENT_NAME_ARTIS";
    private static final String SENT_NAME_BH = "SENT_NAME_BH";
    private static final String POSITION = "POSITION";
    public static final String ACTION_1 = "ACTION1";
    public static final String KEY_SENT_LIST = "SENT_LIST";
    private static final String KEY = "KEY";
    private static final String SENT = "SENT";
    private static final String RESULT1 = "RESULT1";
    private static final String CHECK_ONLINE ="CHECK_ONLINE" ;
    private static final String TURN_ON_FRGT ="TURN_ON_FRGT" ;
    private static final String ACTION_2 ="ACTION_2";
    private static final String SENT_CUR_POS = "SENT_CUR_POS";
    private static final String SENT_DURATION= "SENT_DURATION";

    private ManagerMedidaPlayer mManagerMedidaPlayer;
    private ArrayList<AudioPlayer> mAudioPlayers;
    private int curPosition;
    private boolean isOnline;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mManagerMedidaPlayer = new ManagerMedidaPlayer(this);
        mAudioPlayers = new ArrayList<>();
        curPosition= 0;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intenBroadCast = new Intent();
        String key = intent.getStringExtra(KEY);
        switch (key){
            case SENT:
                mAudioPlayers = intent.getParcelableArrayListExtra(KEY_SENT_LIST);
                curPosition = intent.getIntExtra(POSITION, 0);
                isOnline = intent.getBooleanExtra(CHECK_ONLINE,false);
                if (isOnline){
                    listeningOnline(mAudioPlayers.get(curPosition).getFilePath());
                }
                else {
                    listteningOffline(mAudioPlayers.get(curPosition).getFilePath());
                }
                intenBroadCast.setAction(ACTION_1);
                intenBroadCast.putExtra(SENT_NAME_BH, mAudioPlayers.get(curPosition).getNameSong());
                intenBroadCast.putExtra(SENT_NAME_ARTIS,mAudioPlayers.get(curPosition).getComposer());
                sendBroadcast(intenBroadCast);

                break;
            case NEXT_MUSIC:
                if(curPosition == mAudioPlayers.size() - 1){
                    curPosition  = 0;
                }
                else {
                    curPosition ++;
                }

                if (isOnline){
                    listeningOnline(mAudioPlayers.get(curPosition).getFilePath());
                }
                else {
                    listteningOffline(mAudioPlayers.get(curPosition).getFilePath());
                }
                intenBroadCast.setAction(ACTION_1);
                intenBroadCast.putExtra(SENT_NAME_BH, mAudioPlayers.get(curPosition).getNameSong());
                intenBroadCast.putExtra(SENT_NAME_ARTIS,mAudioPlayers.get(curPosition).getComposer());
                sendBroadcast(intenBroadCast);
                break;
            case PRE_MUSIC:
                if (curPosition == 0 ){
                    curPosition = mAudioPlayers.size() - 1;
                }
                else {
                    curPosition --;
                }
                if (isOnline){
                    listeningOnline(mAudioPlayers.get(curPosition).getFilePath());
                }
                else {
                    listteningOffline(mAudioPlayers.get(curPosition).getFilePath());
                }
                intenBroadCast.setAction(ACTION_1);
                intenBroadCast.putExtra(SENT_NAME_BH, mAudioPlayers.get(curPosition).getNameSong());
                intenBroadCast.putExtra(SENT_NAME_ARTIS,mAudioPlayers.get(curPosition).getComposer());
                sendBroadcast(intenBroadCast);

                break;
            case PLAY_PAUSE:
                boolean isPlay = intent.getBooleanExtra(POP,false);
                if(isPlay){
                    mManagerMedidaPlayer.start();
                }
                else {
                    mManagerMedidaPlayer.pause();
                }
                intenBroadCast.setAction(ACTION_1);
                intenBroadCast.putExtra(SENT_NAME_BH, mAudioPlayers.get(curPosition).getNameSong());
                intenBroadCast.putExtra(SENT_NAME_ARTIS,mAudioPlayers.get(curPosition).getComposer());
                sendBroadcast(intenBroadCast);
                break;
            case TURN_ON_FRGT:
                int curPos = mManagerMedidaPlayer.getCurPos();
                intenBroadCast.setAction(ACTION_2);
                intenBroadCast.putExtra(SENT_NAME_BH, mAudioPlayers.get(curPosition).getNameSong());
                intenBroadCast.putExtra(SENT_NAME_ARTIS, mAudioPlayers.get(curPosition).getComposer());
                intenBroadCast.putExtra(SENT_CUR_POS, curPos);
                intenBroadCast.putExtra(SENT_DURATION,mManagerMedidaPlayer.getDurationn());

                sendBroadcast(intenBroadCast);

                break;
            default:
                break;

        }



        return START_NOT_STICKY;
    }
    private void listteningOffline(String filePath){
        mManagerMedidaPlayer.createMediaPlayer();
        try {
            mManagerMedidaPlayer.setDataSourceOff(filePath);
            mManagerMedidaPlayer.prepareAsyn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listeningOnline(final String linkURl){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Document document = Jsoup.connect(linkURl).get();
                    Element element = document.select("div.wrapper-page").first();
                    try {
                        String patJSoup = element.select("div#html5player").attr("data-xml");
                        Document document1 =
                                Jsoup.connect(patJSoup).ignoreContentType(true).get();
                        String str = document1.text();
                        ParseLinkListen parseLinkListen = new Gson().fromJson(str, ParseLinkListen.class);
                        int size = parseLinkListen.getDatas().
                                get(0).getList().size();
                        String result = parseLinkListen.getDatas().get(0).
                                getList().get(size- 1);
                        return result;
                    }catch (Exception ex){

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                mManagerMedidaPlayer.createMediaPlayer();
                try {
                    mManagerMedidaPlayer.setDataSourceOnLine(getApplicationContext(), Uri.parse("http://" + s));
                    mManagerMedidaPlayer.prepareAsyn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(curPosition == mAudioPlayers.size() - 1){
            curPosition  = 0;
        }
        else {
            curPosition ++;
        }

        if (isOnline){
            listeningOnline(mAudioPlayers.get(curPosition).getFilePath());
        }
        else {
            listteningOffline(mAudioPlayers.get(curPosition).getFilePath());
        }
    }
}
