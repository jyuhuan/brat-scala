name := "brat-scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "net.liftweb" % "lift-json_2.11" % "3.0-M8"

resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "yutil"     % "0.0.12-SNAPSHOT"
