val ScalaVer = "2.12.6"

// val Cats          = "0.9.0"
val CatsEffect    = "0.10.1"
val KindProjector = "0.9.7"

val ScalaTest  = "3.0.4"
val ScalaCheck = "1.13.5"

lazy val commonSettings = Seq(
  name    := "ackermann"
, version := "0.1.0"
, scalaVersion := ScalaVer
, libraryDependencies ++= Seq(
    // "org.typelevel"  %% "cats"        % Cats
    "org.typelevel"  %% "cats-effect" % CatsEffect
  // , "org.scalatest"  %% "scalatest"  % ScalaTest  % "test"
  // , "org.scalacheck" %% "scalacheck" % ScalaCheck % "test"

  )

, addCompilerPlugin("org.spire-math" %% "kind-projector" % KindProjector)
, scalacOptions ++= Seq(
      "-deprecation"
    , "-encoding", "UTF-8"
    , "-feature"
    , "-language:existentials"
    , "-language:higherKinds"
    , "-language:implicitConversions"
    , "-language:experimental.macros"
    , "-unchecked"
    // , "-Xfatal-warnings"
    // , "-Xlint"
    // , "-Yinline-warnings"
    , "-Ywarn-dead-code"
    , "-Xfuture"
    , "-Ypartial-unification")
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    initialCommands := "import ackermann._, Main._"
  )
