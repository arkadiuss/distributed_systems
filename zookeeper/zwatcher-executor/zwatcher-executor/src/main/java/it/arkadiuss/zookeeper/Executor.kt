package it.arkadiuss.zookeeper

import org.apache.zookeeper.AddWatchMode
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.io.InputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock

class Executor(
        private val execPath: String
) {
    private val zooKeeper: ZooKeeper = ZooKeeper("127.0.0.1:2184", 2000, Watcher { ev -> processEvent(ev) })
    private val lock = ReentrantLock()
    private val runningCondition = lock.newCondition()
    private var isRunning = true
    private val nodePath = "/z"
    private var executedProcess: Process? = null
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(4)

    init {
        zooKeeper.addWatch(nodePath, AddWatchMode.PERSISTENT_RECURSIVE)
        val treePrinter = TreePrinter(nodePath, zooKeeper)
        val userInputReader = UserInputReader(treePrinter)
        threadPool.execute(userInputReader)
    }

    private fun processEvent(event: WatchedEvent) {
        when {
            event.type == Watcher.Event.EventType.None -> {
                if(event.state == Watcher.Event.KeeperState.Expired) {
                    terminate()
                }
            }
            event.type == Watcher.Event.EventType.NodeCreated && event.path == nodePath -> {
                executedProcess = Runtime.getRuntime().exec(execPath)
                readStream("in", executedProcess?.inputStream)
                readStream("err", executedProcess?.errorStream)
            }
            event.type == Watcher.Event.EventType.NodeDeleted && event.path == nodePath -> {
                executedProcess?.destroy()
            }
            event.type == Watcher.Event.EventType.NodeCreated || event.type == Watcher.Event.EventType.NodeDeleted -> {
                println("Current children count: ${zooKeeper.getAllChildrenNumber("/z")}")
            }
        }
    }

    private fun readStream(name: String, stream: InputStream?) {
        stream?.let {
            threadPool.submit(ProgramInputReader(name, it))
        }
    }

    fun run(){
        lock.lock()
        while(isRunning){
            runningCondition.await()
        }
        lock.unlock()
        threadPool.shutdownNow()
    }

    private fun terminate() {
        lock.lock()
        isRunning = false
        runningCondition.signal()
        lock.unlock()
    }
}