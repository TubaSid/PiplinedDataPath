package PiplinedDataPath
import chisel3.iotesters.PeekPokeTester
class Decode_t(c:Decode) extends PeekPokeTester(c){
  // poke(c.io.InstructionMem,7341715)
  // poke(c.io.pc,0)
  // step(1)
  // expect(c.io.memwrite,0)
  // expect(c.io.branch,0)
  // expect(c.io.memread,0)
  // expect(c.io.regwrite,1)
  // expect(c.io.memtoreg,0)
  // expect(c.io.aluop,1)
  // expect(c.io.operand_a_sel,0)
  // expect(c.io.operand_b_sel,1)
  // expect(c.io.extend_sel,0)
  // expect(c.io.next_pc_sel,0)
  // expect(c.io.i_imm,7)
  step(1)
  step(1)
  step(1)
  step(1)
  step(1)
  step(1)
  step(1)
  step(1)
  step(1)

  

  
}
