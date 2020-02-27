// See LICENSE.txt for license details.
package PiplinedDataPath

import chisel3._
import chisel3.iotesters.{Driver, TesterOptionsManager}
import utils.TutorialRunner

object Launcher {
  val tests = Map(
    "Decode" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Decode(), manager) {
        (c) => new Decode_t(c)
      }
    },
     "Execute" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Execute(), manager) {
        (c) => new Execute_t(c)
      }
    },
     "Fetch" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Fetch(), manager) {
        (c) => new Fetch_t(c)
      }
    },
    "Memory" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Memory(), manager) {
        (c) => new Memory_t(c)
      }
    },
    "DataMemory" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new DataMemory(), manager) {
        (c) => new DataMemory_t(c)
      }
    },
    "InstructionMemory" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new InstructionMemory(), manager) {
        (c) => new InstructionMemory_t(c)
      }
    },
    "Top" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Top(), manager) {
        (c) => new Top_t(c)
      }
    },
    "Branchlogic" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Branchlogic(), manager) {
        (c) => new Branchlogic_t(c)
      }
    },
    "Writeback" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Writeback(), manager) {
        (c) => new Writeback_t(c)
      }
    },
    "Forwardinglogic" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new Forwardinglogic(), manager) {
        (c) => new Forwardinglogic_t(c)
      }
    },
    "BranchForwarding" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new BranchForwarding(), manager) {
        (c) => new BranchForwarding_t(c)
      }
    },
    "HazardDetection" -> { (manager: TesterOptionsManager) =>
      Driver.execute(() => new HazardDetection(), manager) {
        (c) => new HazardDetection_t(c)
      }
    }
  )
  def main(args: Array[String]): Unit = {
    TutorialRunner("PiplinedDataPath", tests, args)
  }
}