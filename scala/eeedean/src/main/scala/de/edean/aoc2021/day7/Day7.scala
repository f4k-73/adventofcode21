package de.edean.aoc2021.day7

import scala.io.Source
import scala.language.postfixOps

object Day7 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input7.txt"
  else "../../data/day7.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq
  val initialPositions = input.head.split(",").map(_.toInt).sorted
  val amountCrabs = initialPositions.size
  val middleIndex = initialPositions.size / 2 - 1

  val median =
    if (amountCrabs % 2 == 0)
      (initialPositions(middleIndex) + initialPositions(middleIndex + 1)) / 2
    else
      initialPositions(middleIndex)
  val average = (initialPositions.sum.toDouble / amountCrabs).round.toInt

  println(initialPositions.map(n => f"$n%3d").mkString(", "))
  (0 to middleIndex by 1).foreach(_ => print("-----"))
  println("-⤴️")

  def fuelConsumption(n: Int, npp: Int = 0): Int = {
    if(n>1) n + fuelConsumption(n-1) else n
  }

  def findOptimum(positions: Seq[Int], target: Int): Int = {
    def summedConsumption(deviation: Int = 0): Int = {
      positions.map(_ - target + deviation).map(_.abs).map(fuelConsumption(_)).sum
    }
    val left = summedConsumption(-1)
    val right = summedConsumption(+1)
    val middle = summedConsumption()
    println(s"lookint at $left <- $middle -> $right")
    if(middle < left && middle < right)
      middle
    else if (left > right)
      findOptimum(positions, target - 1)
    else
      findOptimum(positions, target + 1)
  }


  println(s"Amount crabbos: $amountCrabs")
  println(s"Middle Index: $middleIndex")
  println(s"Median: $median")
  println(s"Average: $average")
  println("---")
  println("part1: " + initialPositions.map(_-median).map(_.abs).sum)  // part1
  println("part2: " + findOptimum(initialPositions, average))         // part2
}