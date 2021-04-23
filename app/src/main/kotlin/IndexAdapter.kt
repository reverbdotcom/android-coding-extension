import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reverb.android.onsite.databinding.ListingGridItemBinding
import networking.ImageLoader
import networking.ListingModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("EXPERIMENTAL_API_USAGE")
class IndexAdapter(
  private val listings : List<ListingModel>
) : RecyclerView.Adapter<ViewHolder>(), KoinComponent {

  private val imageLoader : ImageLoader by inject()

  override fun getItemCount() = listings.size

  override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
    val binding = ListingGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  @Suppress("UNNECESSARY_SAFE_CALL")
  override fun onBindViewHolder(holder : ViewHolder, position : Int) {
    val listing = listings[position]
    holder.listingTitle.text = listing.title
    holder.listingPrice.text = listing.price?.display
    listing.images?.first()?.source?.let { holder.listingImage.loadImage(it) }
  }

  private fun ImageView.loadImage(url : String) = imageLoader.loadImage(url, this)
}

class ViewHolder(binding : ListingGridItemBinding) : RecyclerView.ViewHolder(binding.root) {
  val listingTitle : TextView = binding.listingTitle
  val listingPrice : TextView = binding.listingPrice
  val listingImage : ImageView = binding.listingImage
}
