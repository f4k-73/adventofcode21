package de.NiklasBara.adventOfCode2021.day11

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day11 extends App {
  val INPUT_PATH = Inputs.test
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().map(_.toCharArray.map(_.asDigit)).toSeq
  source.close()


  println("part1:", lines)
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day11/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day11/input.txt")
  val shared: Inputs.Value = Value("../../data/day11.txt")
}
