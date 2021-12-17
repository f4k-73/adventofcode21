package de.edean.aoc2021.day11

import scala.io.Source
import scala.language.postfixOps

object Day11 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input11.txt"
  else "../../data/day11.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq
  val maxX = input.head.length - 1
  val maxY = input.length - 1

  type OctopusMatrix = Map[(Int, Int), (Int, Boolean)]

  def print(matrix: OctopusMatrix) = {
    println((0 to maxY).map(y => (0 to maxX).map(x => matrix.get(x, y).get).mkString(" ")).mkString("\n"))
  }
  def printNrs(matrix: OctopusMatrix) = {
    println((0 to maxY).map(y => (0 to maxX).map(x => matrix.get(x, y).get._1).map(n => if(n >= 10) 0 else n).mkString).mkString("\n"))
  }

  val matrix = input.map(_.map(n => (n.asDigit, false)))
    .map(_.zipWithIndex).zipWithIndex
    .flatMap({ case (xs, y) => xs.map({ case ((n, activated), x) => ((x, y), (n, activated)) }) }).toMap

  def step(matrix: OctopusMatrix): (OctopusMatrix, Int) = {
    def incrementNeighboursOfActivated(matrix: OctopusMatrix, toExpand: OctopusMatrix): OctopusMatrix = {
      val steppings = Seq(
        (-1, -1),  (0, +1), (+1, -1),
        (-1,  0),           (+1,  0),
        (-1, +1),  (0, -1), (+1, +1)
      )
      val processedNeighbours = toExpand.toSeq.filter(_._2._2)
        .flatMap({ case ((x, y), octopus) =>
          steppings.map({ case (addX, addY) => (x + addX, y + addY) })
            .map(c => (c, matrix.get(c)))
            .filter(_._2.isDefined)
            .map({ case (c, opt) => (c, opt.get) })
            .filterNot(_._2._2)
        }).map({ case (c, (n, activated)) => (c, (n + 1, n >= 9)) })
        .groupMapReduce(_._1)(_._2)({case ((l, _), (r, _)) => (Math.max(l, r) + 1, Math.max(l, r) >= 9)})
      val newMatrix = matrix.map({ case (c, old) => (c, processedNeighbours.get(c).getOrElse(old))})
      if (processedNeighbours.filter(_._2._2).isEmpty)
        newMatrix
      else
        incrementNeighboursOfActivated(newMatrix, processedNeighbours.map({case (c, (n, _)) => (c, newMatrix.get(c).get)}))
    }

    val incrementedMatrix = matrix.map({ case ((x, y), (n, activated)) => ((x, y), (n + 1, n >= 9)) })
    val steppedMatrix = incrementNeighboursOfActivated(incrementedMatrix, incrementedMatrix)
    (steppedMatrix.map({case (c, (n, activated)) => if (activated) (c, (0, false)) else (c, (n, false))}), steppedMatrix.filter(_._2._2).size)
  }

  def doXSteps(matrix: OctopusMatrix, count: Int, flashes: Int = 0): (OctopusMatrix, Int) = {
    if (count == 0)
      (matrix, flashes)
    else {
      val stepped = step(matrix)
      doXSteps(stepped._1, count - 1, stepped._2 + flashes)
    }
  }
  println(s"part 1: ${doXSteps(matrix, 100)._2}")
  def findHellaFlash(matrix: OctopusMatrix, count: Int = 0): Int = {
    val stepped = step(matrix)
    if (stepped._1.filter(_._2._1.equals(0)).size.equals(stepped._1.size))
      count + 1
    else
      findHellaFlash(stepped._1, count + 1)
  }
  println(s"part 2: ${findHellaFlash(matrix)}")
}