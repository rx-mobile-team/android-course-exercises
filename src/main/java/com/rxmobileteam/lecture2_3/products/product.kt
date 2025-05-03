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
  this.sortedWith(
    compareBy<Product> { it.price }.thenBy { it.favoriteCount }
  )

// TODO: Return a set of Products in the orders (The order doesn't matter).
fun List<Order>.getProductsSet(): Set<Product> = this.flatMap { it.products }.toSet()

// TODO: Return a list of Products in the orders, duplicates are allowed.
fun List<Order>.getProductsList(): List<Product> = this.flatMap { it.products }

// TODO: Return a list of delivered orders
fun List<Order>.getDeliveredOrders(): List<Order> = this.filter { it.isDelivered }

// TODO: Return a list of products in the delivered orders
fun List<Order>.getDeliveredProductsList(): List<Product> =
  this.filter { it.isDelivered }.flatMap { it.products }

// TODO: Partition the orders into two lists: "delivered" and "not delivered"
fun List<Order>.partitionDeliveredAndNotDelivered(): Pair<List<Order>, List<Order>> =
  this.partition { it.isDelivered }

// TODO: Return a map of product to count of this product in the orders
// eg. [Product1 -> 2, Product2 -> 1, Product3 -> 3]
fun List<Order>.countOfEachProduct(): Map<Product, Int> =
  this.flatMap { it.products }
    .groupBy { it }
    .mapValues { entry ->
      entry.value.size
    }

// TODO: Return the sum of product prices in the order
fun Order.sumProductPrice(): Double = this.products.sumOf { it.price }

// TODO: Return the product with the maximum price in the order
fun Order.getMaxPriceProduct(): Product =
  this.products.maxBy { it.price }

// TODO: Return the product with the min price in the order
fun Order.getMinPriceProduct(): Product =
  this.products.minBy { it.price }


// Assume you have these lists available to work with in an execution environment:
// val products: List<Product> = ...
// val orders: List<Order> = ...

// --- Combined 40 Kotlin Collections Exercises ---

// TODO 1: Return a list containing only products of the LAPTOP category.
fun List<Product>.getAllLaptops(): List<Product> =
   this.filter { it.category == ProductCategory.LAPTOP }

// TODO 2: Return a list containing only orders that have been delivered (isDelivered = true).

// TODO 3: Return a list containing only the NAMES (String) of all products.
fun List<Product>.getProductNames(): List<String> =
  this.map { it.name }

// TODO 4: Return a list containing only the PRICES (Double) of all products.
fun List<Product>.getProductPrices(): List<Double> =
  this.map { it.price }

// TODO 5: Find the FIRST product with a price lower than `priceThreshold`. Return null if not found.
fun List<Product>.findFirstProductCheaperThan(priceThreshold: Double): Product? =
  this.firstOrNull { it.price < priceThreshold}

// TODO 6: Count the number of products with a price greater than or equal to `priceThreshold`.
fun List<Product>.countProductsMoreExpensiveThan(priceThreshold: Double): Int =
  this.count { it.price >= priceThreshold }

// TODO 7: Check if there is ANY product belonging to the CAMERA category.
fun List<Product>.hasAnyCameras(): Boolean =
  this.any { it.category == ProductCategory.CAMERA }

// TODO 8: Check if ALL products have a `favoriteCount` greater than 0.
fun List<Product>.areAllProductsFavorited(): Boolean =
  this.all { it.favoriteCount > 0 }

// TODO 9: Check if NO products have a price equal to 0.0.
fun List<Product>.hasNoProductsWithZeroPrice(): Boolean =
  this.none { it.price == 0.0 }

// TODO 10: Group all products by category. The result should be a Map<ProductCategory, List<Product>>.
fun List<Product>.groupProductsByCategory(): Map<ProductCategory, List<Product>> =
  this.groupBy { it.category }

// TODO 11: Calculate the sum of the prices of ALL products in the list.
fun List<Product>.sumAllProductPrices(): Double =
  this.sumOf { it.price }

// TODO 12: Calculate the sum of the favorite counts (`favoriteCount`) of ALL products.
fun List<Product>.sumAllFavoriteCounts(): Int =
  this.sumOf { it.favoriteCount }

// TODO 13: Find the product with the highest price. Return null if the list is empty.
fun List<Product>.findMostExpensiveProduct(): Product? =
  this.maxByOrNull { it.price }

// TODO 14: Find the product with the lowest favorite count. Return null if the list is empty.
fun List<Product>.findLeastFavoritedProduct(): Product? =
  this.minByOrNull { it.favoriteCount }

// TODO 15: Return a new list of products sorted by price in ascending order.
fun List<Product>.sortByPriceAscending(): List<Product> =
  this.sortedBy { it.price }

// TODO 16: Return a new list of products sorted by favorite count in descending order.
fun List<Product>.sortByFavoriteCountDescending(): List<Product> =
  this.sortedByDescending { it.favoriteCount }

// TODO 17: From the list of orders, create a SINGLE list containing ALL products from every order.
fun List<Order>.getAllProductsFromAllOrders(): List<Product> =
  this.flatMap { it.products }

// TODO 18: From the list of orders, create a SINGLE list containing all products from UNDELIVERED orders.
fun List<Order>.getProductsFromUndeliveredOrders(): List<Product> =
  this.filter { !it.isDelivered }.flatMap { it.products }

// TODO 19: Partition the list of products into two lists: one for expensive products (price >= `priceThreshold`) and one for the rest.
fun List<Product>.partitionByPrice(priceThreshold: Double): Pair<List<Product>, List<Product>> =
  this.partition { it.price >= priceThreshold }

// TODO 20: Return a list containing the UNIQUE product categories (ProductCategory) present in the product list.
fun List<Product>.getDistinctCategories(): List<ProductCategory> =
  this.map { it.category }.distinct()

// TODO 21: Create a Map<String, Product> where the key is the product ID and the value is the corresponding Product object.
fun List<Product>.associateProductsById(): Map<String, Product> =
  this.associateBy { it.id }

// TODO 22: Create a Map<Product, Double> where the key is the Product object and the value is its price.
fun List<Product>.associateProductsWithPrice(): Map<Product, Double> =
  this.associateWith { it.price }

// TODO 23: Create a list of Pair<String, Double> containing the name and price of each product.
fun List<Product>.zipNamesAndPrices(): List<Pair<String, Double>> =
//  this.map { it.name }.zip(this.map { it.price })
this.map { product -> Pair(product.name, product.price) }

// TODO 24: Group products by category, then count the number of products in each category. Result is Map<ProductCategory, Int>.
fun List<Product>.countProductsPerCategory(): Map<ProductCategory, Int> =
  this.groupBy { it.category }.mapValues { it.value.size }

// TODO 25: Using fold (or another method), calculate the total price of products in the FIRST order of the `orders` list. Assume the `orders` list is not empty.
fun List<Order>.sumProductPricesInFirstOrder(): Double =
  this.first().products.fold(0.0) { newBill, product ->
    newBill + product.price
  }

// TODO 26: Calculate the average price of products belonging to the given 'category'.
// Return 0.0 if no products are found in that category.
fun List<Product>.averagePriceInCategory(category: ProductCategory): Double {
  val productsInCategory = this.filter { it.category == category }
  // Check if the list is empty to avoid division by zero
  return if (productsInCategory.isNotEmpty()) {
    productsInCategory.sumOf { it.price } / productsInCategory.size
  } else {
    0.0 // Return 0.0 if no products are found in that category
  }
}
// TODO 27: Create a Map where keys are Order IDs and values are the NUMBER of products in that order.
fun List<Order>.mapOrderIdToProductCount(): Map<String, Int> =
  this.associate { it.id to it.products.size }

// TODO 28: Find the FIRST product that is a PHONE and costs less than 800.0.
// Return null if no such product exists.
fun List<Product>.findFirstCheapPhone(): Product? =
  this.firstOrNull { it.category == ProductCategory.PHONE && it.price < 800.0 }

// TODO 29: Given two lists of orders (orders1, orders2), return a Set containing all unique product IDs
// found in any product list within orders of EITHER list1 OR list2.
fun findUnionOfProductIds(orders1: List<Order>, orders2: List<Order>): Set<String> =
  (orders1 + orders2).flatMap { it.products }.map { it.id }.toSet()

// TODO 30: Given two lists of products (list1, list2), return a Set of Products that exist in BOTH list1 AND list2.
// Equality should be based on Product ID.
fun findCommonProducts(list1: List<Product>, list2: List<Product>): Set<Product> =
  list1.filter { it.id in list2.map { it.id }.toSet() }.toSet()

// TODO 31: Group the list of orders into a Map where the key is a Boolean (true for delivered, false for not delivered)
// and the value is the list of orders with that delivery status.
fun List<Order>.groupByDeliveryStatus(): Map<Boolean, List<Order>> =
  this.groupBy { it.isDelivered }

// TODO 32: Return a list containing the top 3 most expensive products.
// The result should be sorted by price descending. If there are fewer than 3 products, return all of them sorted.
fun List<Product>.getTop3MostExpensive(): List<Product> =
  this.sortedByDescending { it.price }.take(3)

// TODO 33: Check if there are any products with the same 'id' in this list. Return true if duplicates exist, false otherwise.
fun List<Product>.hasDuplicateIds(): Boolean =
  !this.groupBy { it.id }.takeIf { it.values.size > 1 }.isNullOrEmpty()
//this.size != this.map { it.id }.toSet().size
//this.groupBy { it.id }.any { it.value.size > 1 }


// TODO 34: Given a Map<ProductCategory, List<Product>> (e.g., from groupBy),
// create a new Map where keys are categories and values are the MOST EXPENSIVE product in that category.
// If a category has no products (empty list), omit it from the result map.
fun Map<ProductCategory, List<Product>>.findMostExpensiveProductPerCategory(): Map<ProductCategory, Product> =
  this.mapValues { it.value.maxBy { it.price } }

// TODO 35: Get a list of the NAMES of all products belonging to orders that ARE delivered (isDelivered = true).
fun List<Order>.getNamesOfProductsInDeliveredOrders(): List<String> =
  this.filter { it.isDelivered }.flatMap { it.products }.map { it.name }

// TODO 36: Calculate the total number of products AND the total sum of their prices across ALL orders.
// Return this as a Pair<Int, Double> (totalCount, totalValue).
fun List<Order>.getTotalProductCountAndValue(): Pair<Int, Double> =
  this.fold(Pair(0, 0.0)) { acc, order ->
    Pair(acc.first + order.products.size, acc.second + order.products.sumOf { it.price } )
  }
//val allProducts = this.flatMap { it.products }
//    val totalCount = allProducts.size
//    val totalValue = allProducts.sumOf { it.price }
//    return Pair(totalCount, totalValue)


// TODO 37: Return a sub-list containing products from index 5 up to index 10 (inclusive).
// Handle cases where the list size is smaller than required gracefully (return elements available within the range).
fun List<Product>.getProductsInRange5to10(): List<Product> =
  this.slice(5..10)

// TODO 38: Using a Sequence, find the first product with favoriteCount > 500.
// Return null if no such product exists.
fun List<Product>.findFirstHighlyFavoritedProductUsingSequence(): Product? =
  this.asSequence() // Convert to Sequence FIRST
    .filter { it.favoriteCount > 500 }
    .firstOrNull() // Terminal operation on the sequence

// TODO 39: Divide the list of products into chunks of 5. For each chunk, calculate the average price.
// Return a list of these average prices (List<Double>). The last chunk may have fewer than 5 elements.
fun List<Product>.calculateAveragePriceInChunksOf5(): List<Double> =
  this.chunked(5).map { chunk ->
    if (chunk.isNotEmpty()) {
      chunk.sumOf { it.price } / chunk.size
    } else {
      0.0
    }
  }

// TODO 40: Sort the list of orders. Orders should be sorted primarily by delivery status (undelivered first, so isDelivered=false comes before isDelivered=true).
// Within the same delivery status, orders should be sorted by the number of products they contain, in descending order.
fun List<Order>.sortByDeliveryStatusAndProductCount(): List<Order> =
  this.sortedWith(
    compareBy<Order> { !it.isDelivered } // false (undelivered) comes first
      .thenByDescending { it.products.size } // Then by product count descending
  )


