import  java.util.Calendar
import scala.collection.immutable.Stream.Cons

/*
 * Order: Customer name and contact, shipping address, grand total, date when the order was placed
 */
object Constants {
  val secondsInYear:BigInt = BigInt.int2bigInt(31556926)
  val secondsInMonth:BigInt = BigInt.int2bigInt(2550000)
  val secondsInDay:BigInt = BigInt.int2bigInt(86400)
  val secondsInHour:BigInt = BigInt.int2bigInt(3600)
  val secondsInMinute:BigInt = BigInt.int2bigInt(60)
}
/*
 * Order: Customer name and contact, shipping address, grand total, date when the order was placed
 */
case class Order(customerName:String, contact:String, shippingAddress:String, total:String, placedDate:Time, items:List[Item]) {
  def getTime() =
    placedDate

  def getItems() =
    items

}
/*
 * Item: Cost, shipping fee, tax amount
 */
case class Item(cost:Double, shippingFee:Double, taxAmount:Double, product:Product) {
  def getProduct() =
    product
    
}
/*
 * Product: Name, category, weight, price, creation date
 */
case class Product(name:String, category:String, weight:Double, price:Double, creationDate:Time) {
    def getAge() =
      val now     = Calendar.getInstance()
      val year    = now.get(Calendar.YEAR)
      val month   = now.get(Calendar.MONTH)
      val day     = now.get(Calendar.DAY_OF_MONTH)
      val hour    = now.get(Calendar.HOUR_OF_DAY)
      val minute  = now.get(Calendar.MINUTE)
      val second  = now.get(Calendar.SECOND)

      val currentTimeStr = f"${year}%04d" + "-" + f"${month}%02d" + "-" + f"${day}%02d" + " " + f"${hour}%02d" + ":" + f"${minute}%02d" + ":" + f"${second}%02d"
      val currentTime = Time(currentTimeStr)

      currentTime - creationDate

}
/*
 * Time: Encapsulates info about date
 */
case class Time(var time:String) {
  /* Date format */
  val pattern = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d) (\\d\\d):(\\d\\d):(\\d\\d)".r

  /* Check if date format input is correct */
  if pattern.matches(time) == false then
    println("[ERROR] Wrong format for Time (yyyy-mm-dd hh:mm:ss)")    
    sys.exit(1)
  
  /* Get time from date string  */
  val pattern(yearStr, monthStr, dayStr, hourStr, minuteStr, secondStr) = time
  val year    = BigInt.int2bigInt(yearStr.toInt)
  val month   = BigInt.int2bigInt(monthStr.toInt)
  val day     = BigInt.int2bigInt(dayStr.toInt)
  val hour    = BigInt.int2bigInt(hourStr.toInt)
  val minute  = BigInt.int2bigInt(minuteStr.toInt)
  val second  = BigInt.int2bigInt(secondStr.toInt)
  
  override def toString() =
    time
  
  def toSeconds() =
    val totalSeconds:BigInt = 
      year*Constants.secondsInYear      + 
      month*Constants.secondsInMonth    + 
      day*Constants.secondsInDay        + 
      hour*Constants.secondsInHour      + 
      minute*Constants.secondsInMinute  + 
      second

    totalSeconds

  def -(other:Time) =
    this.toSeconds() - other.toSeconds()

  def >=(other:Time) =
    this.toString() >= other.toString()

  def <=(other:Time) =
    this.toString() <= other.toString()
}
/*
 * MonthInterval: Encapsulate info about a range in months (mm - mm)
 */
case class MonthInterval(interval:String) {
  /* Accepted patterns */
  val pattern = "(\\d+)-(\\d+)".r
  val patternLast = ">(\\d+)".r

  /* Check if some accepted pattern matches input */  
  if pattern.matches(interval) == false && patternLast.matches(interval) == false then
    println("[ERROR] Wrong format for Month Period (\\d+-\\d+)")    
    sys.exit(1)

  /* Get values start month and end month of period */
  var startMonth:BigInt = -1
  var endMonth:BigInt = -1
  if pattern.matches(interval) then
    val pattern(tempStartMonth, tempEndMonth) = interval
    startMonth = BigInt.int2bigInt(tempStartMonth.toInt)
    endMonth = BigInt.int2bigInt(tempEndMonth.toInt)
  else
    val patternLast(tempStartMonth) = interval
    startMonth = BigInt.int2bigInt(tempStartMonth.toInt)

  override def toString() =
    interval

  def getStart() = 
    startMonth

  def getEnd() = 
    endMonth

  def insideInterval(months:BigInt) =
    val cond1 = months > startMonth && endMonth == -1
    val cond2 = months >= startMonth && months <= endMonth

    if cond1 || cond2 then
      true
    else false
}

/*
 * START::Static test data
 */
/* Age: 1 months */
val product1 = Product("product1", "category1", 1.0, 1.0, Time("2022-04-01 10:00:00"))
/* Age: 4 months */
val product2 = Product("product2", "category2", 1.0, 1.0, Time("2022-01-01 10:00:20"))
/* Age: 9 months */
val product3 = Product("product3", "category3", 1.0, 1.0, Time("2021-08-01 10:00:00"))
/* Age: 12 months */
val product4 = Product("product4", "category4", 1.0, 1.0, Time("2021-05-01 10:00:00"))
/* Age: 85 months */
val product5 = Product("product5", "category5", 1.0, 1.0, Time("2015-06-02 10:01:00"))

val item1 = Item(1.0, 1.0, 1.0, product1)
val item2 = Item(1.0, 1.0, 1.0, product2)
val item3 = Item(1.0, 1.0, 1.0, product3)
val item4 = Item(1.0, 1.0, 1.0, product4)
val item5 = Item(1.0, 1.0, 1.0, product5)
val order1 = Order("customer1", "000000000", "address1", "total", Time("2010-01-01 13:45:33"), List(item1))
val order2 = Order("customer1", "000000000", "address1", "total", Time("2011-01-01 13:45:33"), List(item2))
val order3 = Order("customer1", "000000000", "address1", "total", Time("2012-01-01 13:45:33"), List(item3))
val order4 = Order("customer1", "000000000", "address1", "total", Time("2013-01-01 13:45:33"), List(item4))
val order5 = Order("customer1", "000000000", "address1", "total", Time("2012-01-01 13:45:33"), List(item5))
val orders:List[Order] = List(order1, order2, order3, order4, order5)
/*
 * END::Static test data
 */

@main def main(args:String*) = 
  val argc = args.length
  val currentTime = Time("2022-06-02 12:00:00")

  /* Check if there's interval input from bash  */
  if argc < 2 then
    println("[ERROR] Not enough arguments")
    sys.exit(1)
  /* Mount list of intervals for products */
  val monthIntervals:List[MonthInterval] = 
    if (argc > 2) 
      args
      /* Get elements from third to last (First two elements are interval for orders) */
      .tail
      .tail
      /* Mount list of intervals */
      .foldLeft(List[MonthInterval]())((acc, interval) => acc :+ MonthInterval(interval)) 
    else 
      /* Default list of product intervals in case of no input */
      List(MonthInterval("1-3"), MonthInterval("4-6"), MonthInterval("7-12"), MonthInterval(">12"))
      

  /* Sort list of order intervals to make it increasing */
  val interval:List[Time] = List(Time(args(0)), Time(args(1))).sortBy(time => time.toString())
  
  val filteredOrders = 
    orders
    /* Filter orders inside orders interval */
    .filter(order => (order.getTime() >= interval(0)) && (order.getTime() <= interval(1)))
    /* Mount new list of filtered orders */
    .foldLeft(List[Order]())((acc, order) => acc :+ order)
  
  /* Iterate list of product month intervals */
  monthIntervals.map((monthInterval) => {
    print(monthInterval)
    print(" months:\t")
    /* Get total of products inside month interval*/
    val totalOrders = 
      filteredOrders
      /* Access orders to get items and count products with age inside current interval */
      .foldLeft(0)((accOrder, order) => {
      /* Iterate items of order increasing accumulator for each product with age inside current interval */
      accOrder + order.getItems().foldLeft(0)((accItem, item) => {
        val productAge:BigInt = item.getProduct().getAge()

        val productAgeInMonths:BigInt = productAge/Constants.secondsInMonth

        if monthInterval.insideInterval(productAgeInMonths) then
          accItem + 1
        else
          accItem
      })
    })

    println(totalOrders.toString() + " orders")
  })
  