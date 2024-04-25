package dev.cheleb.sbtserenity

import sbt._
import dev.cheleb.sbtserenity.tasks.SerenityTasks

object SerenityKeys {

  // Configuration keys
  val Serenity = config("serenity") extend Test

  // Task keys

  val serenityTasks = taskKey[SerenityTasks]("Serenity sbt tasks")
  val serenityReportTask = taskKey[Unit]("Serenity sbt report task")
  val clearReports =
    taskKey[Unit]("Serenity sbt task to delete report directory")
  val historyReports = taskKey[Unit]("Serenity sbt task to create history")

  val aggregateReports = taskKey[Unit]("Aggregate Serenity reports")

  val clearHistory = taskKey[Unit]("Serenity sbt task to delete history")

}
