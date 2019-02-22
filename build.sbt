val ScalaVer = "2.12.8"

val CatsEffect    = "1.2.0"
val KindProjector = "0.9.9"

val ScalaTest  = "3.0.4"
val ScalaCheck = "1.13.5"

lazy val commonSettings = Seq(
  name    := "fp-koans"
, version := "0.1.0"
, scalaVersion := ScalaVer
, libraryDependencies ++= Seq(
    "org.typelevel"  %% "cats-effect" % CatsEffect
  , "com.chuusai" %% "shapeless" % "2.3.3"
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
  .aggregate(koans, macros)

lazy val macros = (project in file("macros"))
  .settings(commonSettings)
  .settings(
    libraryDependencies += "org.scalameta" %% "scalameta" % "4.1.4"
  )

lazy val koans = (project in file("koans"))
  .dependsOn(macros).settings(commonSettings)
