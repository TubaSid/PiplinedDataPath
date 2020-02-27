package PiplinedDataPath
import chisel3._
import chisel3.util.Cat
class Forwardinglogic extends Module{
    val io=IO(new Bundle{
        val readreg1=Input(UInt(5.W))
        val readreg2=Input(UInt(5.W))
        val mem_writereg=Input(UInt(5.W))
        val wb_writereg=Input(UInt(5.W))
        val mem_regwrite=Input(UInt(1.W))
        val wb_regwrite=Input(UInt(1.W))
        //output
        val fwdA=Output(UInt(2.W))
        val fwdB=Output(UInt(2.W))
    })
    
    //fwdA
    when((io.mem_regwrite === 1.U) && (io.mem_writereg =/= 0.U) && (io.mem_writereg === io.readreg1)) 
    {
        io.fwdA := "b01".U
    } 
    .elsewhen((io.wb_regwrite === 1.U) && (io.wb_writereg =/= 0.U) && ~((io.mem_regwrite === 1.U) && (io.mem_writereg =/= 0.U) && (io.mem_writereg === io.readreg1)) && (io.wb_writereg === io.readreg1)) 
    {
        io.fwdA := "b10".U
    }
    .otherwise
    {
        io.fwdA:="b11".U
    }
    //fwdb
    when(io.mem_regwrite === 1.U && io.mem_writereg =/= 0.U && (io.mem_writereg === io.readreg2)) 
    {
        io.fwdB := "b01".U
    } 
    .elsewhen((io.wb_regwrite === 1.U) && (io.wb_writereg =/= 0.U) && ~((io.mem_regwrite === 1.U) && (io.mem_writereg =/= 0.U)  && (io.mem_writereg === io.readreg2))  && (io.wb_writereg === io.readreg2)) 
    {
        io.fwdB := "b10".U
    } 
    .otherwise
    {
        io.fwdB:="b11".U
    }
    

}