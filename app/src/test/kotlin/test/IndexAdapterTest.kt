package test

import IndexAdapter
import ViewHolder
import android.widget.ImageView
import android.widget.TextView
import com.reverb.android.onsite.com.KoinTestRule
import networking.ListingModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import networking.ImageLoader
import org.hamcrest.MatcherAssert.assertThat
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class IndexAdapterTest : KoinTest {

  @get:Rule val koinTestRule = KoinTestRule(shouldStartKoin = true)

  private val imageLoader : ImageLoader by inject()

  @Test fun getItemCount() {
    val listings = listOf<ListingModel>(mockk(), mockk())

    IndexAdapter(listings).run {
      assertThat(itemCount, `is`(2))
    }
  }

  @Test fun onBindViewHolder() {
    val listings = listOf(mockk<ListingModel>().apply {
      every { title } returns "title"
      every { price?.display } returns "$123"
      every { images } returns listOf(mockk { every { source } returns "image.html"} )
    })

    val mockListingTitle = mockk<TextView>()
    val mockListingPrice = mockk<TextView>()
    val mockListingImage = mockk<ImageView>()

    val viewHolder = mockk<ViewHolder>().apply {
      every { listingTitle } returns mockListingTitle
      every { listingPrice } returns mockListingPrice
      every { listingImage } returns mockListingImage
    }

    IndexAdapter(listings).run {
      onBindViewHolder(viewHolder, 0)

      verify { mockListingTitle.text = "title" }
      verify { mockListingPrice.text = "$123" }
      verify { imageLoader.loadImage("image.html", mockListingImage) }
    }
  }
}
