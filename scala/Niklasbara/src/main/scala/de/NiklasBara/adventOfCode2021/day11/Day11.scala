package de.NiklasBara.adventOfCode2021.day11

import java.nio.charset.StandardCharsets
import java.util.function.Predicate
import scala.io.Source

object Day11 extends App {
  val INPUT_PATH = Inputs.test
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().map(_.toCharArray.map(_.asDigit).toSeq).toSeq
  source.close()

  val offsets = Seq((-1, 0), (0, -1), (1, 0), (0, 1), (-1, -1), (1, 1), (-1, 1), (1, -1))


  var res = lines
  for(x <- 0 until 100){
   res = res.incrementAll(1).resolveFlashes.zeroFlashed
  }
  println(res.amountFlashes)

  implicit class Better2dInts(s: Seq[Seq[Int]]) {

    var amountFlashes = 0


    private def getOrElse(y: Int, x: Int, elseValue: Int): (Int, Int, Int) = {
      if (x < 0 || y < 0) {
        (elseValue, y, x)
      } else if (y > s.length - 1 || x > s(y).length - 1) {
        (elseValue, y, x)
      } else {
        (s(y)(x), y, x)
      }
    }

    def get(y: Int, x: Int): (Int, Int, Int) = {
      getOrElse(y, x, Int.MaxValue)
    }

    def findAll(predicate: Predicate[Int]): Seq[(Int, Int, Int)] = {
      var res = Seq[(Int, Int, Int)]()
      for (y <- s.indices) {
        for (x <- s(y).indices) {
          if (predicate.test(s.get(y, x)._1)) {
            res = res.appended(s.get(y, x))
          }
        }
      }
      res
    }

    def getNeighbour(y: Int, x: Int, offset: (Int, Int)): (Int, Int, Int) = get(y + offset._1, x + offset._2)

    def incrementAll(n: Int): Seq[Seq[Int]] = {
      s.map(row => row.map(cell => cell + n))
    }

    def incrementAllContainedIn(n: Int, container: Set[(Int, Int, Int)]): Seq[Seq[Int]] = {
      s.zipWithIndex.map(rowAndIndex => {
        val y = rowAndIndex._2
        rowAndIndex._1.zipWithIndex.map(colAndIndex => {
          val x = colAndIndex._2
          val cell = (s(y)(x), y, x)
          if (container.contains(cell)) {
            cell._1 + n
          } else {
            cell._1
          }
        })
      })

    }

    def resolveFlashes: Seq[Seq[Int]] = {
      val flashing = s.findAll(_.equals(9))
      if (flashing.isEmpty) {
        s
      } else {
        val neighboursToIncrement: Set[(Int, Int, Int)] = offsets.flatMap(offset => {
          flashing.map(cell => {
            s.getNeighbour(cell._2, cell._3, offset)
          })
        }).filter(_._1 < 9).toSet

        s.incrementAllContainedIn(1, neighboursToIncrement).incrementAllContainedIn(1, flashing.toSet).resolveFlashes
      }
    }

    def zeroFlashed: Seq[Seq[Int]] = {
      s.setAll {
        case 10 | 9 => 0
        case value => value
      }
    }

    def setAll(function: Function[Int, Int]): Seq[Seq[Int]] = {
      s.map(row => row.map(function.apply))
    }

    def simulate(steps: Int) = {
      if (steps > 0) {
        s.incrementAll(1).resolveFlashes.zeroFlashed
      } else {
        s
      }
    }

  }
}


object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day11/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day11/input.txt")
  val shared: Inputs.Value = Value("../../data/day11.txt")
}
