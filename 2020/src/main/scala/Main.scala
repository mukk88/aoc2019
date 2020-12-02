import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import pwvalidator._

object Main extends App {
  val fileStream = getClass.getResourceAsStream("day2.txt")
  val lines = Source.fromInputStream(fileStream).getLines
  val input = lines.to(ArrayBuffer)
  val v = new PwValidator(input)
  println(v.validCount)
}