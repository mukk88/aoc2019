package summer

import scala.collection.mutable.ArrayBuffer

class Summer(nums: ArrayBuffer[Int]) {
  def sum(target: Int): Int = {
    for {
      (first, i) <- nums.zipWithIndex
      (second, j) <- nums.zipWithIndex if j > i
      (third, k) <- nums.zipWithIndex if k > j
    } {
      if (first + second + third == target) {
        return first * second * third
      } 
    }
    1
  }
}