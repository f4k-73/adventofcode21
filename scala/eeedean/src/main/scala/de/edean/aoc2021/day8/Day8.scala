package de.edean.aoc2021.day8

import scala.io.Source
import scala.language.postfixOps

object Day8 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input8.txt"
  else "../../data/day8.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq
  val digits: Map[Int, Int] = Map(
    'a'.toInt -> 1,
    'b'.toInt -> 2,
    'c'.toInt -> 4,
    'd'.toInt -> 8,
    'e'.toInt -> 16,
    'f'.toInt -> 32,
    'g'.toInt -> 64
  )
  val riddles = input.map(line => {
    val splitted = line.split("\\|")
    (splitted(0).split(" ").filter(!_.isEmpty).toSeq,
      splitted(1).split(" ").filter(!_.isEmpty).toSeq)
  })

  case class EncodableString(s: String) {
    def encode: Int = s.foldLeft(0)({case (left, right) => left | digits(right.toInt)})
  }
  implicit def encodableStringConversion(str: String) = EncodableString(str)
  case class DecodableInt(n: Int) {
    def decode: String = digits.toSeq
      .filter({ case (_, value) => (n & value) == value })
      .map(_._1).sorted
      .foldLeft("")({ case (left, right) => left + right.toChar })
  }
  implicit def decodableIntConversion(n: Int) = DecodableInt(n)


  println(riddles.map({ case (uniqueCombinations, writtenOutput) => {
    def findSegments(remaining: Seq[String], taken: Map[Int, Int] = Map()): Map[Int, Int] = {
      if (remaining.exists(_.size.equals(2))) {
        val easySegments = Map(
          1 -> remaining.filter(_.size.equals(2)).head.encode,
          4 -> remaining.filter(_.size.equals(4)).head.encode,
          7 -> remaining.filter(_.size.equals(3)).head.encode,
          8 -> remaining.filter(_.size.equals(7)).head.encode
        )
        findSegments(remaining.filterNot(seg => easySegments.values.exists(_.equals(seg.encode))), taken ++ easySegments)
      } else {
        taken
      }
    }
    val easySegments = findSegments(uniqueCombinations)
    val digitsToSegments = easySegments
    println(s"${uniqueCombinations.mkString(" ")} |\n${writtenOutput.mkString(" ")}")
    println(digitsToSegments.map({case (key, value) => (key, value.decode)}))

    val foundValues = writtenOutput.map(_.encode).filter(segments => digitsToSegments.values.exists(_.equals(segments)))
    println(foundValues.map(n => digitsToSegments.toSeq.filter({ case (_, value) => value.equals(n)}).head._1))
    foundValues.size
  }}).sum)
}