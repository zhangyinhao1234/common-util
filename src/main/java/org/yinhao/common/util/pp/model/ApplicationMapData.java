package org.yinhao.common.util.pp.model;

import lombok.Data;

import java.util.List;

/**
 * 生成 server map需要的节点数据和连线数据
 */
@Data
public class ApplicationMapData {


    /**
     * 节点连线数据
     */
    private List<LinkData> linkDataArray;

    /**
     * 节点数据
     */
    private List<NodeData> nodeDataArray;


}
