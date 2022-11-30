package com.mju.outerSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName :  main.outerSystem
 * fileName : RequestOnSiteSystem
 * author :  규현
 * date : 2022-05-18
 * description : 현장 출동 업체
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-18                규현             최초 생성
 */
public class RequestOnSiteSystem {

    private static List<AccidentWorker> servers = new ArrayList<>();

    private static void init() {
        servers.add(new AccidentWorker("직원1","010-1111-1111"));
        servers.add(new AccidentWorker("직원2","010-2222-2222"));
        servers.add(new AccidentWorker("직원3","010-3333-3333"));
        servers.add(new AccidentWorker("직원4","010-4444-4444"));
    }

    public static AccidentWorker connectWorker() {
        if(servers.size() == 0) {
            init();
        }
        int index = (int) (Math.random()*servers.size());
        return servers.get(index);
    }

}
