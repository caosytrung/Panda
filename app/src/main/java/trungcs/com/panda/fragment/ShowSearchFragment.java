package trungcs.com.panda.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trungcs.com.panda.R;
import trungcs.com.panda.adapter.AdapterListView;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 22/09/2016.
 */
public class ShowSearchFragment extends Fragment implements AdapterListView.IAdapterAudio, View.OnClickListener {


    public IDataMedia getmIDataMedia() {
        return mIDataMedia;
    }

    public void setmIDataMedia(IDataMedia mIDataMedia) {
        this.mIDataMedia = mIDataMedia;
    }

    private IDataMedia mIDataMedia;
    private EditText mEdtSearch;
    private ImageView mIvback;
    private Button mBtSearch;
    private ListView mLvShowSearch;
    private AdapterListView mAdapterListView;;

    public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    private List<AudioPlayer> mAudioPlayers;
    private View.OnClickListener mOnClickListener;
    private String mContent;

    private static final String HEAD_URL = "http://mp3.zing.vn/tim-kiem/bai-hat.html?q=";
    private static final String CONTENT = "CONTENT";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_search, container, false);
        initViews(view);
        mBtSearch.

        Intent intent = getActivity().getIntent();
        mContent = intent.getStringExtra(CONTENT);
        mEdtSearch.setText(mContent);
        mAdapterListView = new AdapterListView(this);
        mAudioPlayers = new ArrayList<>();
        mLvShowSearch.setAdapter(mAdapterListView);
        mLvShowSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIDataMedia.getData(position,mAudioPlayers,true);
            }
        });
        new GetL().execute();


        return view;
    }
    private void initViews(View v){
        mEdtSearch = (EditText) v.findViewById(R.id.edt_frag_search);
        mBtSearch = (Button) v.findViewById(R.id.btn_frag_search);
        mIvback = (ImageView) v.findViewById(R.id.iv_frag_back);
        mLvShowSearch = (ListView) v.findViewById(R.id.lv_frag_search);
        mBtSearch.setOnClickListener(this);

    }

    @Override
    public int getCount() {
        return mAudioPlayers.size();
    }

    @Override
    public AudioPlayer getAudioPlayer(int pos) {
        return mAudioPlayers.get(pos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_frag_search:
                mAudioPlayers.clear();
                String content = mEdtSearch.getText().toString();
                if(content.length() == 0){
                    break;
                }
                mContent = mEdtSearch.getText().toString();
                new GetL().execute();
                break;
            default:
                break;
        }
    }

    public class GetL extends AsyncTask<Void,Void,List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {

            try {
                mContent= mContent.replace(' ','+');
                Document document = Jsoup.connect(HEAD_URL + mContent).get();
                Element element = document.select("div.wrap-content").first();
                Elements elements = element.select("div.item-song");

                for(Element ex : elements){
                    String nameSong = "";
                    String artist= "";
                    String duration = "";
                    String filePath = "";
                    try{
                        nameSong =  ex.select("a[href]").get(0).attr("title");

                    }
                    catch (Exception emm){

                    }
                    try{
                        filePath = ex.select("h3").first().select("a").attr("href");

                    }
                    catch (Exception emm){

                    }
                    String s[] = nameSong.split("-");
                    nameSong = s[0];
                    artist = s[1];
                    mAudioPlayers.add(new AudioPlayer(nameSong, "", artist, "", filePath));

                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> str) {
            super.onPostExecute(str);
            mAdapterListView.notifyDataSetChanged();
        }
    }

}
