package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._
import net.thucydides.core.reports.ExtendedReports

import scala.collection.JavaConverters._
import net.thucydides.model.reports.ResultChecker
import java.nio.file.Files

class ReportTasks(config: Configuration, projectKey: String)
    extends SerenityTask(projectKey) {

  def getTestRoot(): Option[String] = None
  def getRequirementsBaseDir(): Option[String] = None

  def getReports(): List[String] = List("epic", "feature")

  def serenity = Def.task {
    updateLayoutPaths(baseDirectory.value, target.value)
    val reportDirectory = (target.value / "serenity" / "reports").toPath

    Files.createDirectories(reportDirectory)

    streams.value.log.info(
      "Generating Additional Serenity Reports for ${getProjectKey().get()} to directory $reportDirectory"
    )
    System.setProperty("serenity.project.key", projectKey)
    getTestRoot().foreach { root =>
      System.setProperty("serenity.test.root", root)
    }
    getRequirementsBaseDir().foreach(dir =>
      System.setProperty("serenity.test.requirements.basedir", dir)
    )
    val extendedReportTypes = getReports()
    for (
      report <- ExtendedReports.named(extendedReportTypes.toList.asJava).asScala
    ) {

      report.setSourceDirectory(reportDirectory)
      report.setOutputDirectory(reportDirectory)
      // URI reportPath = absolutePathOf(report.generateReport()).toUri()
      // logger.lifecycle("  - ${report.description}: ${reportPath}")
    }

    val resultChecker = new ResultChecker(reportDirectory.toFile())
    resultChecker.checkTestResults()
    ()
  }

}
