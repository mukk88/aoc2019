package intcode

import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer
import scala.math.BigInt

class IntCodeTest extends FunSuite {
  test("basic") {
    val codes = ArrayBuffer[BigInt](1,9,10,3,2,3,11,0,99,30,40,50)
    val inputs = ArrayBuffer[Int]()
    val ic = new IntCode(codes, inputs)
    ic.process()
    assert(ic.memory.head === 3500)
  }

  test("plus") {
    val codes = ArrayBuffer[BigInt](1,0,0,0,99)
    val inputs = ArrayBuffer[Int]()
    val ic = new IntCode(codes, inputs)
    ic.process()
    assert(ic.memory.head === 2)
  }

  test("times") {
    val codes = ArrayBuffer[BigInt](2,3,0,3,99)
    val inputs = ArrayBuffer[Int]()
    val ic = new IntCode(codes, inputs)
    ic.process()
    assert(ic.memory.head === 2)
  }

  test("repeat") {
    val codes = ArrayBuffer[BigInt](2,4,4,5,99,0)
    val inputs = ArrayBuffer[Int]()
    val ic = new IntCode(codes, inputs)
    ic.process()
    assert(ic.memory.head === 2)
  }

  test("setcode") {
    val codes = ArrayBuffer[BigInt](1,1,1,4,99,5,6,0,99)
    val inputs = ArrayBuffer[Int]()
    val ic = new IntCode(codes, inputs)
    ic.process()
    assert(ic.memory.head === 30)
  }

  test("equals8") {
    val codes = ArrayBuffer[BigInt](3,9,8,9,10,9,4,9,99,-1,8)
    val inputs = ArrayBuffer[Int](8)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 1)

    val codes2 = ArrayBuffer[BigInt](3,3,1108,-1,8,3,4,3,99)
    val inputs2 = ArrayBuffer[Int](8)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 1)
  }

  test("notequals8") {
    val codes = ArrayBuffer[BigInt](3,9,8,9,10,9,4,9,99,-1,8)
    val inputs = ArrayBuffer[Int](7)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 0)

    val codes2 = ArrayBuffer[BigInt](3,3,1108,-1,8,3,4,3,99)
    val inputs2 = ArrayBuffer[Int](7)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 0)
  }

  test("lt8") {
    val codes = ArrayBuffer[BigInt](3,9,7,9,10,9,4,9,99,-1,8)
    val inputs = ArrayBuffer[Int](7)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 1)

    val codes2 = ArrayBuffer[BigInt](3,3,1107,-1,8,3,4,3,99)
    val inputs2 = ArrayBuffer[Int](7)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 1)
  }

  test("nlt8") {
    val codes = ArrayBuffer[BigInt](3,9,7,9,10,9,4,9,99,-1,8)
    val inputs = ArrayBuffer[Int](8)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 0)

    val codes2 = ArrayBuffer[BigInt](3,3,1107,-1,8,3,4,3,99)
    val inputs2 = ArrayBuffer[Int](8)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 0)
  }

  test("jumpif0") {
    val codes = ArrayBuffer[BigInt](3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)
    val inputs = ArrayBuffer[Int](0)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 0)

    val codes2 = ArrayBuffer[BigInt](3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)
    val inputs2 = ArrayBuffer[Int](0)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 0)
  }

  test("dontjumpifnot0") {
    val codes = ArrayBuffer[BigInt](3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)
    val inputs = ArrayBuffer[Int](3)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 1)

    val codes2 = ArrayBuffer[BigInt](3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)
    val inputs2 = ArrayBuffer[Int](2)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 1)
  }

  test("triplecompare8") {
    val codes = ArrayBuffer[BigInt](3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104, 999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)
    val inputs = ArrayBuffer[Int](7)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === 999)

    val codes2 = ArrayBuffer[BigInt](3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104, 999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)
    val inputs2 = ArrayBuffer[Int](8)
    val ic2 = new IntCode(codes2, inputs2)
    val outputs2 = ic2.process()
    assert(outputs2.head === 1000)

    val codes3 = ArrayBuffer[BigInt](3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104, 999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)
    val inputs3 = ArrayBuffer[Int](20)
    val ic3 = new IntCode(codes3, inputs3)
    val outputs3 = ic3.process()
    assert(outputs3.head === 1001)
  }

  test("replicate") {
    val codes = ArrayBuffer[BigInt](109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
    val inputs = ArrayBuffer[Int](0)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.length === codes.length)
  }

  test("largenum") {
    val codes = ArrayBuffer[BigInt](1102,34915192,34915192,7,4,7,99,0)
    val inputs = ArrayBuffer[Int](0)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === BigInt("1219070632396864"))
  }

  test("largenum2") {
    val codes = ArrayBuffer[BigInt](104,BigInt("1125899906842624"),99)
    val inputs = ArrayBuffer[Int](0)
    val ic = new IntCode(codes, inputs)
    val outputs = ic.process()
    assert(outputs.head === BigInt("1125899906842624"))
  }
}