lazy val akkaVersion = "2.5.23"
lazy val akkaHttpVersion = "10.1.5"
lazy val akkaPersistenceInmemoryVersion = "2.5.15.2"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaChain",
    version := "0.1.0-RC1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "org.iq80.leveldb" % "leveldb" % "0.10",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.github.dnvriend" %% "akka-persistence-inmemory" % akkaPersistenceInmemoryVersion
    )
  )
