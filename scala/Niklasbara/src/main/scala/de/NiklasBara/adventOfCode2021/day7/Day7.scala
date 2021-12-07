package de.NiklasBara.adventOfCode2021.day7

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day7 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputs = lines.head.split(",").map(_.toInt).groupMapReduce(identity)(_ => 1)(_ + _)

  private val analyzer: CrabAnalyzer = CrabAnalyzer(inputs)

  println(s"part1 ${analyzer.moveLeast(true)}")
  println(s"part1 ${analyzer.moveLeast(false)}")
}

case class CrabAnalyzer(crabToPos: Map[Int, Int]) {

  def moveLeast(staticFuelCosts: Boolean): Int = {
    val positions = crabToPos.keys.min to crabToPos.keys.max
    positions.map(fuelToMoveCrabsIntoPos(crabToPos, _, staticFuelCosts)).min
  }

  private def fuelToMoveCrabsIntoPos(crabs: Map[Int, Int], position: Int, staticFuelCosts: Boolean): Int = {
    crabs.map(
      if (staticFuelCosts)
        fuelToMoveCrabIntoPos(_, position)
      else
        fuelToMoveCrabIntoPosWithGrowingFuelCosts(_, position)
    ).sum
  }

  private def fuelToMoveCrabIntoPos(crab: (Int, Int), position: Int): Int = {
    Math.abs(crab._1 - position) * crab._2
  }

  private def fuelToMoveCrabIntoPosWithGrowingFuelCosts(crab: (Int, Int), position: Int): Int = {
    fuelCosts(Math.abs(crab._1 - position)) * crab._2
  }

  private def fuelCosts(n: Int): Int = {
    n * (n + 1) / 2
  }

}

object Inputs extends Enumeration {
  type Inputs = Value
  val test = Value("src/main/resources/day7/test_input.txt")
  val local = Value("src/main/resources/day7/input.txt")
  val shared = Value("../../data/day7.txt")
}


