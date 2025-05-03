package com.rxmobileteam.lecture6

import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select

@JvmInline
value class UserId(val id: Int)

data class User(
  val id: UserId,
  val name: String,
)

data class UserDetails(
  val id: UserId,
  val email: String,
  val phone: String,
)

data class Post(
  val id: String,
  val title: String,
  val body: String,
  val userId: UserId,
)

data class UserAndPosts(
  val user: User,
  val posts: List<Post>,
)

data class UserAndDetails(
  val user: User,
  val details: UserDetails,
)

interface UserRepository {
  /**
   * Find a user by id.
   * @return the user if found, null otherwise.
   */
  suspend fun findUserById(id: UserId): User?

  /**
   * Find all posts by user id.
   * @return the list of posts if found, empty list otherwise.
   */
  suspend fun getPostsByUserId(id: UserId): List<Post>

  /**
   * Find a user by id and all posts by that user.
   * @return the user and all posts if found, null otherwise.
   */
  suspend fun findUserAndPostsById(id: UserId): UserAndPosts?

  /**
   * Find a user by id and all details by that user.
   * @return the user and all details if found, null otherwise.
   */
  suspend fun findUserAndUserDetailsById(id: UserId): UserAndDetails?
}

interface UserApi {
  suspend fun findUserById(id: UserId): User?
  suspend fun findDetailsByUser(id: UserId): UserDetails?
  suspend fun getPostsByUser(user: User): List<Post>
}

// ------------------------------------------------------------------------------------------

internal class RealUserRepository(
  private val userApi: UserApi,
  private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {
  override suspend fun findUserById(id: UserId): User? {
    // Call userApi's methods on ioDispatcher
    return withContext(ioDispatcher) {
      userApi.findUserById(id)
    }
  }

  override suspend fun getPostsByUserId(id: UserId): List<Post> {
    // Call userApi's methods on ioDispatcher
    val user = findUserById(id) ?: return listOf()
    return withContext(ioDispatcher) {
      userApi.getPostsByUser(user)
    }
  }

  override suspend fun findUserAndPostsById(id: UserId): UserAndPosts? {
    // Call userApi's methods on ioDispatcher
    val user = findUserById(id) ?: return null
    return withContext(ioDispatcher) {
      val posts = userApi.getPostsByUser(user)
      UserAndPosts(user, posts)
    }
  }

  override suspend fun findUserAndUserDetailsById(id: UserId): UserAndDetails? {
    // Call concurrently userApi's methods on ioDispatcher
    return withContext(ioDispatcher) {
      val userDeferred = async {
        userApi.findUserById(id)
      }
      val userDetailsDeferred = async {
        userApi.findDetailsByUser(id)
      }
//      // --- await seq ----
//
//      val user: User = userDeferred.await() ?: return@withContext null
//      val userDetails: UserDetails = userDetailsDeferred.await() ?: return@withContext null
//      UserAndDetails(user,userDetails)

      // --- await parallel ----
      val firstResult: FirstResult = select {
        userDeferred.onAwait { user ->
          if (user == null) {
            userDetailsDeferred.cancel("User returned null, cancelling details.")
            FirstResult.FirstFailed
          } else {
            FirstResult.UserFirst(user)
          }
        }
        userDetailsDeferred.onAwait { details ->
          if (details == null) {
            userDeferred.cancel("Details returned null, cancelling user.")
            FirstResult.FirstFailed
          } else {
            FirstResult.DetailsFirst(details)
          }
        }
      }

      when (firstResult) {
        is FirstResult.FirstFailed -> {
          null
        }
        is FirstResult.UserFirst -> {
          val details = userDetailsDeferred.await() ?: return@withContext null
          UserAndDetails(firstResult.user, details)
        }
        is FirstResult.DetailsFirst -> {
          val userValue = userDeferred.await() ?: return@withContext null
          UserAndDetails(userValue, firstResult.details)
        }
      }
    }
  }
}
sealed interface FirstResult {
  object FirstFailed : FirstResult
  data class UserFirst(val user: User) : FirstResult
  data class DetailsFirst(val details: UserDetails) : FirstResult
}

// ------------------------------------------------------------------------------------------

fun provideUserRepository(): UserRepository = RealUserRepository(
  userApi = object : UserApi {
    val users = listOf(
      User(UserId(1), "Leanne Graham"),
      User(UserId(2), "Ervin Howell"),
      User(UserId(3), "Clementine Bauch"),
      User(UserId(4), "Patricia Lebsack"),
      User(UserId(5), "Chelsey Dietrich"),
    )
    val userDetails = users.map {
      UserDetails(
        id = it.id,
        email = "${it.name.replace(" ", ".").lowercase()}@gmail.com",
        phone = "+1-770-736-8031",
      )
    }
    val posts = users.map { user ->
      List(10) {
        Post(
          id = UUID.randomUUID().toString(),
          title = "Title #${it} of ${user.name}",
          body = "Body #${it} of ${user.name}",
          userId = user.id,
        )
      }
    }

    override suspend fun findUserById(id: UserId): User? = users.find { it.id == id }.also { delay(500) }

    override suspend fun findDetailsByUser(id: UserId): UserDetails? =
      userDetails.find { it.id == id }.also { delay(500) }

    override suspend fun getPostsByUser(user: User): List<Post> =
      posts.find { it.firstOrNull()?.userId == user.id }.orEmpty().also { delay(500) }
  },
  ioDispatcher = Dispatchers.IO,
)


fun main() = runBlocking {
  val userRepository = provideUserRepository()

  println("findUserById")
  userRepository.findUserById(UserId(1))
  userRepository.findUserById(UserId(100))
  println("-".repeat(80))

  println("getPostsByUserId")
  userRepository.getPostsByUserId(UserId(1))
  userRepository.getPostsByUserId(UserId(100))
  println("-".repeat(80))

  println("findUserAndPostsById")
  userRepository.findUserAndPostsById(UserId(1))
  userRepository.findUserAndPostsById(UserId(100))
  println("-".repeat(80))

  println("findUserAndUserDetailsById")
  userRepository.findUserAndUserDetailsById(UserId(1))
  userRepository.findUserAndUserDetailsById(UserId(100))
  println("-".repeat(80))
}
