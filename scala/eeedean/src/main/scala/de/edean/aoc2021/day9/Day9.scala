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
  case class Coord(x: Int, y: Int)
  implicit object CoordOrdering extends Ordering[Coord] {
    override def compare(x: Coord, y: Coord): Int = {
      val xOrder =x.x.compare(y.x)
      if (xOrder == 0)
        x.y.compare(y.y)
      else xOrder
    }
  }

  val coords = input.zipWithIndex.map({ case (str, i) => (i, str.zipWithIndex) }).flatMap({ case (y, xs) => xs.map({case (char, x) => (Coord(x, y), char.asDigit)})}).toMap
  val sortedCoordSeq = coords.toSeq.sorted
  // println((0 to maxY).map(y => (0 to maxX).map(x => coords(Coord(x, y))).mkString).mkString("\n"))
  def findNeighbourVals(c: Coord): Seq[Int] = {
    val (left, right, top, bottom) = (Coord(c.x - 1, c.y), Coord(c.x + 1, c.y), Coord(c.x, c.y - 1), Coord(c.x, c.y + 1))
    Seq(
      coords.get(left).getOrElse(10),
      coords.get(right).getOrElse(10),
      coords.get(top).getOrElse(10),
      coords.get(bottom).getOrElse(10)
    )
  }
  // if all neighbours are lower / value is higher than/equal to none of them => it's a low point!
  val lowPoints = coords.filter({ case (coord, value) => findNeighbourVals(coord).filter(_ <= value).isEmpty })
  println(s"There are ${lowPoints.size} low points: $lowPoints")
  val riskLevelSum = lowPoints.map(_._2 + 1).sum
  println(s"Part 1: $riskLevelSum")

  def exploreBasin(c: Coord, oldResultSet: Set[(Coord, Int)] = Set()): Set[(Coord, Int)] = {
    val (left, right, top, bottom) = (Coord(c.x - 1, c.y), Coord(c.x + 1, c.y), Coord(c.x, c.y - 1), Coord(c.x, c.y + 1))
    var result = oldResultSet + coords.get(c).map((c, _)).get
    def enhanceResult(coord: Coord) = {
      val opt = coords.get(coord).map((coord, _))
      if (opt.exists(_._2 < 9) && !result.contains(opt.get))
        result = result ++ exploreBasin(coord, result)
    }
    enhanceResult(left)
    enhanceResult(right)
    enhanceResult(top)
    enhanceResult(bottom)

    result
  }

  val basins = lowPoints.map({ case (Coord(x, y), value) => exploreBasin(Coord(x, y)) }).toSeq
    .sortBy(_.size).slice(lowPoints.size - 3, lowPoints.size)
  println(basins)
  println(basins.map(_.size).mkString(" "))
  println(s"Part 2: ${basins.map(_.size).reduce((left, right) => left * right)}")
}