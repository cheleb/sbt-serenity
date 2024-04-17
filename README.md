# SBT RSS

This is an SBT 1.x plugin which uses AutoPlugin and serenity libraries to generate the test reports.

Strongly (99%) inspired by [sbt-serenity-plugin](https://github.com/abhijeetardale/serenity-sbt-plugin).

## Building 

You can build and publish the plugin in the normal way to your local Ivy repository:

```
sbt publishLocal
```

## Installation


In `project/plugins.sbt`:

```
addSbtPlugin("dev.cheleb.sbt-serenity" % "serenitysbtplugin" % "1.0.0-SNAPSHOT")
```

In `build.sbt`:

```
val myProject = (project in file(".")).enablePlugins(SerenitySbtPlugin)

```

## Usage

TDB



