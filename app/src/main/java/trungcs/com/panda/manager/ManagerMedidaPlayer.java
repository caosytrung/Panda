package trungcs.com.panda.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by caotr on 16/09/2016.
 */
public class ManagerMedidaPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaPlayer;
    private State mState;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;


    public ManagerMedidaPlayer(MediaPlayer.OnCompletionListener onCompletionListener){
        mState = State.IDLE;
        mOnCompletionListener = onCompletionListener;
    }

    public int getCurPos(){
        return  mMediaPlayer.getCurrentPosition();
    }

    public int getDurationn(){
        return mMediaPlayer.getDuration();
    }

    public void setDataSourceOff(String dataSource)
        throws IOException
    {
        mMediaPlayer.setDataSource(dataSource);
        mState = State.INIT;
    }
    public void setDataSourceOnLine (Context context, Uri uri)
        throws IOException
    {
        mMediaPlayer.setDataSource(context,uri);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mState =State.INIT;

    }

    public void prepareAsyn(){
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
        mState = State.PREPARING;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }




    @Override
    public void onPrepared(MediaPlayer mp) {
        mState = State.PREPARED;
        mState = State.PREPARED;
        start();
    }

    public void start() {
        if(mState == State.PREPARED ||
                mState == State.PAUSE ||
                mState == State.STOP
                ){
            mMediaPlayer.start();
            mState = State.START;
        }
    }

    public void stop(){
        if(mState == State.PAUSE ||
                mState == State.START){
            mMediaPlayer.stop();
            mState = State.STOP;
        }
    }

    public void pause(){
        if(mState == State.START){
            mMediaPlayer.pause();
            mState = State.PAUSE;
        }
    }

    public void seek(int pos){
        if((mState == State.START ||
                mState ==State.PAUSE ||
                mState == State.PREPARED ) &&
                pos <= mMediaPlayer.getDuration()
                ){
            mMediaPlayer.seekTo(pos);
        }
    }

    public void release(){
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void loop(boolean loop){
        if(mState != State.IDLE){
            mMediaPlayer.setLooping(loop);
        }
    }

   public void contine(){

       mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
       mState = State.START;

   }
    public void createMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void autonext(){

    }


    public static  enum State{
        PREPARING(0),
        START(1),
        STOP(2),
        PAUSE(3),
        RELEASE(4),
        IDLE(5),
        INIT(6),
        PREPARED(7);
        private int value;
        private State(int value){
            this.value = value;
        }
    }
}
