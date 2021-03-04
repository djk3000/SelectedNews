package entity;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel extends BaseObservable {
    @SerializedName("T1348647853363")
    private List<DetialData> DetialData;

    public List<DataModel.DetialData> getDetialData() {
        return DetialData;
    }

    public void setDetialData(List<DataModel.DetialData> detialData) {
        DetialData = detialData;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public class DetialData extends BaseObservable {
        //标题
        private String title;

        //发布时间
        private String ptime;

        //来源
        private String source;

        //描述
        private String digest;

        //链接
        private String url;

        //图片链接
        private String imgsrc;

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyChange();
        }

        @Bindable
        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        @Bindable
        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Bindable
        public String getDigest() {
            return digest + "...";
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        @Bindable
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Bindable
        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
            notifyChange();
        }
    }
}
