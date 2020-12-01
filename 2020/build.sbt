scalaVersion := "2.13.3"

lazy val aoc2020 = (project in file("."))
  .settings(
    name := "aoc2020",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
