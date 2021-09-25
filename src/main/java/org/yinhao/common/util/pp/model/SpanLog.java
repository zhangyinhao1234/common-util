package org.yinhao.common.util.pp.model;


import lombok.Data;


import java.io.Serializable;
import java.util.Date;

/**
 * {
 * "agentId":"ccc-admin-prod-027",
 * "spanId":"8085864319665499394",
 * "collectorAcceptTime":1598250733506,
 * "elapsed":155,
 * "traceId":"node-xx-prod-hh8^1595574500958^7356105",
 * "application":"/xx/xx/xx",
 * "startTime":1598250733350,
 * "endpoint":"127.0.0.0:8080",
 * "remoteAddr":"127.0.0.1",
 * "exception":0
 * }
 */
@Data

public class SpanLog implements Serializable {

    private Integer id;

    /**
     * 例如：ccc-admin-prod-044
     */
    private String agentId;

    private String agentName;

    private String spanId;

    private Date acceptTime;

    /**
     * 消耗时间,单位 毫秒
     */
    private Integer elapsed;


    /**
     * txid
     */
    private String txid;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求的开始时间
     */
    private Date startTime;

    /**
     * 被请求的服务ip和端口
     */
    private String endpoint;

    /**
     * 调用方ip
     */
    private String remoteAddr;

    /**
     * 是否存在异常  1 是 0 不是
     */
    private Integer exception;


    public SpanLog() {
    }

    public SpanLog(String agentId, String spanId, Date collectorAcceptTime, Integer elapsed, String traceId, String application, Date startTime, String endpoint, String remoteAddr, Integer exception) {
        this.agentId = agentId;
        this.spanId = spanId;
        this.acceptTime = collectorAcceptTime;
        this.elapsed = elapsed;
        this.txid = traceId;
        this.url = application;
        this.startTime = startTime;
        this.endpoint = endpoint;
        this.remoteAddr = remoteAddr;
        this.exception = exception;
    }
}
