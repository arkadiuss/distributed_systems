package it.arkadiuss.zookeeper

import org.apache.zookeeper.*
import java.io.IOException
import kotlin.system.exitProcess

object App {
    @Throws(IOException::class, KeeperException::class, InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if(args.isEmpty()) {
            println("Usage: program script_to_exec")
            exitProcess(1)
        }

        Executor(args[0]).run()
    }
}