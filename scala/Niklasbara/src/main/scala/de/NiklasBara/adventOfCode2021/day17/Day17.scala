package de.NiklasBara.adventOfCode2021.day17

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}

object Day17 extends App {

  val target = InputReader(AdventDays.SEVENTEEN, InputTypes.local).lines.head.parseToTarget

  val startCoord = Point(0, 0)


  val options: Seq[Point] = {
    (0 until target.maxX).flatMap(x => {
      (target.minY until -target.minY).map(y => {
        Point(y, x)
      })
    })
  }

  var foo = options.map(Probe(startCoord, _))

  println(foo.size)

  val maxY = options.map(Probe(startCoord, _)).filter(_.doesHit(target)).flatMap(_.allCoordinatesUntilHitOrMiss(target)).maxBy(_.y).y

  println(maxY)

  case class Target(minX: Int, maxX: Int, minY: Int, maxY: Int) {
    def missed(point: Point): Boolean = point.x > maxX || point.y < maxY

    def hit(point: Point): Boolean = within(point.x, minX, maxX) && within(point.y, minY, maxY)

    private def within(value: Int, min: Int, max: Int): Boolean = value >= min && value <= max
  }

  case class Probe(coordinates: Point, velocity: Point) {
    def next: Probe = Probe(coordinates + velocity, nextVelocity)

    def allCoordinatesUntilHitOrMiss(target: Target): Seq[Point] = {
      if (target.hit(this.coordinates) || target.missed(this.coordinates)) {
        Seq(this.coordinates)
      } else {
        this.next.allCoordinatesUntilHitOrMiss(target).appended(this.coordinates)
      }
    }

    def doesHit(target: Target): Boolean = {
      if (target.hit(this.coordinates)) {
        true
      } else if (target.missed(this.coordinates)) {
        false
      } else {
        this.next.doesHit(target)
      }
    }

    private def nextXVelocity: Int = if (velocity.x < 0) {
      1
    } else if (velocity.x > 0) {
      -1
    } else {
      0
    }

    private def nextVelocity: Point = Point(velocity.y - 1, nextXVelocity)

  }

  case class Point(y: Int, x: Int) {
    def +(other: Point): Point = Point(y + other.y, x + other.x)
  }

  implicit class ParsableString(s: String) {
    def parseToTarget: Target = {
      val numbers = s.replaceAll("target area: ", "")
        .replaceAll(", ", " ")
        .replaceAll("\\.\\.", " ")
        .replaceAll("(x=|y=)", "")
        .split(" ").map(_.toInt)
      Target(numbers(0), numbers(1), numbers(2), numbers(3))
    }
  }
}
