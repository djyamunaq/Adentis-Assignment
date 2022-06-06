import Time.totalMonths

import java.time.YearMonth
import java.util.Calendar
import scala.collection.immutable.Stream.Cons

/*
 * Order: Customer name and contact, shipping address, grand total, date when the order was placed
 */
case class Order(
    placedDate: Time,
    items: List[Item]
)
/*
 * Item: Cost, shipping fee, tax amount
 */
case class Item(
    product: Product
)
/*
 * Product: Name, category, weight, price, creation date
 */
case class Product(
    creationDate: Time
) {
  def getAge() =
    val currentTime = Time(YearMonth.now())
    currentTime - creationDate

}
/*
 * Time: Encapsulates info about date
 */
object Time {
  val totalMonths: YearMonth => Int = yearMonth =>
    yearMonth.getYear * 12 + yearMonth.getMonthValue
  def apply(totalMonths: Int): Time =
    Time(
      YearMonth.of(
        totalMonths / 12,
        totalMonths % 12
      )
    )
}
case class Time(time: YearMonth) {

  def -(other: Time) = {
    Time.apply(totalMonths(time) - totalMonths(other.time))
  }

  def >=(other: Time) =
    totalMonths(time) >= totalMonths(other.time)

  def <=(other: Time) =
    totalMonths(time) <= totalMonths(other.time)
}
/*
 * MonthInterval: Encapsulate info about a range in months (mm - mm)
 */
case class MonthInterval(interval: String) {
  /* Accepted patterns */
  val pattern = "(\\d+)-(\\d+)".r
  val patternLast = ">(\\d+)".r

  /* Check if some accepted pattern matches input */
  if pattern.matches(interval) == false && patternLast.matches(
      interval
    ) == false
  then
    println("[ERROR] Wrong format for Month Period (\\d+-\\d+)")
    sys.exit(1)

  /* Get values start month and end month of period */
  var startMonth: BigInt = -1
  var endMonth: BigInt = -1
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

  def insideInterval(months: BigInt) =
    val cond1 = months > startMonth && endMonth == -1
    val cond2 = months >= startMonth && months <= endMonth

    if cond1 || cond2 then true
    else false
}

/*
 * START::Static test data
 */
/* Age: 1 months */
val product1 =
  Product(Time(YearMonth of (2022, 4)))
/* Age: 4 months */
val product2 =
  Product(Time(YearMonth of (2022, 1)))
/* Age: 9 months */
val product3 =
  Product(Time(YearMonth of (2021, 8)))
/* Age: 12 months */
val product4 =
  Product(Time(YearMonth of (2021, 5)))
/* Age: 85 months */
val product5 =
  Product(Time(YearMonth of (2015, 6)))

val item1 = Item(product1)
val item2 = Item(product2)
val item3 = Item(product3)
val item4 = Item(product4)
val item5 = Item(product5)
val order1 = Order(
  Time(YearMonth of (2010, 1)),
  List(item1)
)
val order2 = Order(
  Time(YearMonth of (2011, 1)),
  List(item2)
)
val order3 = Order(
  Time(YearMonth of (2012, 1)),
  List(item3)
)
val order4 = Order(
  Time(YearMonth of (2012, 1)),
  List(item4)
)
val order5 = Order(
  Time(YearMonth of (2012, 1)),
  List(item5)
)
val orders: List[Order] = List(order1, order2, order3, order4, order5)
/*
 * END::Static test data
 */

@main def main(args: String*) =
  val argc = args.length
  val currentTime = Time(YearMonth of (2022, 6))

  /* Check if there's interval input from bash  */
  if argc < 2 then
    println("[ERROR] Not enough arguments")
    sys.exit(1)
  /* Mount list of intervals for products */
  val monthIntervals: List[MonthInterval] =
    if (argc > 2)
      args
        /* Get elements from third to last (First two elements are interval for orders) */
        .tail.tail
        /* Mount list of intervals */
        .foldLeft(List[MonthInterval]())((acc, interval) =>
          acc :+ MonthInterval(interval)
        )
    else
      /* Default list of product intervals in case of no input */
      List(
        MonthInterval("1-3"),
        MonthInterval("4-6"),
        MonthInterval("7-12"),
        MonthInterval(">12")
      )

  /* Sort list of order intervals to make it increasing */
  val interval: List[Time] =
    List(
      Time(YearMonth.of(2010, 1)),
      Time(YearMonth.now())
    ).sortBy(time => Time.totalMonths(time.time))

  val filteredOrders =
    orders
      /* Filter orders inside orders interval */
      .filter(order =>
        (order.placedDate >= interval(0)) && (order.placedDate <= interval(1))
      )
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
          accOrder + order.items
            .foldLeft(0)((accItem, item) => {
              val productAgeInMonths: BigInt =
                Time.totalMonths(item.product.getAge().time)

              if monthInterval.insideInterval(productAgeInMonths) then
                accItem + 1
              else accItem
            })
        })

    println(totalOrders.toString() + " orders")
  })
