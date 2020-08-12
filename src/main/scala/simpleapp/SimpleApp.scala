package simpleapp

import com.typesafe.config._

class SimpleLibContext(config: Config) {

  // This verifies that the Config is sane and has our
  // reference config. Importantly, we specify the "simple-lib"
  // path so we only validate settings that belong to this
  // library. Otherwise, we might throw mistaken errors about
  // settings we know nothing about.
  config.checkValid(ConfigFactory.defaultReference(), "simple-lib")

  // This uses the standard default Config, if none is provided,
  // which simplifies apps willing to use the defaults
  def this() {
    this(ConfigFactory.load())
  }

  // this is the amazing functionality provided by simple-lib
  def printSetting(path: String) {
    println("The setting '" + path + "' is: " + config.getString(path))
  }
}

object SimpleApp extends App {
  def demoConfigInSimpleLib(config: Config) {
    val context = new SimpleLibContext(config)
    context.printSetting("simple-lib.foo")
    context.printSetting("simple-lib.hello")
    context.printSetting("simple-lib.whatever")
  }

  System.setProperty("simple-lib.whatever", "This value comes from a system property")

  val config1 = ConfigFactory.load("application.conf")

  demoConfigInSimpleLib(config1)
  println(config1.getString("simple-app.answer"))
}
