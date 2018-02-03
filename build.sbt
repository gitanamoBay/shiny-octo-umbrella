name := "untitled"
 
version := "1.0" 


lazy val `untitled` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc , ehcache , ws , specs2 % Test , guice ,
  "org.scalikejdbc" %% "scalikejdbc" % "3.2.0",
  "org.postgresql" % "postgresql" % "42.2.1",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.2.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.2"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      