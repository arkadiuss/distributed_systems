package it.arkadiuss.zookeeper

class UserInputReader(private val printer: TreePrinter) : Runnable {
    override fun run() {
        while(true) {
            val command = readLine()?:continue
            val response = when(command) {
                "print" -> printer.print()
                else -> "Unknown command"
            }
            println(response)
        }
    }

}