package it.arkadiuss.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2184", 2000, watchedEvent -> {
            System.out.println(watchedEvent.getPath());
        });
        System.out.println(zooKeeper.getState());
        System.out.println(zooKeeper.create("/testnode", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER));
    }
}
