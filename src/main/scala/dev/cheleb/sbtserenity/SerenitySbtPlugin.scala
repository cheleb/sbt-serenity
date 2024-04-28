package dev.cheleb.sbtserenity

import sbt.Keys._
import sbt.Tests.Cleanup
import sbt._
import plugins._
import net.thucydides.core.reports.ExtendedReports
import java.util.ServiceLoader
import net.thucydides.core.reports.ExtendedReport

object SerenitySbtPlugin extends AutoPlugin {

  def projectKey = Def.setting(name.value).toString

  import SerenityKeys._
  override def trigger: PluginTrigger = allRequirements

  override def requires: AutoPlugin = JvmPlugin

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    test := {
      (Serenity / serenityReportTask).dependsOn((Test / test).result).value
    },
    testOnly := (Def.inputTaskDyn {
      import sbt.complete.Parsers.spaceDelimited
      val args = spaceDelimited("<args>").parsed
      Def.taskDyn {
        (Serenity / serenityReportTask).dependsOn(
          (Test / testOnly).toTask(" " + args.mkString(" ")).result
        )
      }
    }).evaluated,
    testQuick := (Def.inputTaskDyn {
      import sbt.complete.Parsers.spaceDelimited
      val args = spaceDelimited("<args>").parsed
      Def.taskDyn {
        (Serenity / serenityReportTask).dependsOn(
          (Test / testQuick).toTask(" " + args.mkString(" ")).result
        )
      }
    }).evaluated,
    clean := {
      (Serenity / clearReports).dependsOn((clean).result).value
    }
  ) ++ mySettings

  lazy val mySettings = inConfig(Serenity)(
    SerenitySetttings.settings(Serenity, projectKey)
  )

}
