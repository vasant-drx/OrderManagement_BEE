import java.time.LocalDate

//import play.api.libs.json._
case class Product( id: Long, name: String,
                    wight: Double, price: Double,
                    category: String,creationDate:LocalDate)

