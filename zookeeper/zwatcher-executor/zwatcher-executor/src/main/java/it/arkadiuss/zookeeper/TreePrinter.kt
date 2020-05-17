package it.arkadiuss.zookeeper

import org.apache.zookeeper.ZooKeeper

class TreePrinter(
        private val parentNode: String,
        private val zooKeeper: ZooKeeper
) {
    fun print(): String {
        if(zooKeeper.exists(parentNode, false) == null)
            return ""
        return print(parentNode, 0)
    }

    private fun print(node: String, indent: Int): String {
        val children = zooKeeper.getChildren(node, false)
        var res = "${getIndent(indent)} $node \n"
        children.forEach {
            res += print("$node/$it", indent + 1)
        }
        return res
    }

    private fun getIndent(indent: Int): String {
        return "-".repeat(indent)
    }
}