package PiplinedDataPath
import chisel3._
import chisel3.util.Cat
class Branchlogic extends Module{
    val io=IO(new Bundle{
        val insn_mem_14_12=Input(UInt(3.W))
        val inputA=Input(SInt(32.W))
        val inputB=Input(SInt(32.W))
        val branch_out=Output(UInt(1.W))
    })
    //beq
    when(io.insn_mem_14_12==="b000".U)
    {
        when(io.inputA === io.inputB) 
        {
            io.branch_out := 1.U
        } 
        .otherwise 
        {
            io.branch_out := 0.U
        }
    }
    //blt
    .elsewhen(io.insn_mem_14_12==="b100".U)
    {
        when(io.inputA < io.inputB)
        {
         io.branch_out := 1.U
        } 
        .otherwise 
        {
         io.branch_out := 0.U
        }
    }
    //bge
    .elsewhen(io.insn_mem_14_12==="b101".U)
    {
        when(io.inputA>=io.inputB)
        {
          io.branch_out := 1.U
        }
        .otherwise
        {
          io.branch_out := 0.U
        }
    }
    //bne
    .elsewhen(io.insn_mem_14_12==="b001".U)
    {
        when(io.inputA=/=io.inputB)
        {
         io.branch_out := 1.U
        }
        .otherwise 
        {
         io.branch_out := 0.U
        }
    }
    //bgeu
    .elsewhen(io.insn_mem_14_12==="b10110".U)
    {
        when(io.inputA >= io.inputB)
        {
         io.branch_out:= 1.U
        }
        .otherwise
        {
          io.branch_out := 0.U
        }
    }
    //bltu
    .elsewhen(io.insn_mem_14_12==="b00011".U)
    {
    when(io.inputA < io.inputB) 
        {
           io.branch_out:= 1.U
        } 
     .otherwise 
        {
          io.branch_out := 0.U
        }
        
    }
    .otherwise
    {
        io.branch_out:=0.U
    }
    
    }








        