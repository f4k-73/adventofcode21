package de.NiklasBara.adventOfCode2021.day10

import java.nio.charset.StandardCharsets
import scala.collection.mutable
import scala.io.Source

object Day10 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()

  val pairs = Map(
    '(' -> ')', ')' -> '(',
    '{' -> '}', '}' -> '{',
    '[' -> ']', ']' -> '[',
    '<' -> '>', '>' -> '<',
  )

  val score = Map(
    ')' -> 3,
    '}' -> 1197,
    ']' -> 57,
    '>' -> 25137,
  )

  println("part1:", lines.map(part1).groupMapReduce(identity)(identity)(_ + _).values.sum)


  private def part1(line: String) = {
    val stack = mutable.Stack[Char]()
    line.foldLeft(0)((number, char) => {
      if (number == 0) {
        char match {
          case ')' | '}' | ']' | '>' =>
            if (stack.pop() != pairs(char)) {
              score(char)
            } else {
              number
            }
          case _ =>
            stack.push(char)
            number
        }
      }
      else {
        number
      }
    }
    )
  }

  private def part2(line: String) = {

  }
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day10/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day10/input.txt")
  val shared: Inputs.Value = Value("../../data/day10.txt")
}
