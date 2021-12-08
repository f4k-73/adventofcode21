package de.NiklasBara.adventOfCode2021.day8


import java.nio.charset.StandardCharsets
import scala.io.Source

object Day8 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputsToOutputs = lines.map(_.split(" \\| ")).map(arr => arr(0) -> arr(1)).toMap

  val uniqueSegmentLookup = Map(
    (2, "1"),
    (4, "4"),
    (3, "7"),
    (7, "8")
  )

  val fiveSegmentLookups = Map(
    ((2, 3), "3"),
    ((1, 2), "2"),
    ((1, 3), "5"),
  )
  val sixSegmentLookups = Map(
    ((2, 4), "9"),
    ((2, 3), "0"),
    ((1, 3), "6"),
  )

  val segmentLookupTable = Map(
    (5, (overlappingOne: Int, overlappingFour: Int) => fiveSegmentLookups.getOrElse((overlappingOne, overlappingFour), "")),
    (6, (overlappingOne: Int, overlappingFour: Int) => sixSegmentLookups.getOrElse((overlappingOne, overlappingFour), "")),
  )


  val countOfUniques = inputsToOutputs.values.map(_.trim)
    .flatMap(_.split(" ")).map(_.length)
    .count(uniqueSegmentLookup.keys.toSet.contains(_))
  println("part1:", countOfUniques)

  val decoded = inputsToOutputs.map(decodeSevenSegment).sum
  println("part2:", decoded)




  def decodeSevenSegment(keyValue: (String, String)): Int = {
    val keyNumbers = keyValue._1.split(" ")
    val valueNumbers = keyValue._2.split(" ")

    val oneSegments = (keyNumbers ++ valueNumbers).find(_.length == 2).get
    val fourSegments = (keyNumbers ++ valueNumbers).find(_.length == 4).get

    valueNumbers.map(numberString => uniqueSegmentLookup.getOrRun(
      numberString.length, () =>
        segmentLookupTable(numberString.length)(
          numberString.intersect(oneSegments).length,
          numberString.intersect(fourSegments).length
        ))).mkString.toInt
  }

  implicit class BetterMap[A, B](val m: Map[A, B]) {
    def getOrRun(key: A, provider: () => B) = m.get(key) match {
      case Some(value) => value
      case None => provider()
    }
  }
}


object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day8/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day8/input.txt")
  val shared: Inputs.Value = Value("../../data/day8.txt")
}
