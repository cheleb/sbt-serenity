package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._
import net.serenitybdd.model.history.TestOutcomeSummaryRecorder
import net.serenitybdd.model.history.FileSystemTestOutcomeSummaryRecorder
import java.nio.file.Paths
import org.apache.commons.io.FileUtils
import java.nio.file.Files

class HistoryTasks(config: Configuration, projectKey: String)
    extends SerenityTask(projectKey) {

  private val defaultHistoryDirectory = "history"

  def historyDirectory = serenityConfiguration.getHistoryDirectory()

  def historyReports = Def.task {

    streams.value.log.info("Creating history reports")

    val outputPath = baseDirectory.value.toPath.resolve(defaultHistoryDirectory)

    Files.createDirectories(outputPath)

    getTestOutcomeSummaryRecorder.recordOutcomeSummariesFrom(
      serenityConfiguration.getOutputDirectory().toPath
    )

  }

  def clearHistory = Def.task {
    streams.value.log.info("Cleaning serenity report history.")

    FileUtils.deleteDirectory(historyDirectory);

  }

  private def getTestOutcomeSummaryRecorder: TestOutcomeSummaryRecorder = {

    new FileSystemTestOutcomeSummaryRecorder(
      historyDirectory.toPath,
      false
    )
  }
}
