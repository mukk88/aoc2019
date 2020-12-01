package fuel;

import org.scalatest.FunSuite

class FuelTest extends FunSuite {
  test("required") {
    val c = new FuelCalculator() 
    assert(c.fuelRequired(12) === 2)
    assert(c.fuelRequired(14) === 2)
    assert(c.fuelRequired(1969) === 966)
    assert(c.fuelRequired(100756) === 50346)
  }
}