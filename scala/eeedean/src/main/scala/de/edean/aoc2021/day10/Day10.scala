package de.edean.aoc2021.day10

import scala.io.Source
import scala.language.postfixOps

object Day10 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input10.txt"
  else "../../data/day10.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq
  val openingChars = "([{<"
  val closingChars = Map(')' -> '(', ']' -> '[', '}' -> '{', '>' -> '<')
  val scoreMap = Map(')' -> 3, ']' -> 57, '}' -> 1197, '>' -> 25137)

  def syntaxCheck(line: String, bracketStack: Vector[Character] = Vector()): Int = {
    val currentCharOpt = line.headOption
    if(currentCharOpt.isDefined) {
      val currentChar = currentCharOpt.get
      if (openingChars.contains(currentChar)) {
        syntaxCheck(line.drop(1), bracketStack :+ currentChar)
      } else {
        if(!bracketStack.isEmpty && bracketStack.last.equals(closingChars.get(currentChar).get)) {
          syntaxCheck(line.drop(1), bracketStack.init)
        } else {
          scoreMap.get(currentChar).get
        }
      }
    } else if (currentCharOpt.isEmpty && bracketStack.isEmpty) {
      0
    } else {
      0 // for now
    }

  }
  val syntaxErrors = input.map(str => (str, syntaxCheck(str))).filter(_._2!=0)
  println(syntaxErrors.map({case (str, n) => s"$str -> $n"}).mkString("\n"))
  println(s"part 1: ${syntaxErrors.map(_._2).sum}")
}