package de.NiklasBara.adventOfCode2021.day14

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}

object Day14 extends App {

  val inputs = InputReader(AdventDays.FOURTEEN, InputTypes.local).lines.filterNot(_.isEmpty)

  val polymerTemplate = inputs.take(1).head
  val insertions = inputs.slice(1, inputs.size)
    .map(_.split(" -> "))
    .map(str => (str(0), str(1)))


  println(polymerTemplate.insertMany(insertions, 10).partOneMath)


  implicit class PolymerTemplate(s: String) {

    def insert(insertions: Seq[(String, String)]): String = {
      val results = s.sliding(2)
        .map(slide => {
          val insertion = insertions.filter(ins => ins._1.equals(slide))
          if (insertion.isEmpty) {
            ""
          } else {
            insertion.head._2
          }
        })
      s.zip(results).map(a => s"${a._1}${a._2}").mkString + s.last

    }

    def insertMany(insertions: Seq[(String, String)], steps: Int): String = {
      if (steps == 0) {
        s
      } else {
        s.insert(insertions).insertMany(insertions, steps - 1)
      }

    }

    def partOneMath: Int = {
      val charCount = s.groupMapReduce(identity)(_ => 1)(Math.addExact)
      charCount.values.max - charCount.values.min
    }


  }

}
