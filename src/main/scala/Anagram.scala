package main.scala

import java.io.{File, FileNotFoundException}

import scala.annotation.tailrec
import scala.io.Source

object Anagram {

  val fileName = "dictionary.txt"

  def main(args: Array[String]) {
    val words = loadWords(fileName)
    val dictionary = buildDictionary(words, Map.empty)
    words.foreach(w =>
      println(w + " => " + findAnagrams(w, dictionary).mkString(", "))
    )
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

  def findAnagrams(word: String, dictionary: Map[String, List[String]]): List[String] = {
    val substrings = findSubstrings(word)
    substrings.flatMap(s => {
      dictionary.getOrElse(s.sorted, List.empty)
    }).toList.sorted
  }

  def findSubstrings(word: String): Set[String] = {
    val max = word.length
    val indices = for (i <- 0 until max; j <- 1 to max if i + j <= max) yield (i, j)
    indices.map(index => {
      word.substring(index._1, index._1 + index._2)
    }).toSet
  }
}
