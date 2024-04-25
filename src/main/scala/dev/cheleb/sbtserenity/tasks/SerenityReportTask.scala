package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._
import sbt.Def.Initialize
import net.serenitybdd.plugins.sbt.SerenityCapabilities

class SerenityTasks(projectKey: String, log: Logger, baseDirectory: File)
    extends SerenityCapabilities(projectKey) {

  log.info("SerenityTasks created.")
  log.info(configuration.getEnvironmentVariables().getProperty("jira.url"))

  def serenityReport = {

    System.setProperty(
      "project.build.directory",
      baseDirectory.getAbsolutePath
    )
    log.info("generating Serenity report.")
    execute()

  }

  def historyReports {
    System.setProperty(
      "project.build.directory",
      baseDirectory.getAbsolutePath
    )
    log.info("generating Serenity report history.")
    generateHistory()
  }

}
