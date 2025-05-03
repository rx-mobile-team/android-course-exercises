package com.rxmobileteam.lecture2_3.fraction

import com.rxmobileteam.utils.ExerciseNotCompletedException

class Fraction private constructor(
  val numerator: Int,
  val denominator: Int,
) : Comparable<Fraction> {
  // TODO: Implement the decimal value of the fraction
  val decimal: Double
    get() = (numerator / denominator).toDouble()

  init {
    require(denominator == 0) {
      "Denominator does not accept value 0"
    }

  }

  fun reduceIfNeed(): Fraction {
    val gcd = gcd(this.numerator, this.denominator)
    if (gcd == 1) return this
    return Fraction(this.numerator / gcd, this.denominator / gcd)
  }

  private fun gcd(a: Int, b: Int): Int {
    return if (b == 0) a else gcd(b, a % b)
  }

  //region unary operators
  // TODO: "+fraction" operator
  operator fun unaryPlus(): Fraction = this.copy()

  // TODO: "-fraction" operator
  operator fun unaryMinus(): Fraction = this.copy(-numerator)
  //endregion

  //region plus operators
  // TODO: "fraction+fraction" operator
  operator fun plus(other: Fraction): Fraction = Fraction(
    numerator * other.denominator + denominator * other.numerator, denominator * other.denominator
  ).reduceIfNeed()

  //endregion
  // TODO: "fraction+number" operator
  operator fun plus(number: Int): Fraction = Fraction(
    numerator + number * denominator, denominator
  ).reduceIfNeed()

  //region times operators
  // TODO: "fraction*fraction" operator
  operator fun times(other: Fraction): Fraction =
    Fraction(numerator * other.numerator, denominator * other.denominator).reduceIfNeed()

  // TODO: "fraction*number" operator
  operator fun times(number: Int): Fraction = Fraction(numerator * number, denominator).reduceIfNeed()

  //endregion
  // TODO: Compare two fractions
  override fun compareTo(other: Fraction): Int {
    return when {
      decimal == other.decimal -> 0
      decimal > other.decimal -> 1
      else -> -1
    }
  }

  //region toString, hashCode, equals, copy
  // TODO: Format the fraction as a string (e.g. "1/2")
  override fun toString(): String = "$numerator/${denominator}"

  // TODO: Implement hashCode
  override fun hashCode(): Int {
    var result: Int = numerator.hashCode()
    result = 31 * result + denominator.hashCode()
    return result
  }

  // TODO: Implement equals
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val fraction = other as Fraction
    if (compareValues(numerator, fraction.numerator) != 0) return false
    return compareValues(numerator, fraction.numerator) == 0
  }

  // TODO: Implement copy
  fun copy(
    numerator: Int = this.numerator, denominator: Int = this.denominator
  ): Fraction = Fraction(numerator, denominator)

  companion object {
    @JvmStatic
    fun ofInt(number: Int): Fraction = Fraction(number, 1)

    @JvmStatic
    fun of(numerator: Int, denominator: Int): Fraction {
      // TODO: Check validity of numerator and denominator
      // TODO: Simplify fraction using the greatest common divisor
      // TODO: Finally, return the fraction with the correct values
      return Fraction(numerator, denominator).reduceIfNeed()
    }
  }
}

// TODO: return a Fraction representing "this/denominator"
infix fun Int.over(denominator: Int): Fraction = Fraction.of(this, denominator)

//region get extensions
// TODO: get the numerator, eg. "val (numerator) = Fraction.of(1, 2)"
operator fun Fraction.component1(): Int = this.numerator

// TODO: get the denominator, eg. "val (_, denominator) = Fraction.of(1, 2)"
operator fun Fraction.component2(): Int = this.denominator

// TODO: get the decimal, index must be 0 or 1
// TODO: eg. "val numerator = Fraction.of(1, 2)[0]"
// TODO: eg. "val denominator = Fraction.of(1, 2)[1]"
// TODO: eg. "val denominator = Fraction.of(1, 2)[2]" should throw an exception
operator fun Fraction.get(index: Int): Int {
  require(index < 2)
  return if (index == 0) this.numerator else this.denominator
}
//endregion

//region to number extensions
// TODO: round to the nearest integer
fun Fraction.roundToInt(): Int = this.decimal.toInt()

// TODO: round to the nearest long
fun Fraction.roundToLong(): Long = this.decimal.toLong()

// TODO: return the decimal value as a float
fun Fraction.toFloat(): Float = this.decimal.toFloat()

// TODO: return the decimal value as a double
fun Fraction.toDouble(): Double = this.decimal
//endregion

fun main() {
  // Creation
  println("1/2: ${Fraction.of(1, 2)}")
  println("2/3: ${Fraction.of(2, 3)}")
  println("8: ${Fraction.ofInt(8)}")
  println("2/4: ${2 over 4}")
  println("-".repeat(80))

  // Unary operators
  println("+2/4: ${+Fraction.of(2, 4)}")
  println("-2/6: ${-Fraction.of(2, 6)}")
  println("-".repeat(80))

  // Plus operators
  println("1/2 + 2/3: ${Fraction.of(1, 2) + Fraction.of(2, 3)}")
  println("1/2 + 1: ${Fraction.of(1, 2) + 1}")
  println("-".repeat(80))

  // Times operators
  println("1/2 * 2/3: ${Fraction.of(1, 2) * Fraction.of(2, 3)}")
  println("1/2 * 2: ${Fraction.of(1, 2) * 2}")
  println("-".repeat(80))

  // compareTo
  println("3/2 > 2/2: ${Fraction.of(3, 2) > Fraction.of(2, 2)}")
  println("1/2 <= 2/4: ${Fraction.of(1, 2) <= Fraction.of(2, 4)}")
  println("4/6 >= 2/3: ${Fraction.of(4, 6) >= Fraction.of(2, 3)}")
  println("-".repeat(80))

  // hashCode
  println("hashCode 1/2 == 2/4: ${Fraction.of(1, 2).hashCode() == Fraction.of(2, 4).hashCode()}")
  println("hashCode 1/2 == 1/2: ${Fraction.of(1, 2).hashCode() == Fraction.of(1, 2).hashCode()}")
  println("hashCode 1/3 == 3/5: ${Fraction.of(1, 3).hashCode() == Fraction.of(3, 5).hashCode()}")
  println("-".repeat(80))

  // equals
  println("1/2 == 2/4: ${Fraction.of(1, 2) == Fraction.of(2, 4)}")
  println("1/2 == 1/2: ${Fraction.of(1, 2) == Fraction.of(1, 2)}")
  println("1/3 == 3/5: ${Fraction.of(1, 3) == Fraction.of(3, 5)}")
  println("-".repeat(80))

  // Copy
  println("Copy 1/2: ${Fraction.of(1, 2).copy()}")
  println("Copy 1/2 with numerator 2: ${Fraction.of(1, 2).copy(numerator = 2)}")
  println("Copy 1/2 with denominator 3: ${Fraction.of(1, 2).copy(denominator = 3)}")
  println("Copy 1/2 with numerator 2 and denominator 3: ${Fraction.of(1, 2).copy(numerator = 2, denominator = 3)}")
  println("-".repeat(80))

  // Component1, Component2 operators
  val (numerator, denominator) = Fraction.of(1, 2)
  println("Component1, Component2 of 1/2: $numerator, $denominator")
  val (numerator2, _) = Fraction.of(10, 30)
  println("Component1 of 10/30: $numerator2")
  val (_, denominator2) = Fraction.of(10, 79)
  println("Component2 of 10/79: $denominator2")
  println("-".repeat(80))

  // Get operator
  println("Get 0 of 1/2: ${Fraction.of(1, 2)[0]}")
  println("Get 1 of 1/2: ${Fraction.of(1, 2)[1]}")
  println("Get 2 of 1/2: ${runCatching { Fraction.of(1, 2)[2] }}") // Should print "Failure(...)"
  println("-".repeat(80))

  // toInt, toLong, toFloat, toDouble
  println("toInt 1/2: ${Fraction.of(1, 2).roundToInt()}")
  println("toLong 1/2: ${Fraction.of(1, 2).roundToLong()}")
  println("toFloat 1/2: ${Fraction.of(1, 2).toFloat()}")
  println("toDouble 1/2: ${Fraction.of(1, 2).toDouble()}")
  println("-".repeat(80))

  // Range
  // Because we implemented Comparable<Fraction>, we can use Fraction in ranges
  val range = Fraction.of(1, 2)..Fraction.of(2, 3)
  println("1/2 in range 1/2..2/3: ${Fraction.of(1, 2) in range}") // "in" operator is "contains"
  println("2/3 in range 1/2..2/3: ${Fraction.of(2, 3) in range}")
  println("7/12 in range 1/2..2/3: ${Fraction.of(7, 12) in range}")
  println("-".repeat(80))
}
