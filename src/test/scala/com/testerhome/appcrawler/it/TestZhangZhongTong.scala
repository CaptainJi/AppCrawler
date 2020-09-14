package com.testerhome.appcrawler.it

import java.time.LocalDateTime

import com.testerhome.appcrawler.AppCrawler
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestZhangZhongTong extends FunSuite {
  test("android") {
    AppCrawler.main(Array("-c", "demo.yml",
      "-o", s"output/${LocalDateTime.now()}",
      "-a", "zzt.apk", "-vv"
    )
    )
  }
}
