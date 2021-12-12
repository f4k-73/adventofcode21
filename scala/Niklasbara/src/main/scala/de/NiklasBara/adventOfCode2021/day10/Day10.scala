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
    '[' -> ']', ']' -> '[',
    '{' -> '}', '}' -> '{',
    '<' -> '>', '>' -> '<',
  )

  val scorePartOne = Map(
    ')' -> 3,
    ']' -> 57,
    '}' -> 1197,
    '>' -> 25137,
  )

  val scorePartTwo = Map(
    '(' -> 1L,
    '[' -> 2L,
    '{' -> 3L,
    '<' -> 4L,
  )

  println("part1:", lines.map(syntaxErrorScore).groupMapReduce(identity)(identity)(_ + _).values.sum)
  println("part2:", lines.zipMap(syntaxErrorScore)(autoCompleteScore).filter(_._1 == 0).map(_._2).sorted.middle)

  private def syntaxErrorScore(line: String) = {
    val stack = mutable.Stack[Char]()
    line.foldLeft(0)((number, char) => {
      if (number == 0) {
        char match {
          case ')' | '}' | ']' | '>' =>
            if (stack.pop() != pairs(char)) {
              scorePartOne(char)
            } else {
              number
            }
          case _ =>
            stack.push(char)
            number
        }
      } else {
        number
      }
    })
  }

  private def autoCompleteScore(line: String) = {
    line.foldLeft(Seq[Char]())((chars, char) => {
      char match {
        case ')' | '}' | ']' | '>' =>
          chars.tail
        case _ =>
          Seq(char).appendedAll(chars)
      }
    }).foldLeft(0L)((number, char) => {
      5 * number + scorePartTwo(char)
    })
  }

  implicit class SeqWithMiddle[T](s: Seq[T]) {
    def middle: T = s(s.length / 2)

    def zipMap[X, Y](mapFirst: Function[T, X])(mapSecond: Function[T, Y]): Seq[(X, Y)] = s.map(mapFirst).zip(s.map(mapSecond))
  }
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day10/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day10/input.txt")
  val shared: Inputs.Value = Value("../../data/day10.txt")
}
