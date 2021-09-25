package org.yinhao.common.util.pp;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.yinhao.common.util.pp.model.APICallStack;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PPTest {


    private String url = "";
    PPDataProcess ppDataProcess;
    @Before
    public void bef(){
        PPCoClient ppCoClient = new PPCoClient(new PPProperties(url));
        ppDataProcess = new PPDataProcess(ppCoClient);
    }

    @Test
    public void test1() throws IOException {
        String agentId = "";
        String spanId= "";
        String txid= "";
        Date acceptTime= new Date(1632389615537L);
        Map<String, String> headers = new HashMap<>();
        List<APICallStack> transactionInfoApis = ppDataProcess.getTransactionInfoApis(agentId, spanId, txid, acceptTime, headers);
        System.out.println(JSON.toJSONString(transactionInfoApis));
    }

}
