package de.NiklasBara.adventOfCode2021.input

import de.NiklasBara.adventOfCode2021.input.InputTypes.InputTypes
import de.NiklasBara.adventOfCode2021.input.AdventDays.AdventDays

import java.nio.charset.StandardCharsets
import scala.io.Source

case class InputReader(adventDay: AdventDays, inputType: InputTypes) {

  def lines: Seq[String] = {
    val source = Source.fromFile(inputType(adventDay.toString), StandardCharsets.UTF_8.toString)
    val lines = source.getLines().toSeq
    source.close()
    lines
  }

}
