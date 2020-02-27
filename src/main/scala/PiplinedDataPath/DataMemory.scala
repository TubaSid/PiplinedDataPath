package PiplinedDataPath
import chisel3._
class DataMemory extends Module{
    val io=IO(new Bundle{
        val address=Input(UInt(8.W))
        val datawrite=Input(SInt(32.W))
        val memwrite=Input(UInt(1.W))
        val memread=Input(UInt(1.W))
        val dataread=Output(SInt(32.W))
    })
    
       val mem = Mem(1024, SInt(32.W))
    when(io.memwrite === "b1".U && io.memread === "b0".U) 
    {
        mem.write(io.address, io.datawrite)
        io.dataread := 0.S
    } 
    .otherwise 
    {
        io.dataread := (mem(io.address)).asSInt
    }
}