package com.crack;


import com.aliyun.TigerTally;
import com.github.unidbg.worker.Worker;

import java.io.IOException;
import java.util.Map;

public class Ibox implements Worker {
    TigerTally tigerTally;

    public Ibox() {
        tigerTally = new TigerTally();
//        System.out.println("Create: " + mafengwo);
    }

    @Override
    public void close() throws IOException {

        tigerTally.destroy();
    }

    public Map<String, String> worker(String args,Boolean hasInit) {
//        System.out.println("MFWWorker worker: " + Thread.currentThread().getName() + Thread.currentThread().getId());
        String url = args;
        return tigerTally.callFunc3(url,hasInit);
    }
}
