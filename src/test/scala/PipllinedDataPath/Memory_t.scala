package PiplinedDataPath
import chisel3.iotesters.PeekPokeTester
class Memory_t(c:Memory) extends PeekPokeTester(c){
 //     poke(c.io.alu_branch,1)
 //     poke(c.io.control_branch,1)
 //     poke(c.io.alu_out,16)
 //     poke(c.io.regfile_readdata2,1)
 //     poke(c.io.control_memread,1)
 //     poke(c.io.control_memwrite,1)
 //     poke(c.io.InstructionMem_out_11_7_bits,13)
 //    // expect(c.io.dataread,4)
 //    step(1)
 //     expect(c.io.branch,1)
 //     expect(c.io.InstructionMem_5_bits,13)
  step(1)
  step(1)
  step(1)
  step(1)
 step(1)
  step(1)
  step(1)
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