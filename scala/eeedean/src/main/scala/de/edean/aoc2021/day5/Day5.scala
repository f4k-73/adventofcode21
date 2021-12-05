package de.edean.aoc2021.day5

import java.io.PrintWriter
import scala.io.Source
import scala.language.postfixOps
import scala.collection.parallel.CollectionConverters._

object Day5 extends App {
  val COLUMN_ROW_AMOUNT = 5
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input5.txt"
  else "../../data/day5.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().toSeq
  case class Coord(x: Int, y:Int) {
    def to(end: Coord, stepping: (Int, Int), coords: Seq[Coord] = Seq(this)): Seq[Coord] = {
      val currentCoord = coords.last
      if(currentCoord.equals(end)) {
        coords
      } else {
        val nextCoord = Coord(currentCoord.x + stepping._1, currentCoord.y + stepping._2)
        to(end, stepping, coords :+ nextCoord)
      }
    }
  }
  case class Line(from: Coord, to: Coord) {
    val isDiagonal = {
      !(from.x == to.x || from.y == to.y)
    }
  }

  val lines = input
    .map(line => {
      val coords = line.split(" -> ")
        .map(coord => {
          val nums = coord.split(",")
          Coord(nums(0).toInt, nums(1).toInt)
        })
      Line(coords(0), coords(1))
    })//.filter(!_.isDiagonal) // out commented for part 2
  val hitCoordinates = (for { Line(from, to) <- lines } yield {
    val horizontalStepping = if (from.x < to.x) 1 else if (from.x > to.x) -1 else 0
    val verticalStepping = if (from.y < to.y) 1 else if (from.y > to.y) -1 else 0
    val stepping = (horizontalStepping, verticalStepping)
    from.to(to, stepping)
  }).flatten
  val countOfHits = hitCoordinates.groupMapReduce(coord => coord)(_ => 1)({case (left, right) => left + right})
  println(countOfHits.filter(_._2>1).map(_=> 1).sum)


  def printThatShit: Unit = {
    println("---")
    val maxX = hitCoordinates.map(_.x).max
    val maxY = hitCoordinates.map(_.y).max
    val maxHits = countOfHits.map(_._2).max
    println(s"creating coord system of size $maxX x $maxY")
    val coordSystem = (0 to maxX).par.flatMap(x => (0 to maxY).map(y => Coord(x, y) -> countOfHits.getOrElse(Coord(x, y), 0))).toMap
    println(s"creating string for coord system")
    val coordSystemString = (0 to maxY).map(y => (0 to maxX).map(x => String.format(s"%${maxHits}d", coordSystem(Coord(x, y)))).mkString(" ")).mkString("\n")
    println("Writing to disk")
    new PrintWriter("/tmp/day6.txt") { write(coordSystemString); close }
  }
  //printThatShit
}