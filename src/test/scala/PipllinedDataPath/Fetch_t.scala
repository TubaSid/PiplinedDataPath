package PiplinedDataPath
import chisel3.iotesters.PeekPokeTester
class Fetch_t(c:Fetch) extends PeekPokeTester(c){
    //poke(c.io.pc_in,0)
    // poke(c.io.next_pc_sel,0)
    // poke(c.io.branch_out,0)
    // poke(c.io.imm_gen_sb_type,0)
    // poke(c.io.imm_gen_uj_type,0)
    // poke(c.io.jalr_out,0)
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