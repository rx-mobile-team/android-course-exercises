package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Maybe

object Riddle29 {
  /**
   * Call the given [function] when the [source] errors.
   *
   * Use case: Add some logging.
   */
  fun solve(source: Maybe<Int>, function: (Throwable) -> Unit): Maybe<Int> {
    // TODO: implement this method
    throw ExerciseNotCompletedException()
  }
}
