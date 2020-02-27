package PiplinedDataPath
import chisel3._
class Memory extends Module{
     val io=IO(new Bundle{

     //branch logic
     //val alu_branch=Input(UInt(1.W))
     //val control_branch=Input(UInt(1.W))
     //inputs for datamem inputs
     val alu_result=Input(SInt(32.W))//5
     val fwdmux2=Input(SInt(32.W))//6
     val memread=Input(UInt(1.W))//2
     val memwrite=Input(UInt(1.W))//1
     //remaining inputs
     val regwrite=Input(UInt(1.W))//3
     val memtoreg=Input(UInt(1.W))//4
     val InstructionMem_out_11_7_bits=Input(UInt(5.W))//7
     val datareadinp=Input(SInt(32.W))//-
     //reg out
     val datareadRegOut=Output(SInt(32.W))
     val alu_resultRegOut=Output(SInt(32.W))
     val regwriteRegOut=Output(UInt(1.W))
     val memtoregRegOut=Output(UInt(1.W))
     val InstructionMem_out_11_7_bitRegOut=Output(UInt(5.W))


     //outputs for datamem inputs
     val alu_resultOut=Output(SInt(32.W))
     val fwdmux2Out=Output(SInt(32.W))
     val memreadOut=Output(UInt(1.W))
     val memwriteOut=Output(UInt(1.W))
     //val InstructionMem_5_bitsRegOut=Output(UInt(5.W))
     //val InstructionMem_out_11_7_bits=Input(UInt(5.W))
     
     //val branch=Output(UInt(1.W))
     //val dataread=Input(SInt(32.W))
     //val datareadRegOut=Input(SInt(32.W)

    })

 //     val InstructionMem_5_bitsOut=RegInit(0.U(5.W))
 //     InstructionMem_5_bitsOut:=io.InstructionMem_out_11_7_bits
 //   io.InstructionMem_5_bitsRegOut:=InstructionMem_5_bitsOut
  //val aluOut=RegInit(0.S(32.W))
  io.memreadOut:=io.memread
  io.memwriteOut:=io.memwrite
  io.fwdmux2Out:=io.fwdmux2
  io.alu_resultOut:=io.alu_result
    
    // when(io.alu_branch==="b1".U && io.control_branch==="b1".U)
    // {
    //     io.branch:=1.U
    // }
    // .otherwise
    // {
    //     io.branch:=0.U

    // }
    
    val datareadOut=RegInit(0.S(32.W))
    val alu_resultReg=RegInit(0.S(32.W))
    val regwriteOut=RegInit(0.U (1.W))
    val memtoregOut=RegInit(0.U (1.W))
    val InstructionMem_out_11_7_bitOut=RegInit(0.U (5.W))
    //inputs in register
    datareadOut:=io.datareadinp
    alu_resultReg:=io.alu_result
    regwriteOut:=io.regwrite
    memtoregOut:=io.memtoreg
    InstructionMem_out_11_7_bitOut:=io.InstructionMem_out_11_7_bits
    //from registers to output
    io.datareadRegOut:=datareadOut
    io.alu_resultRegOut:=alu_resultReg
    io.regwriteRegOut:=regwriteOut
    io.memtoregRegOut:=memtoregOut
    io.InstructionMem_out_11_7_bitRegOut:=InstructionMem_out_11_7_bitOut

 

}