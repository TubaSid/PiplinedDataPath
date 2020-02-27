package PiplinedDataPath
import chisel3._
class Top extends Module{
     val io=IO(new Bundle{
     val top_output=Output(SInt(32.W))
     val top_pc=Output(SInt(32.W))
     val fwdmux1=Output(SInt(32.W))
     val fwdmux2=Output(SInt(32.W))

     })
     val fetch = Module(new Fetch())
     val decode = Module(new Decode())
     val execute = Module(new Execute())
     val memory = Module(new Memory())
     val InsnMem = Module(new InstructionMemory())
     val datamemory = Module(new DataMemory())
     val writeback = Module(new Writeback())
     val branch_logic = Module(new Branchlogic())
     val fwdlogic = Module(new Forwardinglogic())
     val branchfwd = Module(new BranchForwarding())
     val haz_detect = Module(new HazardDetection())
     //HazardDetection input
     haz_detect.io.dec_memread:=decode.io.memreadRegOut
     haz_detect.io.dec_writereg:=decode.io.InstructionMem_out_11_7_bitsRegOut
     haz_detect.io.readreg1:=decode.io.readreg1
     haz_detect.io.readreg2:=decode.io.readreg2
     haz_detect.io.pc_in:=fetch.io.pcoutRegOut
     haz_detect.io.insn_mem:=fetch.io.InstructionMemRegOut
     haz_detect.io.pc_4:=fetch.io.pc4RegOut
     //HazardDetection output
     decode.io.hazard_mux:=haz_detect.io.hazard_mux
     fetch.io.pc_mux:=haz_detect.io.pc_mux
     fetch.io.insn_mux:=haz_detect.io.insn_mux
     fetch.io.pc_in:=haz_detect.io.pc_inOut
     fetch.io.insn_mem:=haz_detect.io.insn_memOut
     fetch.io.pc_4:=haz_detect.io.pc_4Out
     //forwardinglogic input
     fwdlogic.io.readreg1:=decode.io.readreg1RegOut
     fwdlogic.io.readreg2:=decode.io.readreg2RegOut
     fwdlogic.io.mem_writereg:=execute.io.InstructionMem_out_11_7_bitsRegOut
     fwdlogic.io.wb_writereg:=writeback.io.InstructionMem_out_11_7_bitOut
     fwdlogic.io.mem_regwrite:=execute.io.regwriteRegOut
     fwdlogic.io.wb_regwrite:=writeback.io.regwriteOut
     //fwdmux1
    when(fwdlogic.io.fwdA==="b00".U)
    {
    io.fwdmux1:=decode.io.readdata1RegOut
    }
    .elsewhen(fwdlogic.io.fwdA==="b01".U)
    {
    io.fwdmux1:=execute.io.alu_resultRegOut
    }
    .elsewhen(fwdlogic.io.fwdA==="b10".U)
    {
    io.fwdmux1:=writeback.io.writedata_mux
    }
    .otherwise
    {
    io.fwdmux1:=decode.io.readdata1RegOut
    }
    //fwmuux2
    when(fwdlogic.io.fwdB==="b00".U)
    {
    io.fwdmux2:=decode.io.readdata2RegOut
    }
    .elsewhen(fwdlogic.io.fwdB==="b01".U)
    {
    io.fwdmux2:=execute.io.alu_resultRegOut
    }
    .elsewhen(fwdlogic.io.fwdB==="b10".U)
    {
    io.fwdmux2:=writeback.io.writedata_mux
     }
    .otherwise
     {
    io.fwdmux2:=decode.io.readdata2RegOut
    }
    //branchlogic input
     branch_logic.io.inputA:=branchfwd.io.branchmux1
     branch_logic.io.inputB:=branchfwd.io.branchmux2
     branch_logic.io.insn_mem_14_12:=fetch.io.InstructionMemRegOut(14,12)
     //branchforwarding inputs
     branchfwd.io.readreg1:=decode.io.readreg1RegOut
     branchfwd.io.readreg2:=decode.io.readreg2RegOut
     branchfwd.io.exe_writereg:=decode.io.InstructionMem_out_11_7_bitsRegOut
     branchfwd.io.mem_writereg:=execute.io.InstructionMem_out_11_7_bitsRegOut
     branchfwd.io.wb_writereg:=writeback.io.InstructionMem_out_11_7_bitOut
     branchfwd.io.exe_aluout:=execute.io.alu_result
     branchfwd.io.mem_aluout:=execute.io.alu_resultRegOut
     branchfwd.io.wb_aluout:=writeback.io.writedata_mux
     branchfwd.io.readdata1:=decode.io.readdata1
     branchfwd.io.readdata2:=decode.io.readdata2
     //fetch output
     decode.io.pcOut:=fetch.io.pcoutRegOut
     decode.io.pc4Out:=fetch.io.pc4RegOut
     InsnMem.io.write_add:=fetch.io.pc_out_11_2_bits
     decode.io.InstructionMem:=fetch.io.InstructionMemRegOut
     fetch.io.InstructionMeminp:=InsnMem.io.read_data
     //decode output
     execute.io.pc:=decode.io.pcRegOut
     execute.io.pc_4:=decode.io.pc4RegOut
     execute.io.memwrite:=decode.io.memwriteRegOut
     execute.io.memread:=decode.io.memreadRegOut
     execute.io.regwrite:=decode.io.regwriteRegOut
     execute.io.memtoreg:=decode.io.memtoregRegOut
     execute.io.aluop:=decode.io.aluopRegOut
     execute.io.operand_a_sel:=decode.io.operand_a_selRegOut
     execute.io.operand_b_sel:=decode.io.operand_b_selRegOut
     fetch.io.next_pc_sel:=decode.io.next_pc_selOut
     execute.io.fwdmux1:=io.fwdmux1
     execute.io.fwdmux2:=io.fwdmux2
     fetch.io.imm_gen_sb_type:=io.fwdmux2
     fetch.io.imm_gen_uj_type:=decode.io.uj_immOut
     execute.io.imm_mux:=decode.io.imm_muxRegOut
     execute.io.InstructionMem_out_11_7_bits:=decode.io.InstructionMem_out_11_7_bitsRegOut
     execute.io.InstructionMem_out_14_12_bits:=decode.io.InstructionMem_out_14_12_bitsRegOut
     execute.io.InstructionMem_out_30_bits:=decode.io.InstructionMem_out_30_bitsRegOut
     //execute output
     memory.io.memwrite:=execute.io.memwriteRegOut
     memory.io.memread:=execute.io.memreadRegOut
     memory.io.regwrite:=execute.io.regwriteRegOut
     memory.io.memtoreg:=execute.io.memtoregRegOut
     memory.io.alu_result:=execute.io.alu_resultRegOut
     memory.io.InstructionMem_out_11_7_bits:=execute.io.InstructionMem_out_11_7_bitsRegOut
     memory.io.fwdmux2:=execute.io.fwdmux2RegOut
     fetch.io.jalr_out:=execute.io.jalr_out
     //branches and
     fetch.io.branch_out:=branch_logic.io.branch_out & decode.io.control_branchOut
     //memory output
     writeback.io.regwrite:=memory.io.regwriteRegOut
     writeback.io.alu_result:=memory.io.alu_resultRegOut
     writeback.io.dataread:=memory.io.datareadRegOut
     writeback.io.memtoreg:=memory.io.memtoregRegOut
     writeback.io.InstructionMem_out_11_7_bit:=memory.io.InstructionMem_out_11_7_bitRegOut
     //datamemory input
     datamemory.io.address:=memory.io.alu_resultOut(9,2)
     datamemory.io.datawrite:=memory.io.fwdmux2Out
     datamemory.io.memread:=memory.io.memreadOut
     datamemory.io.memwrite:=memory.io.memwriteOut
     memory.io.datareadinp:=datamemory.io.dataread
     //writeback output
     decode.io.regwrite_regfile:=writeback.io.regwriteOut
     decode.io.writedata:=writeback.io.writedata_mux
     decode.io.writereg:=writeback.io.InstructionMem_out_11_7_bitOut
     //top output
     io.top_output:=io.fwdmux2
     io.top_pc:=fetch.io.pc_out




    
     }