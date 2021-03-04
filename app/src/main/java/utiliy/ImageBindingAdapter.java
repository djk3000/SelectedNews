package utiliy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageBindingAdapter {
    @BindingAdapter({"imageUrl", "placeHolder", "error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", "https://");
        }
        final String bingPic_a = url;
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }
}
