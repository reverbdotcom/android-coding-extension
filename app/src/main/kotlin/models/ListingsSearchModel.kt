package models

class ListingsSearchQueryModel(val listingsSearch : ListingsSearchModel) {

  class ListingsSearchModel(val listings : List<ListingModel>)

  class ListingModel(
    val id : String,
    val title : String,
    val price : MoneyModel,
    val shippingPrice : MoneyModel,
    val images : List<ImageModel>,
  )

  class MoneyModel(val display : String)
  class ImageModel(val source : String)
}