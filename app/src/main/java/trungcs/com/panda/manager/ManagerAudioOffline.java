package trungcs.com.panda.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 16/09/2016.
 */
public class ManagerAudioOffline {
    private static final String TAG = "ManagerAudioOffline";
    private Context mContext;

    public ManagerAudioOffline(Context context){
        mContext = context;
    }

    public List<AudioPlayer> getListAudioOff(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String propetiess[] = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.COMPOSER
        };
        ContentResolver resolver = mContext.getContentResolver();



        Cursor cursor = mContext.getContentResolver().query(uri,
                propetiess, null, null, null);


        List<AudioPlayer> audioPlayers = new ArrayList<>();
//        for(String a : cols){
//            Log.d(TAG, "getColumnNames :" + a);
//        }
        if (cursor == null || cursor.getCount() == 0 ){
            return audioPlayers;
        }
        int indexTitle= cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexArtist= cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexDuaration= cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexData= cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexComposer= cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String nameSong = cursor.getString(indexTitle);
            String singer = cursor.getString(indexArtist);
            String duration = cursor.getString(indexDuaration);
            String filePath = cursor.getString(indexData);
            String composer= cursor.getString(indexComposer);
            audioPlayers.add(new AudioPlayer(nameSong, composer,
                singer,duration,filePath));

            Log.d(TAG,"nameSong : " + nameSong);
        cursor.moveToNext();
    }
        return audioPlayers;
    }
}
