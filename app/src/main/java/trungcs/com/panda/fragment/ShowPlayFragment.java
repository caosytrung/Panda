package trungcs.com.panda.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import trungcs.com.panda.R;

/**
 * Created by caotr on 24/09/2016.
 */
public class ShowPlayFragment extends Fragment {
    private SeekBar mSeekBar;
    private int curPos;
    private int duration;
    private TextView mTvCurDuration;
    private TextView mTvDuration;

    private static final String SENT_NAME_ARTIS = "SENT_NAME_ARTIS";
    private static final String SENT_NAME_BH = "SENT_NAME_BH";
    private static final String SENT_CUR_POS = "SENT_CUR_POS";
    private static final String SENT_DURATION= "SENT_DURATION";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pay_music, container, false);
        initView(v);
        Intent intent = getActivity().getIntent();
        curPos = intent.getIntExtra(SENT_CUR_POS,0);
        duration = intent.getIntExtra(SENT_DURATION,0);

        if((duration / 1000) %60 >= 10){
            mTvDuration.setText("0" + (duration/1000)/60 + ": " + (duration/1000)%60);
        }
        else {
            mTvDuration.setText("0" + (duration/1000)/60 + ": 0" + (duration/1000)%60);
        }
        if((duration/1000)%60 >= 10){
            mTvCurDuration.setText("0"+ (curPos/1000)/60 + ": " + (curPos/1000)%60);
        }
        else {
            mTvCurDuration.setText("0"+ (curPos/1000)/60 + ": 0" + (curPos/1000)%60);
        }


        runSeekbar();
        return v;
    }
    private void initView(View v){
        mSeekBar = (SeekBar) v.findViewById(R.id.seekbar_play);
        mTvCurDuration = (TextView) v.findViewById(R.id.tv_pos);
        mTvDuration = (TextView) v.findViewById(R.id.tv_fulltime);

    }

    private void runSeekbar(){
        curPos = curPos / 1000;
        duration = duration/ 1000;
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(curPos);
        new MyAsync().execute();
    }
    public class MyAsync extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = curPos ; i < duration ; i ++ ){
                SystemClock.sleep(1000);
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            int cur = values[0];
            mSeekBar.setProgress(cur);
            if(cur % 60 >= 10){
                mTvCurDuration.setText("0" + (cur/ 60) + ": " + (cur%60));
            }
            else {
                mTvCurDuration.setText("0" + (cur/ 60) + ": 0" + (cur%60));
            }
        }
    }


}
