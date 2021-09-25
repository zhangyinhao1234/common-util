package org.yinhao.common.util.pp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public class PPProperties {

    /**
     * 获取详细的链路信息
     */
    @Getter
    private String transactionInfoUrl;

//    /**
//     * PP获取点的详情的请求信息
//     */
//    @Getter
//    private String transactionmetaDataUrl;
//
//
//    /**
//     * PP获取时间段请求点时间信息
//     */
//    @Getter
//    private String scatterDataUrl;


}
