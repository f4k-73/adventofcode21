package de.edean.aoc2021.day9

import scala.io.Source
import scala.language.postfixOps

object Day9 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input9.txt"
  else "../../data/day9.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq

  val maxX = input.head.size - 1
  val maxY = input.size - 1
  // println(s"$maxX x $maxY")

  //val coords = input.map(_.zipWithIndex).zipWithIndex.flatMap({ case (xs, y) => xs.map(x => ((x._2, y), x._1.asDigit)) }).toMap
  val coords = input.zipWithIndex.map({ case (str, i) => (i, str.zipWithIndex) }).flatMap({ case (y, xs) => xs.map({case (char, x) => ((x, y), char.asDigit)})}).toMap
  val sortedCoordSeq = coords.toSeq.sorted
  // println((0 to maxY).map(y => (0 to maxX).map(x => coords(x, y)).mkString).mkString("\n"))
  def neighbours(x: Int, y: Int): Seq[Int] = {
    Seq(
      coords.get((x - 1, y)).getOrElse(10),
      coords.get((x + 1, y)).getOrElse(10),
      coords.get((x, y - 1)).getOrElse(10),
      coords.get((x, y + 1)).getOrElse(10)
    )
  }
  // if all neighbours are lower / value is higher than/equal to none of them => it's a low point!
  val lowPoints = coords.filter({ case ((x, y), value) => neighbours(x, y).filter(_ <= value).isEmpty })
  println(s"There are ${lowPoints.size} low points: $lowPoints")
  val riskLevelSum = lowPoints.map(_._2 + 1).sum
  println(s"Part 1: $riskLevelSum")
}