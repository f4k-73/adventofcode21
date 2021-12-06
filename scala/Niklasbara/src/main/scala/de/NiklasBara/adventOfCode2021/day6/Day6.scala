package de.NiklasBara.adventOfCode2021.day6

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day6 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputs = lines.head.split(",").map(_.toInt).groupMapReduce(identity)(_ => 1)(_ + _)

  private val analyzer: FishAnalyzer = FishAnalyzer(inputs)

  val res = analyzer.inDays(256)
  println(s"part1: ${res.fish.values.sum}")
}

case class FishAnalyzer(fish: Map[Int, Int]) {

  def inDays(days: Int, analyzer: FishAnalyzer = this): FishAnalyzer = {
    if(days <= 0){
      return analyzer
    }
    inDays(days - 1, analyzer.nextDay())
  }

  def nextDay() = {
    this.copy(
      fish = fish.map(entry => {
        if (entry._1 == 0) {
          Seq(6 -> entry._2, 8 -> entry._2)
        } else {
          Seq(entry._1 - 1 -> entry._2)
        }
      }).flatten.groupMapReduce(_._1)(_._2)(_ + _)
    )
  }
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test = Value("src/main/resources/day6/test_input.txt")
  val local = Value("src/main/resources/day6/input.txt")
  val shared = Value("../../data/day5.txt")
}