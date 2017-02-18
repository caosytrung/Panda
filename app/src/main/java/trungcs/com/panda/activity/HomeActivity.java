package trungcs.com.panda.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import trungcs.com.panda.R;
import trungcs.com.panda.dialog.SearchDialog;
import trungcs.com.panda.fragment.MainFragment;
import trungcs.com.panda.fragment.ShowPlayFragment;
import trungcs.com.panda.fragment.ShowSearchFragment;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.interface_callback.ISearch;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 21/09/2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener,ISearch,IDataMedia {

    private MainFragment fragment1;
    private ShowPlayFragment mShowPlayFragment;
    private ShowSearchFragment mShowSearchFragment;
    private TextView tvShowName;
    private TextView tvShowComposer;
    private ImageView ivPre;
    private ImageView ivNext;
    private MyBroadCast1 mMyBroadCast;
    private ImageView mIvPlayPause;
    private boolean isPlay;
    private TableRow mTbRowBot;

    private static final String ACTION_2 ="ACTION_2";
    private static final String TURN_ON_FRGT ="TURN_ON_FRGT" ;
    private static final String POP = "PLAY_OR_PAUSE";
    private static final String PLAY_PAUSE = "PLAY_PAUSE";
    private static final String PRE_MUSIC = "PRE_MUSIC";
    private static final String NEXT_MUSIC = "NEXT_MUSIC";
    private static final String CONTENT = "CONTENT";
    private static final String DEMO = "DEMO";
    private static final String POSITION = "POSITION";
    public static final String ACTION_1 = "ACTION1";
    public static final String KEY_SENT_LIST = "SENT_LIST";
    private static final String KEY = "KEY";
    private static final String SENT = "SENT";
    public static  final String SAVE = "SAVE";
    private static final String NAME_SONG = "NAME_SONG";
    private static final String ARTIST = "ARTIST";
    private static final String CHECK_ONLINE ="CHECK_ONLINE" ;
    private static final String SENT_NAME_ARTIS = "SENT_NAME_ARTIS";
    private static final String SENT_NAME_BH = "SENT_NAME_BH";
    private static final String SENT_CUR_POS = "SENT_CUR_POS";
    private static final String SENT_DURATION= "SENT_DURATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragment();
        mMyBroadCast = new MyBroadCast1();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_1);
        filter.addAction(ACTION_2);
        registerReceiver(mMyBroadCast, filter);
        isPlay = true;
       // hideView();

    }
    private void initFragment(){
        fragment1 = new MainFragment(getSupportFragmentManager(),this);
        mShowSearchFragment = new ShowSearchFragment();
        mShowPlayFragment = new ShowPlayFragment();
        mShowSearchFragment.setmIDataMedia(this);
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.content_fragment, fragment1).commit();
    }
    private void showPlayMusicFragment(){
        getSupportFragmentManager().
                beginTransaction().
                setCustomAnimations(R.anim.open,R.anim.exit).
                addToBackStack("1").
                add(R.id.content_fragment, mShowPlayFragment).commit();
    }

    private void showFragmentSearch(){
        getSupportFragmentManager().
                beginTransaction().
                setCustomAnimations(R.anim.open, R.anim.exit).
                addToBackStack("2").
                add(R.id.content_fragment, mShowSearchFragment).commit();
    }

    private void initViews(){
        mTbRowBot = (TableRow) findViewById(R.id.table_row_bottom);
        tvShowName = (TextView) findViewById(R.id.tv_show_name_song);
        tvShowComposer = (TextView) findViewById(R.id.tv_show_composer);
        ivPre = (ImageView) findViewById(R.id.iv_pre);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        mIvPlayPause = (ImageView) findViewById(R.id.iv_play_pause);
        mTbRowBot.setEnabled(false);
        mTbRowBot.setOnClickListener(this);

        setEventView();
    }
    private void setEventView(){
        ivPre.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        mIvPlayPause.setOnClickListener(this);

    }


    public void start(int position, List<AudioPlayer> audioPlayers,boolean isOnline){
        mTbRowBot.setEnabled(true);
        mIvPlayPause.setImageLevel(0);
        Log.d("ccc", "abcderttt");
        Intent intent = new Intent();
        intent.setClassName("trungcs.com.panda", "trungcs.com.panda.ServiceMusic");
        intent.putExtra(POSITION, position);
        intent.putExtra(CHECK_ONLINE, isOnline);
        intent.putParcelableArrayListExtra(KEY_SENT_LIST, (ArrayList<? extends Parcelable>) audioPlayers);
        intent.putExtra(KEY,SENT);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_searchnhac:
                SearchDialog searchDialog = new SearchDialog(this,this);
                Window window
                        = searchDialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.TOP|Gravity.LEFT;
                layoutParams.x = 0;
                layoutParams.y = 20;

                window.setAttributes(layoutParams);
                searchDialog.show();
                break;
            case R.id.iv_pre:
                Intent intent = new Intent();
                intent.setClassName("trungcs.com.panda", "trungcs.com.panda.ServiceMusic");
                intent.putExtra(KEY, PRE_MUSIC);
                startService(intent);
                break;
            case R.id.iv_next:
                Intent intent1 = new Intent();
                intent1.setClassName("trungcs.com.panda", "trungcs.com.panda.ServiceMusic");
                intent1.putExtra(KEY, NEXT_MUSIC);
                startService(intent1);
                break;
            case R.id.iv_play_pause:
                isPlay = !isPlay;
                if(isPlay){
                    mIvPlayPause.setImageLevel(0);
                }
                else {
                    mIvPlayPause.setImageLevel(1);
                }
                Intent intent2 = new Intent();
                intent2.setClassName("trungcs.com.panda", "trungcs.com.panda.ServiceMusic");
                intent2.putExtra(KEY,PLAY_PAUSE);
                intent2.putExtra(POP,isPlay);
                startService(intent2);
                break;
            case R.id.table_row_bottom:
                Intent intent3 = new Intent();
                intent3.setClassName("trungcs.com.panda", "trungcs.com.panda.ServiceMusic");
                intent3.putExtra(KEY,TURN_ON_FRGT);
                startService(intent3);
                break;
            default:
                break;
        }
    }

    @Override
    public void search(String contenSearch) {
        if(contenSearch.length() == 0 ){
            return;
        }
        getIntent().putExtra(CONTENT,contenSearch);
        showFragmentSearch();
    }

    @Override
    public void getData(int pos, List<AudioPlayer> list, boolean isOnline) {
        mTbRowBot.setVisibility(View.VISIBLE);
        start(pos,list,isOnline);
    }


    private class MyBroadCast1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ccc", "abcdert");
            switch (intent.getAction()){
                case ACTION_1:
                    String nameSong = intent.getStringExtra(SENT_NAME_BH);
                    tvShowName.setText(nameSong);
                    String nameArtis = intent.getStringExtra(SENT_NAME_ARTIS);
                    tvShowComposer.setText(nameArtis);
                    break;
                case ACTION_2:
                    hideView();
                    String song = intent.getStringExtra(SENT_NAME_BH);
                    String artist = intent.getStringExtra(SENT_NAME_ARTIS);
                    int cur_dur = intent.getIntExtra(SENT_CUR_POS, 0);
                    int duration = intent.getIntExtra(SENT_DURATION,0);
                    Log.d("ccccc", "qwerty / " + cur_dur + " / " + duration);
                    getIntent().putExtra(SENT_NAME_BH, song);
                    getIntent().putExtra(SENT_NAME_ARTIS, artist);
                    getIntent().putExtra(SENT_CUR_POS, cur_dur);
                    getIntent().putExtra(SENT_DURATION, duration);
                    showPlayMusicFragment();

                    break;
                default:
                    break;
            }
        }
    }
    private void hideView(){
        mTbRowBot.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
//        if (mShowSearchFragment.isAdded()) {
//            getSupportFragmentManager().beginTransaction().remove(mShowSearchFragment).commit();
//            getSupportFragmentManager().beginTransaction().show(fragment1).commit();
//        }
//        else {
            super.onBackPressed();
//        }
    }
}
