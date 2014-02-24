name := "implicitgeneratedbymacroincompanion"

version in ThisBuild := "0.1-SNAPSHOT"

organization in ThisBuild := "com.ubeeko"

scalaVersion in ThisBuild := "2.10.3"

lazy val root = project
  .in(file("."))
  .settings(
    publishArtifact := true,
    publish := {},
    publishLocal := {})
  .aggregate(core, macros)

lazy val core = project.dependsOn(macros)

lazy val macros = project

resolvers in ThisBuild ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies in ThisBuild ++= Seq(
  "org.scala-lang"      % "scala-reflect"      % "2.10.3",
  "org.scalatest"       % "scalatest_2.10"     % "2.0"           % "test"
)

scalacOptions in ThisBuild ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)
