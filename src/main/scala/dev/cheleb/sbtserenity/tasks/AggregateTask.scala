package dev.cheleb.sbtserenity.tasks

import sbt._
import sbt.Keys._

class AggregateTask(config: Configuration) {

  def aggregate = Def.task {
    streams.value.log.info("Aggregate Serenity reports")
  }

}
