package dev.cheleb.sbtserenity

import sbt._
import sbt.Keys._

import SerenityKeys._

import dev.cheleb.sbtserenity.tasks.AggregateTask
import dev.cheleb.sbtserenity.tasks.CleaningTasks

object SerenitySetttings {

  def settings(config: Configuration) = {
    val aggregateTask = new AggregateTask(config)
    val cleaningTasks = new CleaningTasks(config)
    Seq(
      config / aggregateReports := aggregateTask.aggregate.value,
      clearReports := cleaningTasks.cleanReports.value,
      clearHistory := cleaningTasks.clearHistory.value
    )
  }
}
