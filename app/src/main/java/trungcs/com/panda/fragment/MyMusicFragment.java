package trungcs.com.panda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import trungcs.com.panda.adapter.AdapterListView;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.manager.ManagerAudioOffline;
import trungcs.com.panda.R;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 16/09/2016.
 */
public class MyMusicFragment extends Fragment implements AdapterListView.IAdapterAudio {
    private AdapterListView mAdapterListView;
    private ListView mListView;
    private ManagerAudioOffline mManagerAudioOffline;
    private List<AudioPlayer> audioPlayers;
    private IDataMedia mIDataMedia;
    public MyMusicFragment(IDataMedia iDataMedia){
        mIDataMedia = iDataMedia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_music,container,false);
        mManagerAudioOffline = new ManagerAudioOffline(getContext());
        audioPlayers = mManagerAudioOffline.getListAudioOff();
        mAdapterListView = new AdapterListView(this);
        mListView = (ListView) view.findViewById(R.id.lv_music);
        mListView.setAdapter(mAdapterListView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIDataMedia.getData(position, audioPlayers,false);
            }
        });
        return view;
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
