package trungcs.com.panda.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trungcs.com.panda.adapter.AdapterListView;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.R;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 16/09/2016.
 */
public class ToChauAFragment  extends Fragment implements AdapterListView.IAdapterAudio {
    private static final String URL = "http://mp3.zing.vn/top100/Han-Quoc/IWZ9Z08W.html";
    private static final String TAG = "ToChauAFragment";
    private ListView mListView;
    private AdapterListView mAdapterListView;
    private List<AudioPlayer> audioPlayers = new ArrayList<>();
    private IDataMedia mIDataMedia;
    public ToChauAFragment(IDataMedia iDataMedia){
        mIDataMedia = iDataMedia;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_chaua, container, false);
        new GetL(this).execute();
        mAdapterListView = new AdapterListView(this);
        mListView = (ListView) view.findViewById(R.id.lv_topchaua);
        mListView.setAdapter(mAdapterListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIDataMedia.getData(position, audioPlayers, true);
            }
        });
        return view;
    }

    public class GetL extends AsyncTask<Void,Void,List<String>>{
        private ToChauAFragment toChauAFragment;
        public GetL(ToChauAFragment toChauAFragment){
            this.toChauAFragment =toChauAFragment;
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            try {
                Document document = Jsoup.connect(URL).get();
                Element e = document.select("ul.fn-list").first();
                Elements elements = e.select("div.e-item");

                for(Element ex : elements){
                    String nameSong = "";
                    String artist= "";
                    String duration = "";
                    String filePath = "";
                    try{
                         nameSong = ex.select("a[href]").get(0).attr("title");

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
                    audioPlayers.add(new AudioPlayer(nameSong, "", artist, "", filePath));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.d(TAG, "cccccccccc");
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> str) {
            super.onPostExecute(str);
            mAdapterListView.notifyDataSetChanged();
        }
    }



    @Override
    public int getCount() {
        return audioPlayers.size();
    }

    @Override
    public AudioPlayer getAudioPlayer(int pos) {
        return audioPlayers.get(pos);
    }
}
