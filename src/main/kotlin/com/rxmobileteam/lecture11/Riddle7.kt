package com.rxmobileteam.lecture11

import com.rxmobileteam.utils.ExerciseNotCompletedException
import io.reactivex.rxjava3.core.Observable

object Riddle7 {
  /**
   * When the [source] emits the same value multiple times, only allow the first value to travel downstream.
   *
   * Use case: You never want to show the same value twice.
   */
  fun solve(source: Observable<Int>): Observable<Int> {
    // TODO: implement this method
    throw ExerciseNotCompletedException()
  }
}
