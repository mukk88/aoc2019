package pwvalidator

import scala.collection.mutable.ArrayBuffer

class PwInfo(min: Int, max: Int, letter: Char, password: String) {
  def valid = password(min) == letter ^ password(max) == letter
}

class PwValidator(lines: ArrayBuffer[String]) {

  val matchingRegex = raw"(\d+)-(\d+)\s(\w{1}):\s(\w+)".r

  private def valid(line: String): Boolean = line match {
    case matchingRegex(min, max, char, str) => (new PwInfo(min.toInt - 1, max.toInt - 1, char.charAt(0), str)).valid
    case default => false
  }

  def validCount(): Int = lines.foldLeft(0) { (a,b) =>
    a + (if (valid(b)) 1 else 0)
  }
}