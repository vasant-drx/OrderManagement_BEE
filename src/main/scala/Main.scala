import java.time.LocalDate

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length == 2) {
      val orders = OrderService.getAllOrder()
      val result = OrderService.processOrder(orders, LocalDate.parse(args(0).split(" ")(0)), LocalDate.parse(args(1).split(" ")(0)), null)
      OrderService.display(result)
    }
    else if (args.length == 3) {
      val orders = OrderService.getAllOrder()
      val range: List[String] = args(2).split(',').toList.map(_.toString)
      val result = OrderService.processOrder(orders, LocalDate.parse(args(0).split(" ")(0)), LocalDate.parse(args(1).split(" ")(0)), range)
      OrderService.display(result)
    }
    else
      {
        println("Please provide argument")
        println("java -jar orderMgt.jar \"2018-01-01 00:00:00\" \"2019-01-01 00:00:00\" ")
        println("java -jar orderMgt.jar \"2018-01-01 00:00:00\" \"2019-01-01 00:00:00\" \"1-2\",\"8-12\"")
      }
  }
}