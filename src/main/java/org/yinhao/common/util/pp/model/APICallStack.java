package org.yinhao.common.util.pp.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * API 的上下游调用链路
 */
@Data
public class APICallStack implements Serializable {


    private String txid;

    private String fromPPName;

    private String fromApiPath;

    private String toPPName;

    private String toApiPath;


    public APICallStack() {
    }

    public APICallStack(String fromPPName, String fromApiPath, String toPPName, String toApiPath) {
        this.fromPPName = fromPPName;
        this.fromApiPath = fromApiPath;
        this.toPPName = toPPName;
        this.toApiPath = toApiPath;
    }
}
