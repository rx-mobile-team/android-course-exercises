package com.rxmobileteam.lecture2_3.products

import com.rxmobileteam.utils.ExerciseNotCompletedException

enum class ProductCategory {
  LAPTOP,
  PHONE,
  HEADPHONES,
  SMART_WATCH,
  CAMERA,
}

data class Product(
  val id: String,
  val name: String,
  val price: Double,
  val category: ProductCategory,
  val favoriteCount: Int,
)

data class Order(
  val id: String,
  val products: List<Product>,
  val isDelivered: Boolean,
)

// TODO: Return a list of Product, sorted in the ascending by price. if prices are equal, sorted by favoriteCount descending
fun List<Product>.sortedByPriceAscendingThenByFavoriteCountDescending(): List<Product> =
  throw ExerciseNotCompletedException()

// TODO: Return a set of Products in the orders (The order doesn't matter).
fun List<Order>.getProductsSet(): Set<Product> = throw ExerciseNotCompletedException()

// TODO: Return a list of Products in the orders, duplicates are allowed.
fun List<Order>.getProductsList(): List<Product> = throw ExerciseNotCompletedException()

// TODO: Return a list of delivered orders
fun List<Order>.getDeliveredOrders(): List<Order> = throw ExerciseNotCompletedException()

// TODO: Return a list of products in the delivered orders
fun List<Order>.getDeliveredProductsList(): List<Product> = throw ExerciseNotCompletedException()

// TODO: Partition the orders into two lists: "delivered" and "not delivered"
fun List<Order>.partitionDeliveredAndNotDelivered(): Pair<List<Order>, List<Order>> =
  throw ExerciseNotCompletedException()

// TODO: Return a map of product to count of this product in the orders
// eg. [Product1 -> 2, Product2 -> 1, Product3 -> 3]
fun List<Order>.countOfEachProduct(): Map<Product, Int> = throw ExerciseNotCompletedException()

// TODO: Return the sum of product prices in the order
fun Order.sumProductPrice(): Double = throw ExerciseNotCompletedException()

// TODO: Return the product with the maximum price in the order
fun Order.getMaxPriceProduct(): Product = throw ExerciseNotCompletedException()

// TODO: Return the product with the min price in the order
fun Order.getMinPriceProduct(): Product = throw ExerciseNotCompletedException()

