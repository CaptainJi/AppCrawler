package com.testerhome.appcrawler.it

import io.appium.java_client.service.local.{AppiumDriverLocalService, AppiumServiceBuilder}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestAppiumService extends FunSuite{

  test("start appium service") {

//    System.setProperty("APPIUM_BINARY_PATH","node_modules/appium/lib/main.js")
    System.setProperty("JAVA_HOME","/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home")
    val builder = new AppiumServiceBuilder
    val service = AppiumDriverLocalService.buildService(builder)

    service.start()

    println(service.isRunning)

    service.stop()
  }
}
