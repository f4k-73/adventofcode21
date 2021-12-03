package de.edean.aoc2021.day1

import java.util.stream.IntStream
import scala.collection.JavaConverters
import scala.io.Source


object Day1 extends App {

  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
                       System.getenv("USER").toLowerCase.equals("edean"))
                              "src/main/resources/input1.txt"
                         else "../../data/day1.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().map(_.toInt).toSeq

  def part1(seq: Seq[Int]) = {
    var counter = 0;
    for {
      i <- 1 to seq.length - 1
    } {
      if (seq(i - 1) < seq(i))
        counter += 1
    }
    println(counter)
  }

  def part2(seq: Seq[Int]) = {
    part1(seq.sliding(3).map(_.reduce((a, b) => a + b)).toSeq)
  }

  part1(input)
  part2(input)
}
