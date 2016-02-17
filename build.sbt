name := """SpraySlickRestTemplate"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
	val akkaV = "2.3.6"
	val sprayV = "1.3.2"
	Seq (
		"io.spray" %% "spray-can" % sprayV withSources() withJavadoc(),
		"io.spray" %% "spray-routing" % sprayV withSources() withJavadoc(),
		"io.spray" %% "spray-json" % "1.3.1",
		"io.spray" %% "spray-testkit" % sprayV % "test",
		"com.typesafe.akka" %% "akka-actor" % akkaV,
		"com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
		"org.scalatest" %% "scalatest" % "2.2.4" % "test"
	)
}