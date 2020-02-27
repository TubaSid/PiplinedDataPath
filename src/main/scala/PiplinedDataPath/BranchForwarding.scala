package PiplinedDataPath
import chisel3._
import chisel3.util.Cat
class BranchForwarding extends Module{
    val io=IO(new Bundle{
        val readreg1=Input(UInt(5.W))
        val readreg2=Input(UInt(5.W))
        val exe_writereg=Input(UInt(5.W))
        val mem_writereg=Input(UInt(5.W))
        val wb_writereg=Input(UInt(5.W))
        val exe_aluout=Input(SInt(32.W))
        val mem_aluout=Input(SInt(32.W))
        val wb_aluout=Input(SInt(32.W))
        val readdata1=Input(SInt(32.W))
        val readdata2=Input(SInt(32.W))
        val branchmux1=Output(SInt(32.W))
        val branchmux2=Output(SInt(32.W))
        //val memread

    })
    //mux1
    when(io.readreg1===io.exe_writereg)
    {
     io.branchmux1:=io.exe_aluout
    }
    .elsewhen(io.readreg1===io.mem_writereg)
    {
     io.branchmux1:=io.mem_aluout
    }
    .elsewhen(io.readreg1===io.wb_writereg)
    {
     io.branchmux1:=io.wb_aluout
    }
    .otherwise
    {
        io.branchmux1:=io.readdata2
    }
    //mux2
    when (io.readreg2===io.exe_writereg)
    {
     io.branchmux2:=io.exe_aluout
    }
    .elsewhen(io.readreg2===io.mem_writereg)
    {
     io.branchmux2:=io.mem_aluout
    }
    .elsewhen(io.readreg2===io.wb_writereg)
    {
     io.branchmux2:=io.wb_aluout
    }
    .otherwise
    {
     io.branchmux2:=io.readdata2
    }

    }