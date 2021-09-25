package org.yinhao.common.util.pp.model;

import lombok.Data;

/**
 * 生成 server map需要的连线数据
 */
@Data
public class LinkData {

    private LinkDataSourceInfo sourceInfo;

    private LinkDataTargetInfo targetInfo;

}
