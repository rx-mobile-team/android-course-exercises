package com.rxmobileteam.lecture6

import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.*

private val count = AtomicInteger()

suspend fun maybeFailedFunction(): Int {
  delay(100)

  if (count.incrementAndGet() % 2 == 0) {
    throw RuntimeException("Failed")
  } else {
    return 42
  }
}

class Logger {
  fun log(message: String) {
    println(message)
  }

  fun logError(throwable: Throwable, message: String) {
    println("$message: $throwable")
  }
}

class DemoModel(
  private val logger: Logger = Logger(),
) {
  val scope = CoroutineScope(Dispatchers.Default + Job())

  fun doSomething() {

    scope.launch {
      maybeFailedFunction()
      try {
          logger.log("${maybeFailedFunction()}")
      } catch (cancellable: CancellationException) {
        throw cancellable
      } catch (e: Exception) {
        logger.logError(e, "Error")
      }
    }
  }

  fun cancelAndJoinBlocking() {
    runBlocking {
      scope.coroutineContext.job.cancelAndJoin()
    }
    logger.log("Cancelled")
  }
}

fun main() = runBlocking {
  val model = DemoModel()

  model.doSomething()
  delay(100)
  model.cancelAndJoinBlocking()

  delay(5_000)
  // At this point, the coroutine should have been cancelled
  // Nothing should be printed to the console
}
