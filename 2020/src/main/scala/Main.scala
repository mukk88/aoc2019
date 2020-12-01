import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main extends App {
  val fileStream = getClass.getResourceAsStream("day1.txt")
  val lines = Source.fromInputStream(fileStream).getLines
  val input = lines
  for (line <- input) {
    println(line)
  }
}