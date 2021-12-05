package de.NiklasBara.adventOfCode2021.day5

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day5 extends App {
  val INPUT_PATH = Inputs.test
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()
  val inputs = lines.map(
      _.split("->").map(pointStr => {
        val ints = pointStr.split(",").map(_.trim.toInt)
        Point(ints.head, ints.last)
      })
  )
/*
  val pointByCount = inputs.map(Line(_).fillIn()).flatMap(_.points)
    .groupMapReduce(identity)(_ => 1)(_ + _)
  val res = pointByCount.count(_._2 >= 2)

  printBoard(pointByCount, 10)
  println(s"Part1: $res")
*/

  val diagPointByCount = inputs.map(Line(_).fillInWithDiagonals()).flatMap(_.points)
    .groupMapReduce(identity)(_ => 1)(_ + _)
  val res2 = diagPointByCount.count(_._2 >= 2)

  printBoard(diagPointByCount, 11)
  println(s"Part2: $res2")

  def printBoard(board :Map[Point, Int], size: Int) = {
    for (y <- 0 until size){
      for (x <- 0 until size){
        print(show(board.get(Point(x,y))))
        print(" ")
      }
      println()
    }
  }

  def show(x: Option[Int]) = x match {
    case Some(s) => s
    case None => "."
  }

  case class Point(x: Int, y: Int) {
    // Defining canEqual method
    def canEqual(a: Any) = a.isInstanceOf[Point]

    // Defining equals method with override keyword
    override def equals(that: Any): Boolean =
      that match
      {
        case that: Point => that.canEqual(this) &&
          this.hashCode == that.hashCode
        case _ => false
      }

    // Defining hashcode method
    override def hashCode: Int = {
      val prime = 31
      var result = 1
      result = prime * result + x;
      result = prime * result +
        (if (y == null) 0 else y.hashCode)
      return result
    }
  }

  case class Line(points: Seq[Point]) {

    private def inBetween(points: Seq[Point]) : Seq[Point] = {
      if(points.head == points.last){
        println(points.head, points.last)
      }


      if (points.head.x == points.last.x) {
        rangeBetween(points.map(_.y).sorted).map(Point(points.head.x, _))
      } else if (points.head.y == points.last.y){
        rangeBetween(points.map(_.x).sorted).map(Point(_, points.head.y))
      }else {
        Seq()
      }
    }

    private def diagonals(points: Seq[Point]) : Seq[Point] = {
      val xs = rangeBetween(points.map(_.x))
      val ys = rangeBetween(points.map(_.y))

      if(xs.size == ys.size){
        xs.zipWithIndex.map(x => Point(x._1, ys(x._2)))
      } else {
        Seq()
      }
    }

    private def rangeBetween(ints: Seq[Int]) = {
      if (ints.head < ints.last) {
        ints.head to ints.last
      } else {
        ints.head to ints.last by -1
      }
    }
    def fillIn(): Line = {
      this.copy(
        points = inBetween(points)
      )
    }

    def fillInWithDiagonals(): Line = {
      this.copy(
        points = diagonals(points).concat(inBetween(points))
      )
    }
  }
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test = Value("src/main/resources/day5/test_input.txt")
  val local = Value("src/main/resources/day5/input.txt")
  val shared = Value("../../data/day5.txt")
}