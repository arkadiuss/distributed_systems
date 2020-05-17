package it.arkadiuss.zookeeper

import java.io.*
import java.util.*

class ProgramInputReader(
        streamName: String,
        private val inputStream: InputStream
): Runnable {
    private val baseLogsPath = "./logs"
    private val file = File("$baseLogsPath/$streamName.txt")

    override fun run() {
        file.createNewFile()
        val br = BufferedReader(InputStreamReader(inputStream))
        val writer = FileWriter(file)
        while (true) {
            val s = br.readLine() ?: break
            writer.write("[${Date()}] $s\n")
            writer.flush()
        }
        writer.close()
    }
}