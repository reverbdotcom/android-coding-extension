package networking

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {
  fun loadImage(uri : String, imageView : ImageView) {
    Glide.with(imageView.context)
      .load(uri)
      .into(imageView)
  }
}