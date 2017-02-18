package trungcs.com.panda.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducnd on 14/09/2016.
 */
public class ParseLinkListen {
    @SerializedName("data")
    public List<Data> datas;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }
    public static class Data{
        @SerializedName("source_list")
        public List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
