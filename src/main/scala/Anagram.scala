package main.scala

import java.io.{File, FileNotFoundException}

import scala.annotation.tailrec
import scala.io.Source

object Anagram {

  val fileName = "dictionary.txt"

  def main(args: Array[String]) {

    val words = loadWords(fileName)
    val dictionary = buildDictionary(words, Map.empty)

    dictionary.filter(p => {
      p._2.length > 1
    }).foreach(p => {
      println(p._2.mkString(", "))
    })

  }

  def loadWords(name: String): List[String] = {
    try {
      val f = new File(getClass.getClassLoader.getResource("main/resources/" + name).getPath)
      Source.fromFile(f).getLines.map(_.toLowerCase).toList
    } catch {
      case _ : FileNotFoundException | _ : NullPointerException =>
        println("Could not open word list.")
        List.empty
    }
  }

  @tailrec
  def buildDictionary(words: List[String], m: Map[String, List[String]]): Map[String, List[String]] = {
    if (words.isEmpty) m
    else {
      val key = words.head.sorted
      val m_ = m + (key -> (m.getOrElse(key, List.empty) :+ words.head))
      buildDictionary(words.tail, m_)
    }
  }
}
