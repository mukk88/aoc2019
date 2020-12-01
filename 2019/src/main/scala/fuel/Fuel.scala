package fuel;

class FuelCalculator() {
  def fuelRequired(x: Int): Int = {
    var total = 0
    var current = x
    while (current > 0) {
      current = ((current / 3) - 2).max(0)
      total += current
    }
    total
  }
}