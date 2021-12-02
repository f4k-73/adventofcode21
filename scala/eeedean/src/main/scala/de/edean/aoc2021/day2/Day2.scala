package de.edean.aoc2021.day2

import scala.io.Source

object Day2 extends App {

  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
                       System.getenv("USER").toLowerCase.equals("edean"))
                              "src/main/resources/input1.txt"
                         else "../../data/day1.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().toSeq

  def part1(lines: Seq[String]) = {
    val finalHorizontal = lines.filter(_.startsWith("forward")).map(_.substring(8)).map(_.toInt).sum
    val finalDepth = lines.filter(line => line.startsWith("up") || line.startsWith("down"))
      .map(line => line match {
        case _ if line.startsWith("up")   => line.substring(3).toInt * -1
        case _ if line.startsWith("down") => line.substring(5).toInt
      })
      .sum
    val product = finalHorizontal * finalDepth
    println(product)
  }

  def part2(lines: Seq[String]) = {
    trait Message
    case class Forward(distance: Int)  extends Message
    case class Up(decreaseAim: Int)    extends Message
    case class Down(increasesAim: Int) extends Message

    val messages = lines.map(line => line match {
      case _ if line.startsWith("forward") => Forward(line.substring(8).toInt)
      case _ if line.startsWith("up")     => Up(line.substring(3).toInt)
      case _ if line.startsWith("down")   => Down(line.substring(5).toInt)
    })

    class Submarine(val aim: Int = 0, val horizontalDistance: Int = 0, val depth: Int = 0) {
      def apply(message: Message): Submarine = message match {
        case Forward(distance)  => new Submarine(aim, horizontalDistance + distance, depth + distance * aim)
        case Up(decreaseAim)    => new Submarine(aim - decreaseAim, horizontalDistance, depth)
        case Down(increasesAim) => new Submarine(aim + increasesAim, horizontalDistance, depth)
      }
    }

    var mySub = new Submarine()
    messages.foreach(msg => mySub = mySub.apply(msg))
    println(mySub.depth * mySub.horizontalDistance)

  }

  part1(input)
  part2(input)
}
