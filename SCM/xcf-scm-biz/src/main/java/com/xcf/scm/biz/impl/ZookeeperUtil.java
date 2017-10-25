package com.xcf.scm.biz.impl;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by WangBin on 2016/8/16.
 */
public class ZookeeperUtil implements Watcher {
    private static Logger log = LoggerFactory.getLogger(ZookeeperUtil.class);
    public static void main(String[] args)throws Exception{
        ZookeeperUtil util = new ZookeeperUtil();
        util.connect("172.20.11.103");
        /*List<String> res = util.getChild("/dubbo");
        for(String child:res){
            log.info("节点为：" + child);
        }*/
        System.out.println("data="+util.getData("/dubbo"));
        util.close();
    }

    public List<String> getChild(String path) throws KeeperException, InterruptedException{
        try{
            List<String> list=this.zooKeeper.getChildren(path, false);
            if(list.isEmpty()){
                log.info(path + "中没有节点");
            }else{
                log.info(path + "中存在节点,size={}",list.size());
            }
            return list;
        }catch (KeeperException.NoNodeException e) {
            throw e;
        }
    }

    public String getData(String path) throws KeeperException, InterruptedException{
        try{
            String list = null;
            byte[] data = this.zooKeeper.getData(path,false,null);
            list = new String(data);
            if(list.isEmpty()){
                log.info(path + "中没有节点");
            }else{
                log.info(path + "中存在节点,size={}",list);
            }
            return list;
        }catch (KeeperException.NoNodeException e) {
            throw e;
        }
    }

    //缓存时间
    private static final int SESSION_TIME   = 2000;
    protected ZooKeeper zooKeeper;
    protected CountDownLatch countDownLatch=new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException{
        zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);
        countDownLatch.await();
    }

    /* (non-Javadoc)
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if(event.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException{
        zooKeeper.close();
    }
}
