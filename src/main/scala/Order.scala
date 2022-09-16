import java.time.LocalDate
import scala.collection.mutable.Seq


case class Order (var orderItemNumber : Int = 0, var name : String = null, var contactNumber : String = null, var shippingAddress : String,
                  var grandTotal : Int = 0, var orderDate:LocalDate,var item: Seq[OrderItem]
                 )
{
}
