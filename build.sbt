import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "day1"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code-2021",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "co.fs2" %% "fs2-io" % "3.2.2",
    libraryDependencies += "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0",
    libraryDependencies += "com.github.julien-truffaut" %% "monocle-core" % "2.1.0",
    libraryDependencies += "com.github.julien-truffaut" %% "monocle-macro" % "2.1.0",
    libraryDependencies += "org.typelevel" %% "kittens" % "2.3.0"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
