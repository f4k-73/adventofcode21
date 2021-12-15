package de.edean.aoc2021.day10

import scala.io.Source
import scala.language.postfixOps

object Day10 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input10.txt"
  else "../../data/day10.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq
  val openingChars = Map('(' -> ')', '[' -> ']', '{' -> '}', '<' -> '>')
  val closingChars = Map(')' -> '(', ']' -> '[', '}' -> '{', '>' -> '<')
  val syntaxErrorScoreMap = Map(')' -> 3, ']' -> 57, '}' -> 1197, '>' -> 25137)

  def syntaxCheck(line: String, bracketStack: Vector[Character] = Vector(), lineCompletionHandler: Option[(Vector[Character], String) => Long] = Option.empty): Long = {
    val currentCharOpt = line.headOption
    if(currentCharOpt.isDefined) {
      val currentChar = currentCharOpt.get
      if (openingChars.keys.exists(_.equals(currentChar))) {
        syntaxCheck(line.drop(1), bracketStack :+ currentChar, lineCompletionHandler)
      } else {
        if(!bracketStack.isEmpty && bracketStack.last.equals(closingChars.get(currentChar).get)) {
          syntaxCheck(line.drop(1), bracketStack.init, lineCompletionHandler)
        } else {
          syntaxErrorScoreMap.get(currentChar).get
        }
      }
    } else if (currentCharOpt.isEmpty && bracketStack.isEmpty) {
      0
    } else {
      lineCompletionHandler.map(f => f(bracketStack, "")).getOrElse(0)
    }

  }
  val checkedLines = input.map(str => (str, syntaxCheck(str)))
  val corruptedLines = checkedLines.filter(_._2 != 0)
  println(corruptedLines.map({ case (str, n) => s"$str -> $n" }).mkString("\n"))
  println(s"part 1: ${corruptedLines.map(_._2).sum}")

  val completionScoreMap = Map(')' -> 1, ']' -> 2, '}' -> 3, '>' -> 4)
  val lineCompletion: (Vector[Character], String) => Long = (bracketStack: Vector[Character], completion: String) => {
    def calcScore(completion: String, currentScore: Long = 0): Long = {
      if (completion.isEmpty)
        currentScore
      else
        calcScore(completion.tail, currentScore * 5 + completionScoreMap.get(completion.head).get)
    }

    if (bracketStack.isEmpty) {
      calcScore(completion)
    } else {
      lineCompletion(bracketStack.init, completion :+ openingChars.get(bracketStack.last).get)
    }
  }
  val incompleteLines = checkedLines.filter(_._2 == 0).map(t => (t._1, syntaxCheck(line = t._1, lineCompletionHandler = Some(lineCompletion))))
  println(incompleteLines.map(t => s"${t._1} -> ${t._2}").mkString("\n"))
  println(s"part 2: ${incompleteLines.sortBy(_._2).map(_._2)(incompleteLines.length/2)}")
}