lazy val root = (project in file("."))
  .settings(name := "ddd-microservices-workshop")
  .aggregate(listingApi, listingImpl, bookingApi, bookingImpl, userApi, userImpl)

// Övernattning
organization in ThisBuild := "eu.reactivesystems.workshop"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

// we use a combination of Lombok annotations for data classes
// and vavr for collections for immutable data structures in Java.
val lombok = "org.projectlombok" % "lombok" % "1.16.18"
val vavr = "io.vavr" % "vavr" % "0.9.2"
val vavrSerialization = "io.vavr" % "vavr-jackson" % "0.9.2"

lazy val security = (project in file("security"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lagomJavadslServer % Optional
    )
  )

lazy val listingApi = (project in file("listing-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )
  .dependsOn(security)

lazy val listingImpl = (project in file("listing-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslTestKit,
      lagomJavadslKafkaBroker
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(listingApi)

lazy val bookingApi = (project in file("booking-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )
  .dependsOn(security)

lazy val bookingImpl = (project in file("booking-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomJava)
  .dependsOn(bookingApi, listingApi)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslTestKit,
      lagomJavadslKafkaBroker,
      vavr,
      vavrSerialization
    ),
    maxErrors := 10000

  )

lazy val searchApi = (project in file("search-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )
  .dependsOn(security)

lazy val searchImpl = (project in file("search-impl"))
  .settings(commonSettings: _*)
  // .enablePlugins(LagomJava)
  .dependsOn(searchApi, listingApi, bookingApi)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslTestKit
    )
  )

lazy val transactionApi = (project in file("transaction-api"))
  .settings(commonSettings: _*)
  .dependsOn(listingApi)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )
  .dependsOn(security)

lazy val transactionImpl = (project in file("transaction-impl"))
  .settings(commonSettings: _*)
  // .enablePlugins(LagomJava)
  .dependsOn(transactionApi, bookingApi)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslTestKit
    )
  )

lazy val userApi = (project in file("user-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )
  .dependsOn(security)

lazy val userImpl = (project in file("user-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomJava)
  .dependsOn(userApi)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslPersistenceCassandra
  )


def commonSettings: Seq[Setting[_]] = eclipseSettings ++ Seq(
  javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation", "-parameters"),
  libraryDependencies += "org.projectlombok" % "lombok" % "1.16.12"
)

// Configuration of sbteclipse
// Needed for importing the project into Eclipse
lazy val eclipseSettings = Seq(
  EclipseKeys.projectFlavor := EclipseProjectFlavor.Java,
  EclipseKeys.withBundledScalaContainers := false,
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.withJavadoc := true,
  // avoid some scala specific source directories
  unmanagedSourceDirectories in Compile := Seq((javaSource in Compile).value),
  unmanagedSourceDirectories in Test := Seq((javaSource in Test).value)
)


lagomCassandraCleanOnStart in ThisBuild := false
