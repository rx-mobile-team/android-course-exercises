package com.rxmobileteam.lecture2_3.fraction

import com.rxmobileteam.utils.ExerciseNotCompletedException
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class Fraction private constructor(
  val numerator: Int,
  val denominator: Int,
) : Comparable<Fraction> {
  val decimal: Double = numerator.toDouble() / denominator.toDouble()

  init {
    if (denominator == 0) throw ExerciseNotCompletedException()
  }

  //region unary operators
  operator fun unaryPlus(): Fraction = Fraction(numerator, denominator)

  //
  operator fun unaryMinus(): Fraction = Fraction(-numerator, denominator)
  //endregion

  //region plus operators
  operator fun plus(other: Fraction): Fraction = Fraction(
    numerator = this.numerator * other.denominator + other.numerator * this.denominator,
    denominator = this.denominator * other.denominator
  )

  operator fun plus(other: Int): Fraction = Fraction(
    numerator = this.numerator + other * this.denominator,
    denominator = this.denominator
  )
  //endregion

  //region times operators
  operator fun times(other: Fraction): Fraction = Fraction(
    numerator = this.numerator * other.numerator,
    denominator = this.denominator * other.denominator
  )

  operator fun times(number: Int): Fraction = Fraction(this.numerator * number, this.denominator)
  //endregion

  override fun compareTo(other: Fraction): Int {
    if (this.denominator == other.denominator) return this.numerator.compareTo(other.numerator)
    else {
      val firstNumerator = this.numerator * other.denominator
      val secondNumerator = other.numerator * this.denominator
      return firstNumerator.compareTo(secondNumerator)
    }
  }

  //region toString, hashCode, equals, copy
  override fun toString(): String = "$numerator/$denominator"

  // TODO: hashCode should be implemented
  override fun hashCode(): Int = listOf(numerator, denominator).hashCode()

  override fun equals(other: Any?): Boolean = this.toString() == other.toString()

  fun copy(
    numerator: Int = this.numerator,
    denominator: Int = this.denominator
  ): Fraction = Fraction(numerator, denominator)
  //endregion

  companion object {
    @JvmStatic
    fun ofInt(number: Int): Fraction = Fraction(number, 1)

    @JvmStatic
    fun of(numerator: Int, denominator: Int): Fraction {
      if (denominator == 0) throw ExerciseNotCompletedException()
      val gcd = gcd(numerator, denominator)
      return Fraction(numerator / gcd, denominator / gcd)
    }

    private fun gcd(first: Int, second: Int): Int {
      var a = first
      var b = second
      while (b > 0) {
        val temp = b
        b = a % b
        a = temp
      }
      return a
    }
  }
}

infix fun Int.over(denominator: Int): Fraction = Fraction.of(this, denominator)

//region get extensions
operator fun Fraction.component1(): Int = this.numerator

operator fun Fraction.component2(): Int = this.denominator

operator fun Fraction.get(index: Int): Int {
  return when (index) {
    0 -> this.numerator
    1 -> this.denominator
    else -> throw IndexOutOfBoundsException()
  }
}
//endregion

//region to number extensions
fun Fraction.roundToInt(): Int = this.decimal.roundToInt()

fun Fraction.roundToLong(): Long = this.decimal.roundToLong()

fun Fraction.toFloat(): Float = this.decimal.toFloat()

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
