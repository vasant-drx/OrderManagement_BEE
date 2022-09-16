import java.time.{LocalDate, Period}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object OrderService {
  val orderList = mutable.ListBuffer[Order]()
  val orderAge: mutable.LinkedHashMap[String, Int] = mutable.LinkedHashMap[String, Int]()
  orderAge.addOne("1-3", 0)
  orderAge.addOne("4-6", 0)
  orderAge.addOne("7-12", 0)
  orderAge.addOne(">12", 0)

  def createOrder(): Unit = {
    val orderItem = mutable.ListBuffer[OrderItem]()
    val orderItems = OrderItem(1, 1450, 30, 20, Product(1, "ABC", 30, 100, "xyx", LocalDate.parse("2018-10-10")))
    orderItem.addOne(orderItems)
    orderList.addOne(Order(1, "VACANT", "+919956565XX", "TO ABC", 1500, LocalDate.now(), orderItem))

    val orderItem2 = mutable.ListBuffer[OrderItem]()
    val orderItems2 = OrderItem(1, 1450, 30, 20, Product(1, "ABC", 30, 100, "xyx", LocalDate.parse("2018-05-01")))
    orderItem2.addOne(orderItems2)
    orderList.addOne(Order(2, "Testing", "+919956565XX", "TO ABC", 1200, LocalDate.now(), orderItem2))

    val orderItem3 = mutable.ListBuffer[OrderItem]()
    val orderItems3 = OrderItem(1, 1450, 30, 20, Product(1, "ABC", 30, 100, "abc", LocalDate.parse("2018-08-01")))
    orderItem3.addOne(orderItems3)
    orderList.addOne(Order(3, "DataX", "+919956565XX", "TO ABC", 1200, LocalDate.now(), orderItem3))
  }

  def getAllOrder(): ListBuffer[Order] = {
    createOrder()
    orderList
  }

  def display(result: mutable.LinkedHashMap[String, Int]): Unit = {
    for (key <- result.keys) {
      println(key + " months: " + result.get(key).get + "  orders")
    }
  }

  @deprecated
  def processOrder(data: ListBuffer[Order], start: LocalDate, end: LocalDate): mutable.LinkedHashMap[String, Int] = {
    for (order <- data) {
      for (item <- order.item) {
        println("Product  creation date: " + item.product.creationDate);
        val product_Date = item.product.creationDate
        println(product_Date.isBefore(end) && product_Date.isAfter(start))
        if (product_Date.isBefore(end) && product_Date.isAfter(start)) {
          val p = Period.between(start, product_Date)
          println(p.getMonths())
          p.getMonths() match {
            case 1 | 2 | 3 => orderAge.update("1-3", orderAge.get("1-3").get + 1)
            case 4 | 5 | 6 => orderAge.update("4-6", orderAge.get("4-6").get + 1)
            case 7 | 8 | 9 | 10 | 11 | 12 => orderAge.update("7-12", orderAge.get("7-12").get + 1)
            case 0 =>
            case _ => orderAge.update(">12", orderAge.get(">12").get + 1)
          }
        }
      }
    }
    orderAge
  }


  def processOrder(data: ListBuffer[Order], start: LocalDate, end: LocalDate, ranges: List[String]): mutable.LinkedHashMap[String, Int] = {
    if (ranges != null) {
      orderAge.clear()
      for (age <- ranges) {
        orderAge.addOne(age, 0)
      }
    }
    for (order <- data) {
      for (item <- order.item) {
        println("Product  creation date: " + item.product.creationDate);
        val product_Date = item.product.creationDate
        if (product_Date.isBefore(end) && product_Date.isAfter(start)) {
          val p = Period.between(start, product_Date)
          for (rangeKey <- orderAge.keys) {
            val rangeValues = rangeKey.split("-")
            if (rangeValues.length == 2) {
              if (rangeValues(0).toInt <= p.getMonths && p.getMonths <= rangeValues(1).toInt) {
                orderAge.update(rangeKey, orderAge.get(rangeKey).get + 1)
              }
            } else {
              rangeKey match {
                case rangeKey if rangeKey.contains(">") && p.getMonths > rangeKey.split(">")(1).toInt =>
                  orderAge.update(rangeKey, orderAge.get(rangeKey).get + 1)
                case rangeKey if rangeKey.contains("<") && p.getMonths > rangeKey.split("<")(1).toInt =>
                  orderAge.update(rangeKey, orderAge.get(rangeKey).get + 1)
                case _ =>

              }
            }
          }
        }
      }
    }
    orderAge
  }
}
