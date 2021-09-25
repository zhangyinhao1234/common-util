package org.yinhao.common.util.pp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.yinhao.common.util.net.HttpClientHelper;
import org.yinhao.common.util.pp.model.CallStack;
import org.yinhao.common.util.pp.model.TransactionInfoData;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PPCoClient {


    private PPProperties ppProperties;

    public PPCoClient(PPProperties ppProperties) {
        this.ppProperties = ppProperties;
    }


    public TransactionInfoData getTransactionInfoData(String agentId, String spanId, String txid, Date acceptTime, Map<String, String> headers) throws IOException {
        TransactionInfoData transactionInfo = getTransactionInfo(agentId, spanId, txid, acceptTime, headers);
        List<Object[]> callStack = transactionInfo.getCallStack();
        if (callStack.isEmpty()) {
            log.info("查询txid={},没有找到链路数据", txid);
            return null;
        }
        Object[] root = callStack.get(0);
        /**
         * 链路数据
         * 数据信息：
         * {"depth", "begin", "end", "excludeFromTimeline",
         * "applicationName", "tab", "id", "parentId", "isMethod", "hasChild",
         * "title", "arguments", "executeTime", "gap", "elapsedTime", "barWidth", "executionMilliseconds",
         * "simpleClassName", "methodType", "apiType", "agent", "isFocused", "hasException", "isAuthorized" }
         */

        CallStack rootCallStack = new CallStack((String) root[7], (String) root[6], (String) root[4], true, (boolean) root[8], (String) root[10], (String) root[11], (String) root[19]);
        for (int i = 1; i < callStack.size(); i++) {
            Object[] obj = callStack.get(i);
            CallStack child = new CallStack((String) obj[7], (String) obj[6], (String) obj[4], (boolean) obj[9], (boolean) obj[8], (String) obj[10], (String) obj[11], (String) root[19]);
            try {
                rootCallStack.addChild(child);
            } catch (Exception e) {
                log.error("分析链路关系发生异常child={},rootCallStack={}", JSON.toJSONString(child), JSON.toJSONString(rootCallStack), e);
            }
        }
        rootCallStack.clean();
        transactionInfo.setCallStackAfterAnalysis(rootCallStack);
        return transactionInfo;
    }

    private TransactionInfoData getTransactionInfo(String agentId, String spanId, String txid, Date acceptTime, Map<String, String> headers) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("spanId", spanId);
        params.put("traceId", URLEncoder.encode(txid, "UTF-8"));
        params.put("focusTimestamp", acceptTime.getTime() + "");
        String s = HttpClientHelper.get(ppProperties.getTransactionInfoUrl(), params, headers);
        return JSON.parseObject(s, TransactionInfoData.class);
    }
}
