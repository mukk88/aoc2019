import scala.io.Source
import scala.util.control.Breaks._
import intcode._
import scala.collection.mutable.ArrayBuffer
import scala.math.BigInt

object Main extends App {
  val fileStream = getClass.getResourceAsStream("day9.txt")
  val line = Source.fromInputStream(fileStream).getLines.next()
  val input = line.split(",").map(x => BigInt(x)).to(ArrayBuffer)
  val intCode = new IntCode(input, ArrayBuffer[Int](2))
  val processed = intCode.process()
  println(processed.mkString(", "))
}