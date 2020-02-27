package PiplinedDataPath
import chisel3._
import chisel3.util.Cat
class Execute extends Module{
    val io=IO(new Bundle{
        val memwriteRegOut=Output(UInt(1.W))
        val memreadRegOut=Output(UInt(1.W))
        val regwriteRegOut=Output(UInt(1.W))
        val memtoregRegOut=Output(UInt(1.W))
        
        val memwrite=Input(UInt(1.W))
        val memread=Input(UInt(1.W))
        val regwrite=Input(UInt(1.W))
        val memtoreg=Input(UInt(1.W))


        val alu_resultRegOut=Output(SInt(32.W))
        val InstructionMem_out_11_7_bitsRegOut=Output(UInt(5.W))
        val fwdmux2RegOut=Output(SInt(32.W))
        //alu out for branch forwarding
        val alu_result=Output(SInt(32.W))



        //muxa
        val fwdmux1=Input(SInt(32.W))
        val pc=Input(SInt(32.W))
        val pc_4 = Input(SInt(32.W))
        val operand_a_sel=Input(UInt(2.W))
        
        //muxb
        val fwdmux2=Input(SInt(32.W))
        val imm_mux=Input(SInt(32.W))
        val operand_b_sel=Input(UInt(1.W))
        //alucntrl
        //val InstructionMem=Input(UInt(32.W))
        val aluop=Input(UInt(3.W))
        //output
        // val branch=Output(UInt(1.W))
        // val out=Output(SInt(32.W))
        // val InstructionMem_out_11_7_bits=Output(UInt(5.W))
        val InstructionMem_out_11_7_bits=Input(UInt(5.W))
        val InstructionMem_out_14_12_bits=Input(UInt(3.W))
        val InstructionMem_out_30_bits=Input(UInt(1.W))
        

        // val a:=Input(SInt(32.W))
        // val b:=Input(SInt(32.W))
        // val alucontrol=Input(UInt(5.W))
       // val alu_branch=Output(UInt(1.W))
        // val out=Output(SInt(32.W))
        val jalr_out=Output(SInt(32.W))
    })
     val memwriteOut=RegInit(0.U (1.W))
        val memreadOut=RegInit(0.U (1.W))
       val regwriteOut=RegInit(0.U (1.W))
        val memtoregOut=RegInit(0.U (1.W))
       val fwdmux2Out=RegInit(0.S(32.W))
     
     val alucontrol=Wire(UInt(5.W))
     //  val alu_result=Wire(SInt(32.W))
     val InstructionMem_out_11_7_bitsOut=RegInit(0.U(5.W))
     val alu_resultOut=RegInit(0.S(32.W))
     fwdmux2Out:=io.fwdmux2
     io.fwdmux2RegOut:=fwdmux2Out
    

     when(io.aluop==="b000".U )
    {
        alucontrol:=Cat("b0".U,io.InstructionMem_out_30_bits,io.InstructionMem_out_14_12_bits)
    }
    .elsewhen(io.aluop==="b100".U){
        alucontrol:="b00000".U
    }
    .elsewhen(io.aluop==="b101".U  ){
        alucontrol:="b00000".U
    }
    .elsewhen(io.aluop==="b010".U ){
        alucontrol:=Cat("b10".U,io.InstructionMem_out_14_12_bits)
    }
    .elsewhen(io.aluop==="b001".U ){
        when(io.InstructionMem_out_14_12_bits==="b101".U){
            alucontrol:=Cat("b0".U,io.InstructionMem_out_30_bits,io.InstructionMem_out_14_12_bits)
        }
        .otherwise{
        alucontrol:=Cat("b00".U,io.InstructionMem_out_14_12_bits)}
    }
    .elsewhen(io.aluop==="b011".U){
        alucontrol:="b11111".U
    }
    .elsewhen(io.aluop==="b110".U){
        alucontrol:="b00000".U
    }
    .otherwise{
        alucontrol:=DontCare
    }

    var a=Wire(SInt(32.W))
    var b=Wire(SInt(32.W))
    //mux1
    when(io.operand_a_sel==="b00".U)
    {
       a:=io.fwdmux1
    }
    .elsewhen(io.operand_a_sel==="b01".U)
    {
        a:=io.pc
    }
    .elsewhen(io.operand_a_sel==="b10".U)
    {
        a:=io.pc_4
    }
     .otherwise
    {
        a:=io.fwdmux1
    }
    //mux2
    when(io.operand_b_sel==="b0".U)
    {
       b:=io.fwdmux2
        
    }
    .otherwise
    {
        b:=io.imm_mux

    }
    
    


    //add,addi
    when(alucontrol==="b00000".U)
    {
        io.alu_result:=a+b
    }
    //slt.slti
    .elsewhen(alucontrol==="b00010".U)
    {
        //io.alu_result:=a<<b(4,0)
        when(a<b)
        {
            io.alu_result:=1.S
        }
        .otherwise
        {
            io.alu_result:=0.S
        }

    }
    // .
    // // //beq
    // // .elsewhen(alucontrol==="b10000".U)
    // // {
    // //     when(a === b) 
    // //     {
    // //         io.alu_result := 1.S
    // //     } 
    // //     .otherwise 
    // //     {
    // //         io.alu_result := 0.S
    // //     }
    // // }

    // .
    // // //bge
    // // .elsewhen(alucontrol==="b10101".U)
    // // {
    // //     when(a>=b)
    // //     {
    // //       io.alu_result := 1.S
    // //     }
    // //     .otherwise
    // //     {
    // //       io.alu_result := 0.S
    // //     }
    // // }


    //xor,xori
    .elsewhen(alucontrol==="b00100".U)
    {
        io.alu_result := a ^ b
    }
    //sll,slli
    .elsewhen(alucontrol==="b00001".U){
        io.alu_result:=a<<b(4,0)

    }
    //srl,srli
    .elsewhen(alucontrol==="b00101".U)
    {
      when((a >=b(4,3).asSInt)) 
      {
        io.alu_result := 1.S
      }
    .otherwise
        {
            io.alu_result := 0.S
        }
    }
    //or,ori
    .elsewhen(alucontrol==="b00110".U)
    {
        io.alu_result := a | b
    }
    //and,andi
    .elsewhen(alucontrol==="b00111".U)
    {
        io.alu_result:= a & b
        
    }
    //sub
    .elsewhen(alucontrol==="b01000".U)
    {
     io.alu_result := a - b
    }
    // .
    // // //bne
    // // .elsewhen(alucontrol==="b10001".U)
    // // {
    // //   when(a=/=b)
    // //     {
    // //      io.alu_result := 1.S
    // //     }
    // //     .otherwise 
    // //     {
    // //      io.alu_result := 0.S
    // //     }
    // // }

    // .
    // // //blt
    // // .elsewhen(alucontrol==="b10100".U)
    // // {
    // //     when(a < b)
    // //     {
    // //      io.alu_result := 1.S
    // //     } 
    // //     .otherwise 
    // //     {
    // //      io.alu_result := 0.S
    // //     }
    // // }
    //sra,srai
    .elsewhen(alucontrol==="b01101".U)
    {
     io.alu_result := a >> b(4,0)
    }
       
    //sltui,sltu,bltu
    .elsewhen(alucontrol==="b00011".U)
    {
    when(a.asUInt < b.asUInt) 
        {
           io.alu_result:= 1.S
        } 
     .otherwise 
        {
          io.alu_result := 0.S
        }
        
    }
    // .
    // // //bgeu
    // // .elsewhen(alucontrol==="b10110".U)
    // // {
    // //     when(a.asUInt >= b.asUInt)
    // //     {
    // //      io.alu_result:= 1.S
    // //     }
    // //     .otherwise
    // //     {
    // //       io.alu_result := 0.S
    // //     }
    // // }
    //jal,jalr
    .elsewhen(alucontrol==="b11111".U)
    {
      io.alu_result:=a
    }
    .otherwise
    {
        io.alu_result:=DontCare
    }
    // .
    // // when(alucontrol(4,3)==="b10".U && io.alu_result===1.S)
    // // {
    // //     io.alu_branch:=1.U
    // // }
    // // .otherwise{
    // //     io.alu_branch:=0.U
    // // }
    
    //reg_input
    memwriteOut:=io.memwrite
    memreadOut:=io.memread
    regwriteOut:=io.regwrite
    memtoregOut:=io.memtoreg
 InstructionMem_out_11_7_bitsOut:=io.InstructionMem_out_11_7_bits
 alu_resultOut:=io.alu_result
    //reg_output
    io.memwriteRegOut:=memwriteOut
    io.memreadRegOut:=memreadOut
    io.regwriteRegOut:=regwriteOut
    io.memtoregRegOut:=memtoregOut
    io.InstructionMem_out_11_7_bitsRegOut:=InstructionMem_out_11_7_bitsOut
    io.alu_resultRegOut:=alu_resultOut
    io.jalr_out:=(io.fwdmux1+io.imm_mux)&(4294967294L.S)
}
