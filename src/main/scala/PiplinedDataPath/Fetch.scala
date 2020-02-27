package PiplinedDataPath
import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class Fetch extends Module{
    val io=IO(new Bundle{
        val pc_in = Input(SInt(32.W))
        val pcoutRegOut = Output(SInt(32.W))
        val pc4RegOut=Output(SInt(32.W))
        val pc_out_11_2_bits=Output(UInt(20.W))
        //val read_data=Output(UInt(32.W))
        val pc_out=Output(SInt(32.W))
        //mux
        
        
        //mux1 input
        val imm_gen_sb_type=Input(SInt(32.W)) 
        ////pc+4
        val branch_out=Input(UInt(1.W)) //selection

        //mux2 input
        ////mux1output
        ////pc+4
        val imm_gen_uj_type=Input(SInt(32.W))
        val next_pc_sel=Input(UInt(2.W))//selection
        val jalr_out=Input(SInt(32.W))
        //insn mem
        val InstructionMeminp=Input(UInt(32.W))
        val InstructionMemRegOut=Output(UInt(32.W))
        //hazard detection
        val insn_mux=Input(UInt(1.W))
        val pc_mux=Input(UInt(1.W))
        //val pc_in=Input(SInt(32.W))
        //val pc_4=Input(SInt(32.W))
        val insn_mem=Input(UInt(32.W))
        val pc_4=Input(SInt(32.W))

     })

 val pc_in=Wire(SInt(32.W))
 //val io.io.pc_out=Wire(SInt(32.W))
  val reg = RegInit(0.S(32.W))
  val pc4=RegInit(0.S(32.W))
  val pcOut=RegInit(0.S(32.W))
  val InstructionMemOut=RegInit(0.U(32.W))
  val pc_4=Wire(SInt(32.W))
    //    val haz_detect = Module(new HazardDetection())

    //    haz_detect.io.pc_in:=.io.pcoutRegOut
    //   haz_detect.io.insn_mem:=.io.InstructionMemRegOut
    //  haz_detect.io.pc_4:=fetch.io.pc4RegOut

     

    //    io.pc_in:=haz_detect.io.pc_inOut
    //    io.insn_mem:=haz_detect.io.insn_memOut
    //    io.pc_4:=haz_detect.io.pc_4Out
    // when(io.pc_mux===1.U)
    // {
    //   pc_in:=io.pc_in
    // }
    // .otherwise
    // {
    // pc_in:=0.S
    // }
  
 reg:=pc_in ////**change io.pc_in to pc_in
 //val pcOut=Wire(SInt(32.W))
    io.pc_out:=reg
    //val pc4=Wire(SInt(32.W))
    pc_4:=reg+4.S
   // val write_add=Wire(UInt(20.W))
    
 
    
    when(io.pc_mux===1.U)
     {
        pc_in:=io.pc_4
     }
    //muxb
   .otherwise
   {
        when(io.next_pc_sel==="b00".U)
        {
        pc_in:=pc_4
        pcOut:=0.S


        }
         .elsewhen(io.next_pc_sel==="b01".U)
     {//muxa
     
        when(io.branch_out===1.U)
        {
            pc_in:=io.imm_gen_sb_type
        }
        .otherwise
        {
            pc_in:=pc_4
        }

        }
        .elsewhen(io.next_pc_sel==="b10".U)
        {
        pc_in:=io.imm_gen_uj_type

        }
     .otherwise
      {
        pc_in:=io.jalr_out

        }

    }

    //input from register
    io.pc_out_11_2_bits:=io.pc_out(11,2)
    //pcOut:=io.pc_out
    io.pcoutRegOut:=pcOut
    pc4:=pc_4
    io.pc4RegOut:=pc4
 
 //  pc4:=pc_4
 //  io.pc4RegOut:=pc4
    when(io.insn_mux===1.U)
    { 
     //     // pcOut:=io.pc_in//pcoutRegOut
     //  //     io.pcoutRegOut:=pcOut
     //     // pc4:=io.pc_4//pc4RegOut
     //     // io.pc4RegOut:=pc4
         InstructionMemOut:=io.insn_mem//InstructionMemRegOut
         
         pcOut:=io.pc_in
         
    }
    .otherwise
    {
        // pcOut:=io.pc_out
        // io.pcoutRegOut:=pcOut
        // pc4:=pc_4
        // io.pc4RegOut:=pc4
        //input in register
        InstructionMemOut:=io.InstructionMeminp //***change to InstructionMeminp
        
        pcOut:=io.pc_out
        

    }

 //   pcOut:=io.pc_in//pcoutRegOut
 //     io.pcoutRegOut:=pcOut
 //     pc4:=io.pc_4//pc4RegOut
 //     io.pc4RegOut:=pc4
 //     InstructionMemOut:=io.insn_mem//InstructionMemRegOut
 //     io.InstructionMemRegOut:=InstructionMemOut
 io.InstructionMemRegOut:=InstructionMemOut
 io.pcoutRegOut:=pcOut
}
//  write_add:=pcOut(21,2)

//     val memory = Mem(1024, UInt(32.W))
//     loadMemoryFromFile(memory, "/home/tuba/Desktop/file.txt")
//     io.read_data := memory.read(write_add)
