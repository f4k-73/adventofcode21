package de.NiklasBara.adventOfCode2021.day8


import java.nio.charset.StandardCharsets
import scala.io.Source

object Day8 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputsToOutputs = lines.map(_.split(" \\| ")).map(arr => arr(0) -> arr(1)).toMap

  val uniqueSegments = Seq(2, 3, 4, 7)


  val countOfUniques = inputsToOutputs.values.map(_.trim)
    .flatMap(_.split(" ")).map(_.length)
    .count(uniqueSegments.contains(_))

  println("part1:", countOfUniques)

}


object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day8/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day8/input.txt")
  val shared: Inputs.Value = Value("../../data/day8.txt")
}
