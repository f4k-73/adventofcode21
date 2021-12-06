package de.edean.aoc2021.day6

import scala.io.Source
import scala.language.postfixOps
import scala.collection.parallel.CollectionConverters._

object Day6 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input6.txt"
  else "../../data/day6.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().toSeq
  val initialFishes = input.head.split(",").map(_.toInt)

  val daysToMaturity = 8
  val daysToReproduce = 6
  val daysToSimulate = 256

  def fishSimulation(fishes: Seq[Int], remainingDays: Int): Seq[Int] = {
    if (remainingDays == 0) fishes
    else {
      fishSimulation(fishes.par.flatMap(
        fish =>
          if (fish == 0)
            Seq(daysToReproduce, daysToMaturity)
          else
            Seq(fish - 1)).toIndexedSeq, remainingDays - 1)
    }
  }

  def fasterFishSimulation(fishes: Seq[Int], remainingDays: Int): Seq[Int] = {
    if (remainingDays == 0) fishes
    else if (remainingDays % daysToReproduce != 0) {
      val daysToCorrect = remainingDays % daysToReproduce
      val correctedInitialFishes = fishSimulation(fishes, daysToCorrect)
      fasterFishSimulation(correctedInitialFishes, remainingDays - daysToCorrect)
    } else {
      fasterFishSimulation(fishes.par.flatMap(
        fish =>
          if (fish < daysToReproduce)
            Seq(fish + 1, daysToMaturity-daysToReproduce + fish + 1)
          else
            Seq(fish - daysToReproduce)).toIndexedSeq, remainingDays - daysToReproduce)
    }
  }

  def ultimateFishSimulation(fishesPerSize: Map[Int, Long], remainingDays: Int): Map[Int, Long] = {
    if (remainingDays == 0) fishesPerSize
    else {
      ultimateFishSimulation(fishesPerSize.toSeq.flatMap({
        case (daysUntilReproduction, amount) =>
          if (daysUntilReproduction == 0)
            Seq(daysToReproduce -> (amount), daysToMaturity -> amount)
          else
            Seq(daysUntilReproduction - 1 -> amount)
      }).groupMapReduce(_._1)(_._2)((left, right) => left + right), remainingDays - 1)
    }
  }

  //println(fishSimulation(initialFishes, daysToSimulate))
  //println(fasterFishSimulation(initialFishes, daysToSimulate).size)
  println(ultimateFishSimulation((0 to 8).map(i => (i, initialFishes.filter(_ == i).size.toLong)).toMap, daysToSimulate).map(_._2).sum)

  /*(0 to 200).foreach(i => {
    val oldAmount = fasterFishSimulation(initialFishes, i).size
    val newAmount = newFishSimulation((0 to 8).map(i => (i, initialFishes.filter(_ == i).size)).toMap, i).map(_._2).sum
    if (oldAmount == newAmount) println(s"$i: $oldAmount") else System.err.println(s"$i: $oldAmount vs $newAmount")
  })*/
}