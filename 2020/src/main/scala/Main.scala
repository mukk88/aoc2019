import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import summer._

object Main extends App {
  val fileStream = getClass.getResourceAsStream("day1.txt")
  val lines = Source.fromInputStream(fileStream).getLines
  val input = lines.map(_.toInt).to(ArrayBuffer)
  val s = new Summer(input)
  // println(s.sum(2020))
  val sum = input.fold(0) { (z, i) =>
    z + i
  }
  println(sum)
}