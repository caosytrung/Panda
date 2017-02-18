package trungcs.com.panda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import trungcs.com.panda.R;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 16/09/2016.
 */
public class AdapterListView extends BaseAdapter {
    private IAdapterAudio iAdapterAudio;
    public AdapterListView(IAdapterAudio iAdapterAudio){
        this.iAdapterAudio = iAdapterAudio;
    }

    @Override
    public int getCount() {
        return iAdapterAudio.getCount();
    }

    @Override
    public AudioPlayer getItem(int position) {
        return iAdapterAudio.
                getAudioPlayer(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_listview,parent,false);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_title);
            viewHolder.tvNameSOng = (TextView) convertView.findViewById(R.id.tv_name_song);
            viewHolder.tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AudioPlayer audioPlayer = iAdapterAudio.getAudioPlayer(position);
        viewHolder.tvNameSOng.setText(audioPlayer.getNameSong());
        viewHolder.tvArtist.setText("< " + audioPlayer.getSinger() + " >");

        return convertView;
    }

    public static class ViewHolder{
        public ImageView iv;
        public TextView tvNameSOng;
        private TextView tvArtist;
    }

    public interface IAdapterAudio {

        public int getCount();
        public AudioPlayer getAudioPlayer(int pos);
    }
}