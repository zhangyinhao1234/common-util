package org.yinhao.common.util.pp.model;

import lombok.Data;

/**
 * 生成 server map需要的连线数据->to数据
 */
@Data
public class LinkDataTargetInfo {

    private String applicationName;

    private String serviceType;

    private String serviceTypeCode;

}
