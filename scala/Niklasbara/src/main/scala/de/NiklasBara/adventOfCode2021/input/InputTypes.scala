package de.NiklasBara.adventOfCode2021.input

object InputTypes extends Enumeration {
  type InputTypes = String => String
  val test: String => String = day => s"src/main/resources/day$day/test_input.txt"
  val local: String => String = day => s"src/main/resources/day$day/input.txt"
  val shared: String => String = day => s"../../data/day$day.txt"
}
