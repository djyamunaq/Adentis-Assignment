HEADER

Adentis Assignment
Author: Denis Yamunaque
Program: Organizing orders by age of product
Language: Scala 3


ABOUT THE PROGRAM

The program was developed using scala lang and trying to take profit of the language resources.
Some possible improvements are the better structuration of code in order to make it more robust and 
efficient in time and resources since the compilation and running are not as fast as desired. 
Due to my 2 days knowledge at the language, there's surely lot of space to achieve it with more study and 
preparation.
Other important point is the precision of the time management logic. To make the maths easier, all the times
were converted to seconds using an average of seconds in each structure of time (year, month etc). That is
of low accuracy, since months have different amounts of days and therefore different amounts of seconds, 
what is not perceptible within low range periods, but is more and more perceptible when dealing with larger 
ranges.


HOW TO USE IT

With scala installed and inside src/main/scala folder, type command 

<scala .\Main.scala "start-order-interval" "end-order-interval" "product-interval-1" "product-interval-2" 
    ... "product-interval-n">

in which start-order-interval and end-order-interval are dates in format "yyyy-mm-dd hh:mm:ss" and 
product-interval-x is a month interval for products age in format "mm-mm". Note that product-interval-x is
and optional argument and if not used, a default list of intervals will be used.

For the static data used in program, the ages of the products, from product1 to product5, are 1, 4, 9, 12, 85,
respectively. As mentioned in 'ABOUT THE PROGRAM', the ages may not be as accurate as desirable, since it was
used an average of seconds to do the math. Anyway, it's possible to see how the program works, printing

1-3 months:     1 orders
4-6 months:     1 orders
7-12 months:    2 orders
\>12 months:     1 orders

for the static data with the default list of month intervals for products. 

- 1 order (product1 with age 1 month) in 1-3 months
- 1 order (product2 with age 4 months) in 4-6 months
- 2 order (product3 with age 9 months and product4 with age 12 months) in 7-12 months
- 1 order (product5 with age 85 months) in >12 months

With custom intervals (1-3, 4-5, 8-10) the output is as follows

- 1 order (product1 with age 1 month) in 1-3 months
- 1 order (product2 with age 4 months) in 4-5 months
- 1 order (product5 with age 85 months) in 80-100 months
