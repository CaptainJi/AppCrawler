package com.testerhome.appcrawler

import java.io.File

import javax.xml.bind.annotation.XmlAttribute
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import org.apache.commons.text.StringEscapeUtils
import org.openqa.selenium.{Dimension, Point}

import scala.collection.immutable

/**
  * Created by seveniruby on 15/12/18.
  */
case class URIElement(
                       @XmlAttribute(name = "url")
                       var url: String="",
                       @XmlAttribute(name = "tag")
                       var tag: String="",
                       @XmlAttribute(name = "id")
                       var id: String="",
                       @XmlAttribute(name = "name")
                       var name: String="",
                       @XmlAttribute(name = "text")
                       var text: String="",
                       @XmlAttribute(name = "instance")
                       var instance: String="",
                       @XmlAttribute(name = "depth")
                       var depth: String="",
                       @XmlAttribute(name = "valid")
                       var valid: String="true",
                       @XmlAttribute(name = "selected")
                       var selected: String="false",
                       @XmlAttribute(name = "loc")
                       var xpath:String="",
                       @XmlAttribute(name = "ancestor")
                       var ancestor:String="",
                       @XmlAttribute(name = "x")
                       var x:Int=0,
                       @XmlAttribute(name = "y")
                       var y: Int=0,
                       @XmlAttribute(name = "width")
                       var width: Int=0,
                       @XmlAttribute(name = "height")
                       var height:Int=0
                     ) {
  //用来代表唯一的控件, 每个特定的命名控件只被点击一次. 所以这个element的构造决定了控件是否可被点击多次.
  //比如某个输入框被命名为url=xueqiu id=input, 那么就只能被点击一次
  //如果url修改为url=xueqiu/xxxActivity id=input 就可以被点击多次
  //定义url是遍历的关键. 这是一门艺术

  def this(nodeMap:scala.collection.Map[String, Any], uri:String) = {
    //name为id/name属性. 为空的时候为value属性
    //id表示android的resource-id或者iOS的name属性

    this()
    this.url=uri
    this.tag = nodeMap.getOrElse("tag", "").toString
    this.id = nodeMap.getOrElse("name", "").toString
    this.name = nodeMap.getOrElse("label", "").toString
    this.text = nodeMap.getOrElse("value", "").toString
    this.instance = nodeMap.getOrElse("instance", "").toString
    this.depth = nodeMap.getOrElse("depth", "").toString
    this.xpath = nodeMap.getOrElse("xpath", "").toString
    this.x=nodeMap.getOrElse("x", "0").toString.toInt
    this.y=nodeMap.getOrElse("y", "0").toString.toInt
    this.width=nodeMap.getOrElse("width", "0").toString.toInt
    this.height=nodeMap.getOrElse("height", "0").toString.toInt
    this.ancestor=nodeMap.getOrElse("ancestor", "").toString
    this.selected=nodeMap.getOrElse("selected", "false").toString
    this.valid=nodeMap.getOrElse("valid", "true").toString


  }
  /**
    * 提取元素的tag组成的路径
    * @return
    */
  def getAncestor(): String ={
    ancestor
  }
  def center(): Point  ={
    new Point(x+width/2, y+height/2)
  }

  def location(): Point={
    new Point(x, y)
  }

  def size(): Dimension ={
    new Dimension(width, height)
  }
  def toXml(): String ={
    s"""
      |<UIAButton dom="" enabled="true" height="${height}" hint="" label="${id}"
      |        name="${name}" path="/0/0/4" valid="true" value="${xpath}" visible="true"
      |        width="${width}" x="${x}" y="${y}"/>
    """.stripMargin

  }

  override def toString: String = {
    val fileName=new StringBuilder()
    fileName.append(url)
    fileName.append(s".tag=${tag.replace("android.widget.", "").replace("Activity", "")}")
    if(instance.nonEmpty){
      fileName.append(s".${instance}")
    }
    if(depth.nonEmpty){
      fileName.append(s".depth=${depth}")
    }
    if(id.nonEmpty){
      fileName.append(s".id=${id.split('/').last}")
    }
    if(name.nonEmpty){
      fileName.append(s".name=${name}")
    }
    if(text.nonEmpty){
      fileName.append(s".text=${ StringEscapeUtils.unescapeHtml4(text).replace(File.separator, "+")}")
    }

    fileName.toString().take(100)
  }


  def hash(s:String)={
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = s.getBytes("UTF-8")
    m.update(b,0,b.length)
    new java.math.BigInteger(1,m.digest()).toString(16)
  }

}
