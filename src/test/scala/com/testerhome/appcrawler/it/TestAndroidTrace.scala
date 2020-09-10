package com.testerhome.appcrawler.it

import com.testerhome.appcrawler.plugin.AndroidTrace
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by seveniruby on 16/4/24.
  */
@RunWith(classOf[JUnitRunner])
class TestAndroidTrace extends FunSuite{
  test("list"){
    new AndroidTrace().getConnections()

  }

}
