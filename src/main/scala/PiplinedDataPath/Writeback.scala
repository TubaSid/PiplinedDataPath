package PiplinedDataPath
import chisel3._
import chisel3.util.Cat
class Writeback extends Module{
    val io=IO(new Bundle{
        val regwrite=Input(UInt(1.W))
        val dataread=Input(SInt(32.W))
        val alu_result=Input(SInt(32.W))
        val memtoreg=Input(UInt(1.W))
        val InstructionMem_out_11_7_bit=Input(UInt(5.W))
        //output
        val regwriteOut=Output(UInt(1.W))
        val writedata_mux=Output(SInt(32.W))
        val InstructionMem_out_11_7_bitOut=Output(UInt(5.W))
    })
    io.regwriteOut:=io.regwrite
    io.InstructionMem_out_11_7_bitOut:=io.InstructionMem_out_11_7_bit
    when(io.memtoreg==="b0".U)
     {
        io.writedata_mux:=io.alu_result
     }
     .otherwise
     {
       io.writedata_mux:=io.dataread
     }
    
    }