sbtPlugin := true

organization := "dev.cheleb"

name := "sbt-serenity"

scalacOptions ++= Seq("-deprecation", "-feature")

resolvers += Resolver.sonatypeRepo("snapshots")

val serenityVersion = "4.1.14"

libraryDependencies ++= Seq(
//  "junit" % "junit" % "4.11",
  "com.novocode" % "junit-interface" % "0.11",
  "net.serenity-bdd" % "serenity-core" % serenityVersion,
  "net.serenity-bdd" % "serenity-cucumber" % serenityVersion,
  "net.serenity-bdd" % "serenity-junit" % serenityVersion,
//  "net.serenity-bdd" % "serenity-single-page-report" % serenityVersion,
//  "net.serenity-bdd" % "serenity-navigator-report" % serenityVersion,
  "net.serenity-bdd" % "serenity-jira-plugin" % serenityVersion,
  "org.scalatest" %% "scalatest" % "3.2.19",
  "org.slf4j" % "slf4j-simple" % "2.0.13"
)

sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
pgpPublicRing := file("/tmp/public.asc")
pgpSecretRing := file("/tmp/secret.asc")
pgpPassphrase := sys.env.get("PGP_PASSWORD").map(_.toArray)
scmInfo := Some(
  ScmInfo(
    url("https://github.com/cheleb/sbt-serenity/"),
    "scm:git:git@github.com:cheleb/sbt-serenity.git"
  )
)
homepage := Some(url("https://github.com/cheleb/sbt-serenity"))
licenses := List(
  "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
)
developers := List(
  Developer(
    "cheleb",
    "Olivier NOUGUIER",
    "olivier.nouguier@gmail.com",
    url("https://github.com/cheleb")
  )
)
