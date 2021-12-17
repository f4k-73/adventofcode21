package de.NiklasBara.adventOfCode2021.day11

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}


object Day11 extends App {
  val inputs = InputReader(AdventDays.ELEVEN, InputTypes.local).lines.map(_.toCharArray.map(_.asDigit).toSeq)

  var flashes = 0

  val offsets = Seq((-1, 0), (0, -1), (1, 0), (0, 1), (-1, -1), (1, 1), (-1, 1), (1, -1))
  var flashCounter = 0;

  var res = inputs.simulateSteps(100)
  println("part1: ", flashes)
  println("part2: ", inputs.simulateUntilSynchronized)

  implicit class Better2dInts(s: Seq[Seq[Int]]) {

    def simulateUntilSynchronized: Int = {
      var count = 0
      var copy = s
      while(!copy.isSynchronized){
        copy = copy.simulateStep
        count += 1
      }
      count
    }

    def isSynchronized: Boolean = {
      s.flatten.toSet.size == 1
    }


    def get(y: Int, x: Int): Int = {
      if (x < 0 || y < 0) {
        0
      } else if (y > s.length - 1 || x > s(y).length - 1) {
        0
      } else {
        s(y)(x)
      }
    }

    def simulateStep: Seq[Seq[Int]] = {
      s.incrementAll(1).handleFlashes
    }

    def simulateSteps(amount: Int): Seq[Seq[Int]] = {
      if (amount <= 0) {
        s
      } else {
        simulateStep.simulateSteps(amount - 1)
      }
    }

    def handleFlashes: Seq[Seq[Int]] = {
      var flashOccurred = false
      var incremented: Seq[(Int, Int)] = Seq()
      val flashedArray = s.zipWithIndex.map(rowAndY => {
        rowAndY._1.zipWithIndex.map(cellAndX => {
          if (s.get(rowAndY._2, cellAndX._2) > 9) {
            flashes += 1
            flashOccurred = true
            incremented = incremented.appended(rowAndY._2, cellAndX._2)
            0
          } else {
            cellAndX._1
          }
        })
      }).incrementAllContained(1, getNeighbors(incremented))

      if (flashOccurred) {
        flashedArray.handleFlashes
      } else {
        flashedArray
      }
    }

    def getNeighbors(points: Seq[(Int, Int)]): Seq[(Int, Int)] = {
      points.flatMap(point => offsets.map(off => (point.y + off.y, point.x + off.x)))
    }

    def incrementAllContained(n: Int, contained: Seq[(Int, Int)]): Seq[Seq[Int]] = {
      s.zipWithIndex.map(rowAndIndex =>
        rowAndIndex._1.zipWithIndex.map(cellAndIndex => {
          incrementIfValid(n, contained, rowAndIndex._2, cellAndIndex._2, cellAndIndex._1)
        })
      )
    }

    private def incrementIfValid(n: Int, contained: Seq[(Int, Int)], y: Int, x: Int, value: Int): Int = {
      if (value != 0) {
        value + (n * contained.count(point => point == (y, x)))
      } else {
        0
      }
    }

    def incrementAll(n: Int): Seq[Seq[Int]] = {
      setAll(_ + n)
    }

    def setAll(function: Function[Int, Int]): Seq[Seq[Int]] = {
      s.map(row => row.map(function.apply))
    }

  }

  implicit class Point(p: (Int, Int)) {
    def y: Int = p._1

    def x: Int = p._2
  }
}