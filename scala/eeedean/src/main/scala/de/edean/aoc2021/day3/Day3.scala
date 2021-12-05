package de.edean.aoc2021.day3

import scala.io.Source

object Day3 extends App {

  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
                       System.getenv("USER").toLowerCase.equals("edean"))
                              "src/main/resources/input3.txt"
                         else "../../data/day3.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().toSeq

  def part1(lines: Seq[String]) = {
    val gammaRateBinary = lines.map(_.toCharArray.map(digit => if (digit.toInt > 48) 1 else 0)) // map to Int Arry
      .reduce((left, right) => left.zip(right).map({case (left, right) => left + right}))       // count the ones
      .map(value => if (value > (lines.length.toDouble/2)) 1 else 0)                            // make it binary (if more than half of the entries have a 1 at that digit, it's a one eventually
    println(s"Gamma Rate Binary: ${gammaRateBinary.mkString}")
    val gammaRate = binaryToDecimal(gammaRateBinary)
    println(s"Gamma Rate: $gammaRate")
    val epsilonRateBinary = gammaRateBinary.map(value => if (value > 0) 0 else 1)
    println(s"Epsilon Rate Binary: ${epsilonRateBinary.mkString}")
    val epsilonRate = binaryToDecimal(epsilonRateBinary)
    println(s"Epsilon Rate: $epsilonRate")
    val powerConsumption = gammaRate * epsilonRate
    println(s"Power Consumption: $powerConsumption")
  }

  def part2(lines: Seq[String]) = {
    val linesAsDecimal    = lines.map(_.toCharArray.map(digit => if (digit.toInt > 48) 1 else 0).toSeq)

    def findCandidate(candidates: Iterable[Seq[Int]], favourizeOne: Boolean, currentDigit: Int = 0): Iterable[Int] = {
      if (candidates.size == 1)
        candidates.head
      else {
        val onesOfDigit = candidates.map(_(currentDigit)).sum
        val mostCommonDigit = if (favourizeOne) {
          if (onesOfDigit >= (candidates.size.toDouble / 2)) 1 else 0
        } else {
          if (onesOfDigit <  (candidates.size.toDouble / 2)) 1 else 0
        }
        findCandidate(candidates.filter(candidate => candidate(currentDigit) == mostCommonDigit), favourizeOne, currentDigit + 1)
      }
    }
    val oxygenGeneratorRatingBinary = findCandidate(linesAsDecimal, true)
    val oxygenGeneratorRating       = binaryToDecimal(oxygenGeneratorRatingBinary.toSeq)
    val co2ScrubberRatingBinary     = findCandidate(linesAsDecimal, false)
    val co2ScrubberRating           = binaryToDecimal(co2ScrubberRatingBinary.toSeq)
    val lifeSupportRating           = oxygenGeneratorRating * co2ScrubberRating
    println(s"Oxygen Generator Rating Binary: ${oxygenGeneratorRatingBinary.mkString}")
    println(s"Oxygen Generator Rating:        $oxygenGeneratorRating")
    println(s"CO2 Scrubber Rating Binary:     ${co2ScrubberRatingBinary.mkString}")
    println(s"CO2 Scrubber Rating:            $co2ScrubberRating")
    println(s"Life Support Rating:            $lifeSupportRating")
  }

  part1(input)
  println("---")
  part2(input)

  def binaryToDecimal(iterable: Seq[Int]): Int = {
    iterable.reverseIterator
      .zipWithIndex                                             // number the digits with a index
      .map({case (value, index) => value * Math.pow(2, index)}) // multiply the 0 or 1 with 2^i
      .sum.toInt                                                // collect the binary digit values
  }
}
