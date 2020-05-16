package it.arkadiuss.zookeeper

import org.apache.zookeeper.AddWatchMode
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
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

    init {
        zooKeeper.addWatch(nodePath, AddWatchMode.PERSISTENT_RECURSIVE)
    }

    private fun processEvent(event: WatchedEvent) {
        println(event)
        when {
            event.type == Watcher.Event.EventType.None -> {
                if(event.state == Watcher.Event.KeeperState.Expired) {
                    terminate()
                }
            }
            event.type == Watcher.Event.EventType.NodeCreated && event.path == nodePath -> {
                executedProcess = Runtime.getRuntime().exec(File(execPath).absolutePath)
                readStream("stdin", executedProcess?.inputStream)
                readStream("stderr", executedProcess?.errorStream)
                println(executedProcess?.pid())
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
        Thread(Runnable {
            val br = BufferedReader(InputStreamReader(stream))
            while (true) {
                val s = br.readLine() ?: break
                println("[$name] $s")
            }
        }).start()
        println("AFTER START")
    }

    fun run(){
        lock.lock()
        while(isRunning){
            runningCondition.await()
        }
        lock.unlock()
    }

    private fun terminate() {
        lock.lock()
        isRunning = false
        runningCondition.signal()
        lock.unlock()
    }
}