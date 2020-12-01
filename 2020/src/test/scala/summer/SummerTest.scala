package summer

import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer

class SummerTest extends FunSuite {
  test("required") {
    val s = new Summer(ArrayBuffer[Int](2000, 10, 10)) 
    assert(s.sum(2020) === 200000)
  }
}