sbtPlugin := true

organization := "dev.cheleb"

name := "sb-serenity"

scalacOptions ++= Seq("-deprecation", "-feature")

resolvers += Resolver.sonatypeRepo("snapshots")

val serenityVersion = "4.1.9"

libraryDependencies ++= Seq(
//  "junit" % "junit" % "4.11",
  "com.novocode" % "junit-interface" % "0.11",
  "net.serenity-bdd" % "serenity-core" % serenityVersion,
  "net.serenity-bdd" % "serenity-cucumber" % serenityVersion,
  "net.serenity-bdd" % "serenity-junit" % serenityVersion,
  "net.serenity-bdd" % "serenity-jira-plugin" % "4.1.9",
  "org.scalatest" %% "scalatest" % "3.2.18",
  "org.slf4j" % "slf4j-simple" % "2.0.13"
)

publishMavenStyle := false

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
developers := List(
  Developer(
    "cheleb",
    "Olivier NOUGUIER",
    "olivier.nouguier@gmail.com",
    url("https://github.com/cheleb")
  )
)
