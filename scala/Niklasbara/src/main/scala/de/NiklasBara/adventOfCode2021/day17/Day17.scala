package de.NiklasBara.adventOfCode2021.day17

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}

object Day17 extends App {

  val inputs = InputReader(AdventDays.SEVENTEEN, InputTypes.local).lines
    .head.replaceAll("target area: ", "").split(", ")

  println(inputs.mkString(" "))

}
