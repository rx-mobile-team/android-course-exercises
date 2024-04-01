package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Completable

object Riddle28 {
  /**
   * Call the given [function] when the [source] completes.
   *
   * Use case: Add some logging.
   */
  fun solve(source: Completable, function: () -> Unit): Completable {
    // TODO: implement this method
    throw ExerciseNotCompletedException()
  }
}
