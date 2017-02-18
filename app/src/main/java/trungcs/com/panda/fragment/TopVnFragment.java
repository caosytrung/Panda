package trungcs.com.panda.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import trungcs.com.panda.R;
import trungcs.com.panda.adapter.AdapterListView;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 16/09/2016.
 */
public class TopVnFragment extends Fragment  implements AdapterListView.IAdapterAudio {
    private static final String URL ="http://mp3.zing.vn/top100/Nhac-Tre/IWZ9Z088.html" ;
    private ListView mListView;
    private List<AudioPlayer> audioPlayers;
    private AdapterListView mAdapterListView;
    private IDataMedia mIDataMedia;
    public TopVnFragment(IDataMedia iIDataMedia){
        mIDataMedia = iIDataMedia;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_topvn,container,false);
        audioPlayers = new ArrayList<>();
        mListView = (ListView) v.findViewById(R.id.lv_topvn);
        mAdapterListView = new AdapterListView(this);
        mListView.setAdapter(mAdapterListView);
        new GetL().execute();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mIDataMedia.getData(position, audioPlayers,true);


            }
        });
        return v;
    }
    public class GetL extends AsyncTask<Void,Void,List<String>> {

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
