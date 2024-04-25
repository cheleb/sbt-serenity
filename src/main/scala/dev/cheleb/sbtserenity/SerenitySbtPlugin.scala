package dev.cheleb.sbtserenity

import sbt.Keys._
import sbt.Tests.Cleanup
import sbt._
import plugins._
import dev.cheleb.sbtserenity.tasks.SerenityTasks

object SerenitySbtPlugin extends AutoPlugin {

  def projectKey = Def.setting(name.value).toString

  import SerenityKeys._
  override def trigger: PluginTrigger = allRequirements

  override def requires: AutoPlugin = JvmPlugin

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    serenityTasks := new SerenityTasks(
      projectKey,
      streams.value.log,
      baseDirectory.value
    ),
    test := {
      serenityReportTask.dependsOn((Test / test).result).value
    },
    testOnly := (Def.inputTaskDyn {
      import sbt.complete.Parsers.spaceDelimited
      val args = spaceDelimited("<args>").parsed
      Def.taskDyn {
        serenityReportTask.dependsOn(
          (Test / testOnly).toTask(" " + args.mkString(" ")).result
        )
      }
    }).evaluated,
    testQuick := (Def.inputTaskDyn {
      import sbt.complete.Parsers.spaceDelimited
      val args = spaceDelimited("<args>").parsed
      Def.taskDyn {
        serenityReportTask.dependsOn(
          (Test / testQuick).toTask(" " + args.mkString(" ")).result
        )
      }
    }).evaluated,
    clean := {
      (Serenity / clearReports).dependsOn((clean).result).value
    },
    historyReports := serenityTasks.value.historyReports,
    serenityReportTask := serenityTasks.value.serenityReport
  ) ++ mySettings

  lazy val mySettings = inConfig(Serenity)(
    SerenitySetttings.settings(Serenity)
  )

}
