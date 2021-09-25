package org.yinhao.common.util.pp.model;

import lombok.Data;

import java.util.List;

/**
 * 详细的某个请求的链路数据
 */
@Data
public class TransactionInfoData {

    /**
     * example : xxx-order-prod-026
     */
    private String agentId;

    /**
     * example : xxx-order-prod
     */
    private String applicationId;


    /**
     * 节点和连线数据
     */
    private ApplicationMapData applicationMapData;


    /**
     * 链路数据
     * 数据信息：
     * {"depth", "begin", "end", "excludeFromTimeline",
     * "applicationName", "tab", "id", "parentId", "isMethod", "hasChild",
     * "title", "arguments", "executeTime", "gap", "elapsedTime", "barWidth", "executionMilliseconds",
     * "simpleClassName", "methodType", "apiType", "agent", "isFocused", "hasException", "isAuthorized" }
     */
    private List<Object[]> callStack;

    /**
     * 请求的path
     */
    private String applicationName;

    /**
     * 1600394905832
     */
    private Long callStackStart;

    /**
     * 1600394905894
     */
    private Long callStackEnd;

    /**
     * xxx-xxx-prod-8^1593519116477^824385
     */
    private String transactionId;


    /**
     * 对 callStack 解析后的数据
     */
    private CallStack callStackAfterAnalysis;




}
