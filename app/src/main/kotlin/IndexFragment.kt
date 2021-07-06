import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.apollographql.apollo.exception.ApolloException
import com.reverb.android.onsite.R
import com.reverb.android.onsite.databinding.IndexFragmentBinding
import networking.ApolloWrapper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class IndexFragment : Fragment() {

  private lateinit var binding : IndexFragmentBinding

  private val apolloWrapper : ApolloWrapper by inject()

  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
    binding = IndexFragmentBinding.inflate(inflater, container, false).apply {
      pullToRefresh.setOnRefreshListener { fetchData() }
      recyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    fetchData()

    return binding.root
  }

  private fun fetchData() = MainScope().launch {
    binding.pullToRefresh.isRefreshing = true

    try {
      val response = apolloWrapper.getListingsResponse()
      response.data()?.listingsSearch?.listings?.filterNotNull()?.let {
        binding.recyclerView.adapter = IndexAdapter(it)
      }
    } catch (e : ApolloException) {
      // Network Failure
      Toast.makeText(context, R.string.listing_fetch_failed_toast, Toast.LENGTH_SHORT).show()
    } finally {
      binding.pullToRefresh.isRefreshing = false
    }
  }
}
