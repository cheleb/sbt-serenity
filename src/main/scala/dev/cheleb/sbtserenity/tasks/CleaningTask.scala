package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._
import net.serenitybdd.core.di.SerenityInfrastructure
import org.apache.commons.io.FileUtils

class CleaningTasks(config: Configuration) {

  def cleanReports = Def.task {

    val configuration =
      SerenityInfrastructure.getConfiguration

    streams.value.log.info("cleaning serenity report directory.")
    val serinityReportFolder = configuration
      .getOutputDirectory()
      .toPath()
      .subpath(
        target.value.toPath.getNameCount(),
        configuration.getOutputDirectory().toPath().getNameCount()
      )
    streams.value.log.debug(s"[${serinityReportFolder}]")

    FileUtils.deleteDirectory(target.value / serinityReportFolder.toString)

  }

}
