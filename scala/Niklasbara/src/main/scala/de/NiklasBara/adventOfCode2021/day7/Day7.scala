package de.NiklasBara.adventOfCode2021.day7

import de.NiklasBara.adventOfCode2021.day7

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day7 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputs = lines.head.split(",").map(_.toInt).groupMapReduce(identity)(_ => 1)(_ + _)

  private val analyzer: CrabAnalyzer = CrabAnalyzer(inputs)

  println(s"part1 ${analyzer.moveLeast(FuelCalculators.SameAsSpaces)}")
  println(s"part1 ${analyzer.moveLeast(FuelCalculators.PlusOnePerSpace)}")
}

case class CrabAnalyzer(crabToPos: Map[Int, Int]) {

  def moveLeast(calculateFuel: Int => Int): Int = {
    val positions = crabToPos.keys.min to crabToPos.keys.max
    positions.map(fuelToMoveCrabsIntoPos(crabToPos, _, calculateFuel)).min
  }

  private def fuelToMoveCrabsIntoPos(crabs: Map[Int, Int], position: Int, calculateFuel: Int => Int): Int = {
    crabs.map(fuelToMoveCrabIntoPos(_, position, calculateFuel)).sum
  }

  private def fuelToMoveCrabIntoPos(crab: (Int, Int), position: Int, calculateFuel: Int => Int): Int = {
    calculateFuel(Math.abs(crab._1 - position)) * crab._2
  }
}

object FuelCalculators extends Enumeration {
  val SameAsSpaces: Int => Int = (a: Int) => a
  val PlusOnePerSpace: Int => Int = (spacesToMove: Int) => spacesToMove * (spacesToMove + 1) / 2
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: day7.Inputs.Value = Value("src/main/resources/day7/test_input.txt")
  val local: day7.Inputs.Value = Value("src/main/resources/day7/input.txt")
  val shared: day7.Inputs.Value = Value("../../data/day7.txt")
}
