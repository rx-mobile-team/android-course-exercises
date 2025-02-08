package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object Riddle32 {
  /**
   * Signal a [TimeoutException] when the given [source] does not terminate within 3 seconds.
   *
   * Use case: You want to terminate the given reactive type and stop the operation.
   */
  fun solve(source: Single<Long>): Single<Long> {
    return source.timeout(3, TimeUnit.SECONDS)
  }
}
