package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction

object Riddle5 {
  /**
   * Sum up the latest values of [first] and [second] whenever one of the Observables emits a new value.
   *
   * Use case: Two input fields in a calculator that need to be summed up and the result should be updated every time one of the inputs change.
   */
  fun solve(first: Observable<Int>, second: Observable<Int>): Observable<Int> {
    return Observable.combineLatest(first, second, { a, b -> a + b })
  }
}
