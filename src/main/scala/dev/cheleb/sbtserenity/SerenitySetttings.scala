package dev.cheleb.sbtserenity

import sbt._
import sbt.Keys._

import SerenityKeys._

import dev.cheleb.sbtserenity.tasks._

object SerenitySetttings {

  def settings(config: Configuration, projectKey: String) = {
    val aggregateTask = new AggregateTask(config)
    val cleaningTasks = new CleaningTasks(config)
    val reportTasks = new ReportTasks(config, projectKey)
    val historyTasks = new HistoryTasks(config, projectKey)
    Seq(
      config / aggregateReports := aggregateTask.aggregate.value,
      config / clearReports := cleaningTasks.cleanReports.value,
      config / serenityReportTask := reportTasks.serenity.value,
      config / historyReports := historyTasks.historyReports.value,
      config / clearHistory := historyTasks.clearHistory.value
    )
  }
}
