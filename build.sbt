scalaVersion := "2.11.6"

val scalazVersion = "7.1.0"
/*
libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test",
  "org.scala-sbt" %% "io" % "1.0.0-M3"
)
*/

scalacOptions += "-feature"

//initialCommands in console := "import scalaz._, Scalaz._"
//initialCommands in console := "import SVM._"
