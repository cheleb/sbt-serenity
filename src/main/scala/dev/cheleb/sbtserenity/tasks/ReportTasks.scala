package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._
import net.thucydides.core.reports.ExtendedReports

import scala.collection.JavaConverters._
import net.thucydides.model.reports.ResultChecker
import java.nio.file.Files
import java.nio.file.Path
import java.util.Locale
import net.thucydides.model.environment.SystemEnvironmentVariables
import net.thucydides.model.ThucydidesSystemProperty
import net.serenitybdd.core.{Serenity => JSerenity}
import net.thucydides.model.domain.TestResult
import net.thucydides.core.reports.html.HtmlAggregateStoryReporter
import com.google.common.base.Splitter
import sbt.internal.util.ManagedLogger
import java.util.ServiceLoader
import net.thucydides.core.reports.ExtendedReport
import dev.cheleb.sbtserenity.SbtSerenityExtendedReports

class ReportTasks(config: Configuration, projectKey: String)
    extends SerenityTask(projectKey) {

  val requirementsBaseDir = "src/test/resources"

  val ignoreFailedTests = false

  def serenity = Def.task {

    val projectDirectory = baseDirectory.value
    def info(msg: String) = streams.value.log.info(msg)
    def warn(msg: String) = streams.value.log.warn(msg)

    prepareExecution(projectDirectory, target.value)

    info(s"Generating Serenity reports for project: $projectKey")

    info(s"Output directory: ${outputDirectory}")

    val testResult = generateHtmlStoryReports(projectDirectory.toPath())
    generateExtraReports(
      projectDirectory,
      "single-page-html,navigator",
      streams.value.log
    );
//    generateCustomReports();
    if (ignoreFailedTests) {
      warn("Ignoring failed tests in the Serenity test suite")
    } else
      testResult match {
        case TestResult.ERROR =>
          warn(
            "An error occurred in the Serenity tests"
          )
        case TestResult.FAILURE =>
          warn(
            "A failure occurred in the Serenity tests"
          )
        case TestResult.COMPROMISED =>
          warn(
            "There were compromised tests in the Serenity test suite"
          )
        case TestResult.SUCCESS =>
          info(
            "All tests in the Serenity test suite passed"
          )
        case unknown => warn(s"Unknown test result  $unknown")
      }
  }

  private def generateHtmlStoryReports(
      projectDirectory: Path
  ): TestResult = {

    val reporter = new HtmlAggregateStoryReporter(projectKey)
    reporter.setProjectDirectory(projectDirectory.toFile().getPath());
    reporter.setSourceDirectory(sourceDirectory);
    reporter.setOutputDirectory(outputDirectory);
    reporter.setIssueTrackerUrl(issueTrackerUrl);
    reporter.setJiraUrl(jiraUrl);
    reporter.setJiraProject(jiraProject);
    reporter.setJiraUsername(jiraUsername);
    reporter.setJiraPassword(jiraPassword);
    reporter.setTags(tags);
    reporter.setGenerateTestOutcomeReports();
    val outcomes =
      reporter.generateReportsForTestResultsFrom(sourceDirectory);
    return new ResultChecker(outputDirectory).checkTestResults(outcomes);
  }

  private def generateExtraReports(
      projectDirectory: File,
      reports: String,
      log: ManagedLogger
  ) =
    if (reports.nonEmpty) {
      val extendedReportTypes = Splitter.on(",").splitToList(reports);
      log.info(
        s"Generating extended reports: ${extendedReportTypes.asScala.mkString(", ")}"
      )
      SbtSerenityExtendedReports
        .named(extendedReportTypes, log)
        .foreach(report => {
          report.setProjectDirectory(projectDirectory.getPath());
          report.setSourceDirectory(sourceDirectory.toPath());
          report.setOutputDirectory(outputDirectory.toPath());
          val generatedReport = report.generateReport();
          log.info(
            s"Generated report: ${report.getDescription}  ${generatedReport.toUri()}"
          )
        })
    }

  private def prepareExecution(baseDirectory: File, target: File) = {
//    updateLayoutPaths(baseDirectory, target)
    configureEnvironmentVariables()

  }

  private def configureEnvironmentVariables() {

    Locale.setDefault(Locale.ENGLISH)

    // TODO override environment variables
    updateSystemProperty(
      ThucydidesSystemProperty.SERENITY_PROJECT_KEY.getPropertyName(),
      projectKey
    );
    updateSystemProperty(
      ThucydidesSystemProperty.SERENITY_TEST_REQUIREMENTS_BASEDIR.toString(),
      requirementsBaseDir
    );

  }

  private def getEnvironmentVariables() =
    SystemEnvironmentVariables.currentEnvironmentVariables()

  private def updateSystemProperty(propertyName: String, value: String) {

    getEnvironmentVariables()
      .setProperty(propertyName, value)
  }
}
