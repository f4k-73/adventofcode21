package de.edean.aoc2021.day12

import scala.io.Source
import scala.language.postfixOps

object Day12 extends App {
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input12.txt"
  else "../../data/day12.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().filter(!_.isEmpty).toSeq

  case class Cave(name: String, neighbours: Seq[String] = Seq()) {
    val isSmol = name.head > 'Z'
  }
  def stringify(path: Seq[Cave]) = path.map(_.name).mkString("->")

  val edges = input.map(_.split('-')).map(strs => Seq(Cave(strs.head), Cave(strs.last))).flatMap(edge => Seq(edge, edge.reverse))
  val caves = edges.groupMapReduce(_.head.name)(edge => Seq(edge.last.name))((l, r) => l ++ r)  // map each room to it's neighbours
                   .map({case (cave, neighbours) => Cave(cave, neighbours)})                    // and create case class objects of em

  def findPath(start: Seq[Cave], end: String, forbiddenPaths: Seq[Seq[Cave]] = Seq()): (Seq[Cave], Seq[Seq[Cave]]) = {
    val forbiddenNeighbours = (forbiddenPaths.filter(_.size > start.size).filter(_.startsWith(start)).map(_(start.size)) ++ start.filter(_.isSmol))
      .map(_.name)
    val currentNodeOpt = start.lastOption
    if (currentNodeOpt.isDefined) {
      val currentNode = currentNodeOpt.get
      if (currentNode.name.equals(end))
        (start, forbiddenPaths)
      else {
        val potentialNext = currentNode.neighbours
          .filter(cave => !forbiddenNeighbours.contains(cave))
          .headOption
        if (potentialNext.isDefined) {
          findPath(start :+ potentialNext.flatMap(cave => caves.find(_.name.equals(cave))).get, end, forbiddenPaths)
        } else {
          findPath(start.init, end, forbiddenPaths :+ start)
        }
      }
    }
    else
      (Seq(), forbiddenPaths)
  }

  def findAllPaths(from: Seq[Cave], to: String,
                   foundPaths: Seq[Seq[Cave]] = Seq(),
                   forbiddenPaths: Set[Seq[Cave]] = Set(),
                   dropIfEmpty: Int = 1): Seq[Seq[Cave]] = {
    val nextPath = findPath(from, to, forbiddenPaths.toSeq)
    if (from.isEmpty) {
      println("from is empty")
      foundPaths
    } else {
      if(nextPath._1.isEmpty) {
        println(s"nextPath is empty, tried [${stringify(from)}]")
        findAllPaths(foundPaths.last.dropRight(dropIfEmpty), to, foundPaths, forbiddenPaths + from, dropIfEmpty + 1)
      } else {
        println(s"found a new path: [${stringify(nextPath._1)}] for [${stringify(from)}]")
        findAllPaths(nextPath._1.init, to, foundPaths :+ nextPath._1, forbiddenPaths + nextPath._1 ++ nextPath._2)
      }
    }
  }

  val startNode = caves.find(_.name.equals("start")).get
  val allPaths = findAllPaths(Seq(startNode), "end")
  var allPathsString = allPaths.map(stringify).mkString("\n")
  println(s"---\n${allPathsString}\nAmount of paths (part1): ${allPaths.size}")

}