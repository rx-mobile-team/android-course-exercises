package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Observable

object Riddle19 {
  /**
   * Use the given [Interaction] interface and use its listener to transform the [Int] callback to an Observable that emits every time the listener is called.
   * When the Observable is being disposed the listener should be set to null.
   *
   * Use case: Transform any listener into an Observable.
   */
  fun solve(interaction: Interaction): Observable<Int> {
    // TODO: implement this method
    throw ExerciseNotCompletedException()
  }

  interface Interaction {
    var listener: ((Int) -> Unit)?
  }
}
