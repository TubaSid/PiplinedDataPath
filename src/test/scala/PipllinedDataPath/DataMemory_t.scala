package PiplinedDataPath
import chisel3.iotesters.PeekPokeTester
class DataMemory_t(c:DataMemory) extends PeekPokeTester(c){
    // poke(c.io.memwrite,1)
    // poke(c.io.memread,0)
    // poke(c.io.datawrite,3)
    // poke(c.io.address,2)
    // //expect(c.io.dataread,3)
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
