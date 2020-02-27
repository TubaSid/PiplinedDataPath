package PiplinedDataPath
import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class InstructionMemory extends Module{
    val io=IO(new Bundle{
        val write_add=Input(UInt(10.W))
        val read_data=Output(UInt(32.W))
        
    })
    val memory = Mem(1024, UInt(32.W))
    loadMemoryFromFile(memory, "/home/tuba/Desktop/file.txt")
    io.read_data := memory.read(io.write_add)
}