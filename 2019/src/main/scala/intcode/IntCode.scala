package intcode

import scala.collection.mutable.ArrayBuffer
import scala.annotation.meta.param
import scala.io.StdIn
import scala.math.BigInt

case class OpCode(
  code: Int,
  paramCount: Int,
  operation: (ArrayBuffer[BigInt], ArrayBuffer[BigInt], ArrayBuffer[Int], ArrayBuffer[BigInt], Int, Int) => (Int, Int)
)

case class Operation(
  code: Int,
  params: ArrayBuffer[Int],
  work: (ArrayBuffer[BigInt], ArrayBuffer[BigInt], ArrayBuffer[Int], ArrayBuffer[BigInt], Int, Int) => (Int, Int)
)

object OpCodeInfos {
    val MAX_MEM_SIZE = 2000

    def getAsInt(args: ArrayBuffer[BigInt], position: Int): Int = {
      args(position).intValue
    }

    val ADD = new OpCode(1, 3, (args, memory, inputs, outputs, pointer, rbase) => {
      var val1 = getAsInt(args, 0)
      var val2 = getAsInt(args, 1)
      var position = getAsInt(args, 2)
      memory(position) = memory(val1) + memory(val2)
      (pointer, rbase)
    })
    val MULTIPLY = new OpCode(2, 3, (args, memory, inputs, outputs, pointer, rbase) => {
      var val1 = getAsInt(args, 0)
      var val2 = getAsInt(args, 1)
      var position = getAsInt(args, 2)
      memory(position) = memory(val1) * memory(val2)
      (pointer, rbase)
    })
    val INPUT = new OpCode(3, 1, (args, memory, inputs, outputs, pointer, rbase) => {
      var position = getAsInt(args, 0)
      memory(position) = inputs.head
      inputs.remove(0)
      (pointer, rbase)
    })
    val OUTPUT = new OpCode(4, 1, (args, memory, inputs, outputs, pointer, rbase) => {
      var position = getAsInt(args, 0)
      outputs.addOne(memory(position))
      (pointer, rbase)
    })
    val JIFT = new OpCode(5, 2, (args, memory, inputs, outputs, pointer, rbase) => {
      var newPointer = pointer
      if (memory(getAsInt(args, 0)) != 0) {
        newPointer = memory(getAsInt(args, 1)).intValue
      }
      (newPointer, rbase)
    })
    val JIFF = new OpCode(6, 2, (args, memory, inputs, outputs, pointer, rbase) => {
      var newPointer = pointer
      if (memory(getAsInt(args, 0)) == 0) {
        newPointer = memory(getAsInt(args, 1)).intValue
      }
      (newPointer, rbase)
    })
    val LESSTHAN = new OpCode(7, 3, (args, memory, inputs, outputs, pointer, rbase) => {
      if (memory(getAsInt(args, 0)) < memory(getAsInt(args, 1))) {
        memory(getAsInt(args, 2)) = 1
      } else {
        memory(getAsInt(args, 2)) = 0
      }
      (pointer, rbase)
    })
    val EQUALS = new OpCode(8, 3, (args, memory, inputs, outputs, pointer, rbase) => {
      if (memory(getAsInt(args, 0)) == memory(getAsInt(args, 1))) {
        memory(getAsInt(args, 2)) = 1
      } else {
        memory(getAsInt(args, 2)) = 0
      }
      (pointer, rbase)
    })
    val RELATIVEBASE = new OpCode(9, 1, (args, memory, inputs, outputs, pointer, rbase) => {
      (pointer, rbase + memory(getAsInt(args, 0)).intValue)
    })
    val EXIT = new OpCode(99, 0, (args, memory, inputs, outputs, pointer, rbase) => {(pointer, rbase)})
}

class IntCode(codes: ArrayBuffer[BigInt], inputs: ArrayBuffer[Int]) {

  private var _codeMapping = Map(
    1 -> OpCodeInfos.ADD,
    2 -> OpCodeInfos.MULTIPLY,
    3 -> OpCodeInfos.INPUT,
    4 -> OpCodeInfos.OUTPUT,
    5 -> OpCodeInfos.JIFT,
    6 -> OpCodeInfos.JIFF,
    7 -> OpCodeInfos.LESSTHAN,
    8 -> OpCodeInfos.EQUALS,
    9 -> OpCodeInfos.RELATIVEBASE,
    99 -> OpCodeInfos.EXIT,
  )
  private var _position = 0
  private var _inputPosition = 0
  private var _outputs = ArrayBuffer[BigInt]()
  private var _relativeBase = 0
  private var _memory = ArrayBuffer.fill[BigInt](OpCodeInfos.MAX_MEM_SIZE)(0)

  for ((c, index) <- codes.zipWithIndex) {
    _memory(index) = c
  }

  def memory() = _memory

  private def consume(paramType: Int): BigInt = {
    val value: BigInt = paramType match {
      case 0 => _memory(_position)
      case 1 => _position
      case 2 => _memory(_position) + _relativeBase
      case _ => throw new RuntimeException("invalid param type")
    }
    _position+=1
    value
  }

  private def parseOpCode(): Operation = {
    val code = consume(0).intValue
    val opCode = code % 100
    val opCodeInfo = _codeMapping.get(opCode) match {
      case Some(opInfo) => opInfo
      case None => throw new RuntimeException("invalid code")
    }
    var paramsEncoded = code / 100
    val params = ArrayBuffer[Int]()
    for  (_ <- 0 to opCodeInfo.paramCount - 1) {
      params.addOne(paramsEncoded % 10)
      paramsEncoded = paramsEncoded / 10
    }
    new Operation(opCodeInfo.code, params, opCodeInfo.operation)
  } 

  private def processSingle(): Boolean = {
    val operation = parseOpCode()
    if (operation.code == 99) {
      return false
    }
    var params = ArrayBuffer[BigInt]()
    for(paramType <- operation.params) {
      params.addOne(consume(paramType))
    }
    val result = operation.work(params, _memory, inputs, _outputs, _position, _relativeBase)
    _position = result._1
    _relativeBase = result._2

    true
  }
  
  def process(): ArrayBuffer[BigInt] = {
    while (processSingle) {
      // println("debug", _memory.take(codes.length), _position, _relativeBase)
    }
    _outputs
  }
}