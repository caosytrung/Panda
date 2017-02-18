package trungcs.com.panda.interface_callback;

import java.util.List;

import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 19/09/2016.
 */
public interface IDataMedia {
    public void getData(int pos, List<AudioPlayer> list,boolean isOnline);

}
