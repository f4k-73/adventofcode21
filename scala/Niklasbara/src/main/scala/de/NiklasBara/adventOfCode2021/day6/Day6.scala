package de.NiklasBara.adventOfCode2021.day6

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day6 extends App {
  val INPUT_PATH = Inputs.test
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputs = lines.head

  println(inputs)
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test = Value("src/main/resources/day6/test_input.txt")
  val local = Value("src/main/resources/day6/input.txt")
  val shared = Value("../../data/day5.txt")
}