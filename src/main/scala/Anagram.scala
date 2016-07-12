package main.scala

import java.io.{File, FileNotFoundException}

import scala.annotation.tailrec
import scala.io.Source

object Anagram {

  val wordList = new File(getClass.getClassLoader.getResource("main/resources/dictionary.txt").getPath)

  def main(args: Array[String]) {

    val words = loadWords(wordList)
    buildDictionary(Map.empty[String, List[String]], words)

  }

  def loadWords(wl: File): List[String] = {
    try {
      Source
        .fromFile(wl)
        .getLines
        .map(_.toLowerCase)
        .toList
    } catch {
      case e: FileNotFoundException => List.empty
    }
  }

  @tailrec
  def buildDictionary(m: Map[String, List[String]], words: List[String]): Map[String, List[String]] = {
    if (words.isEmpty) m
    else {
      val key = words.head.sorted
      val m_ = m + (key -> m.getOrElse(key, List[String]()).:+(words.head))
      buildDictionary(m_, words.tail)
    }
  }

}
