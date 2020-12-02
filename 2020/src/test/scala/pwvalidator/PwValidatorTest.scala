package pwvalidator

import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer

class PwValidatorTest extends FunSuite {
  test("required") {
    val validator = new PwValidator(ArrayBuffer[String](
      "1-3 a: abcde",
      "1-3 b: cdefg",
      "2-9 c: ccccccccc"
    ))
    assert(validator.validCount === 1)
  }
}