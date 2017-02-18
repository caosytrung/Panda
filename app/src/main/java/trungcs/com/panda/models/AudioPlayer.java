package trungcs.com.panda.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by caotr on 16/09/2016.
 */
public class AudioPlayer implements Serializable, Parcelable {
    private String nameSong;
    private String composer;

    protected AudioPlayer(Parcel in) {
        nameSong = in.readString();
        composer = in.readString();
        singer = in.readString();
        duration = in.readString();
        filePath = in.readString();
    }



    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    private String singer;
    private String duration;
    private String filePath;

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AudioPlayer(String nameSong,String composer, String singer, String duration, String filePath) {

        this.nameSong = nameSong;
        this.composer= composer;
        this.singer = singer;
        this.duration = duration;
        this.filePath = filePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameSong);
        dest.writeString(composer);
        dest.writeString(singer);
        dest.writeString(duration);
        dest.writeString(filePath);
    }
    public static final Creator<AudioPlayer> CREATOR = new Creator<AudioPlayer>() {
        @Override
        public AudioPlayer createFromParcel(Parcel in) {
            return new AudioPlayer(in);
        }

        @Override
        public AudioPlayer[] newArray(int size) {
            return new AudioPlayer[size];
        }
    };
}
