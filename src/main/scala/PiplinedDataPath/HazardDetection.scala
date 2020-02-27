package PiplinedDataPath
import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class HazardDetection extends Module{
    val io=IO(new Bundle{
        val dec_memread=Input(UInt(1.W))
        val dec_writereg=Input(UInt(5.W))
        val readreg1=Input(UInt(5.W))
        val readreg2=Input(UInt(5.W))
        val hazard_mux=Output(UInt(1.W))
        val pc_in=Input(SInt(32.W))
        val pc_4=Input(SInt(32.W))
        val insn_mem=Input(UInt(32.W))
        val pc_inOut=Output(SInt(32.W))
        val pc_4Out=Output(SInt(32.W))
        val insn_memOut=Output(UInt(32.W))
        val pc_mux=Output(UInt(1.W))
        val insn_mux=Output(UInt(1.W))

        
    })
    when (io.dec_memread===1.U &((io.dec_writereg === io.readreg1) ||(io.dec_writereg === io.readreg2)))
    {
       io.hazard_mux:=1.U
        io.insn_mux := 1.U
        io.pc_mux := 1.U   

    }
    .otherwise
    {
        io.hazard_mux:=0.U
        io.insn_mux := 0.U
        io.pc_mux := 0.U
    }
    io.pc_inOut:= io.pc_in
    io.insn_memOut:=io.insn_mem
    io.pc_4Out:=io.pc_4


}
