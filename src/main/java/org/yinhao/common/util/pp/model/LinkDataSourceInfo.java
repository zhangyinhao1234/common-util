package org.yinhao.common.util.pp.model;

import lombok.Data;

/**
 * 生成 server map需要的连线数据->from数据
 */
@Data
public class LinkDataSourceInfo {

    private String applicationName;

    private String serviceType;

    private String serviceTypeCode;
}
