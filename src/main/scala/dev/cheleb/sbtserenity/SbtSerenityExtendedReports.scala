package dev.cheleb.sbtserenity

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import net.thucydides.core.reports.ExtendedReport
import scala.collection.JavaConverters._
import sbt.internal.util.ManagedLogger

object SbtSerenityExtendedReports {

  private lazy val extendedReports = getReports()

  /** Returns a list of ExtendedReport instances matching the specified names.
    * The corresponding ExtendedReport classes must be on the classpath.
    */
  def named(reportNames: List[String], log: ManagedLogger) = {

    ensureAllReportsExistForReportNames(reportNames, log)
    extendedReports
      .filter(report => reportNames.contains(report.getName()))

  }

  private def ensureAllReportsExistForReportNames(
      reportNames: List[String],
      log: ManagedLogger
  ) {

    val knownReports = extendedReports

    reportNames.forEach(reportName => {
      if (!knownReports.contains(reportName)) {
        log.warn("No report found on classpath with name " + reportName);
      }
    });
  }

  private def getReports() = {
    ServiceLoader
      .load(classOf[ExtendedReport], getClass().getClassLoader())
      .iterator()
      .asScala
      .toList

  }
}
