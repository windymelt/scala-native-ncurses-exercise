ThisBuild / scalaVersion := "3.5.2"
import bindgen.interface.Binding

lazy val core = (projectMatrix in file("core"))
  .enablePlugins(BindgenPlugin)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-effect" % "3.5.5",
    ),
    bindgenBindings := Seq(
      Binding(
        (Compile / resourceDirectory).value / "scala-native" / "ncurses.h",
        "libncurses",
      ),
    ),
    nativeConfig ~= (_.withLinkingOptions(Seq("-lncurses"))),
  )
  // .jvmPlatform(scalaVersions = Seq("3.5.1"))
  // .jsPlatform(scalaVersions = Seq("3.5.1"))
  .nativePlatform(scalaVersions = Seq("3.5.2"))
