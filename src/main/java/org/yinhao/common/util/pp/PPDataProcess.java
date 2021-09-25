package org.yinhao.common.util.pp;

import org.yinhao.common.util.pp.model.APICallStack;
import org.yinhao.common.util.pp.model.CallStack;
import org.yinhao.common.util.pp.model.TransactionInfoData;

import java.io.IOException;
import java.util.*;

public class PPDataProcess {


    private PPCoClient ppCoClient;

    public PPDataProcess(PPCoClient ppCoClient) {
        this.ppCoClient = ppCoClient;
    }

    public List<APICallStack> getTransactionInfoApis(String agentId, String spanId, String txid, Date acceptTime) throws IOException {
        return getTransactionInfoApis(agentId, spanId, txid, acceptTime, new HashMap<>());
    }

    public List<APICallStack> getTransactionInfoApis(String agentId, String spanId, String txid, Date acceptTime, Map<String, String> headers) throws IOException {
        TransactionInfoData transactionInfoData = ppCoClient.getTransactionInfoData(agentId, spanId, txid, acceptTime, headers);
        List<APICallStack> callStacks = new ArrayList<>();
        CallStack callStack = transactionInfoData.getCallStackAfterAnalysis();
        List<String[]> nodes = callStack.getNodes();
        for (String[] node : nodes) {
            CallStack from = callStack.getByID(node[0]);
            CallStack to = callStack.getByID(node[1]);
            from.setArguments(from.getArguments());
            to.setArguments(to.getArguments());
            APICallStack apiCallStack = new APICallStack(from.getApplicationName(), from.getArguments(), to.getApplicationName(), to.getArguments());
            callStacks.add(apiCallStack);
        }
        return callStacks;
    }


}
