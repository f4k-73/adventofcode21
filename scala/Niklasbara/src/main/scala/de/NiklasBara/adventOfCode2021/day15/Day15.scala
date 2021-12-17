package de.NiklasBara.adventOfCode2021.day15

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}

import scala.collection.mutable

object Day15 extends App {
  val inputs = InputReader(AdventDays.FIFTEEN, InputTypes.test).lines.filterNot(_.isEmpty)
  val vertices = inputs.map(_.split("").map(_.toInt).toSeq)

  val offsets = Seq((-1, 0), (0, -1), (1, 0), (0, 1))

  println(vertices.breadthFirstSearch((0,0),(vertices.size, vertices.head.size)))


  implicit class TwoDimensionalSeq(s: Seq[Seq[Int]]) {
    def getVertex(y: Int, x: Int): Vertex = {
      if (y < 0 || y >= s.size) {
        Vertex((0, y, x))
      } else if (x < 0 || x >= s(y).size) {
        Vertex((0, y, x))
      } else {
        Vertex((s(y)(x), y, x))
      }
    }

    def getNeighbours(y: Int, x: Int): Seq[Vertex] = offsets
      .map(offset => getVertex(y + offset.y, x + offset.x))


    def breadthFirstSearch(startPoint: (Int, Int), destination: (Int,Int)) = {


    }

  }

  implicit class Vertex(v: (Int, Int, Int)) {
    def y: Int = v._2

    def x: Int = v._3

    def value: Int = v._1

  }

  implicit class Point(o: (Int, Int)) {
    def x: Int = o._2

    def y: Int = o._1
  }

}