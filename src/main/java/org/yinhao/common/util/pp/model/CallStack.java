package org.yinhao.common.util.pp.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * PP 的链路信息,关注  Servlet Process 的数据形成集成关系
 */
@Data
public class CallStack {


    private String id;

    /**
     * PP应用名称
     */
    private String applicationName;

    private String parentId;

    private boolean isMethod;

    private final boolean hasChild;

    private List<CallStack> childs = new ArrayList<>();

    /**
     * example : Servlet Process
     */
    private String title;

    /**
     * example : /deliveryPlatform/order/updateOrder/noauth
     */
    private String arguments;

    /**
     * 清除中间节点后生成的关联到父节点，不会影响原本的数据结构
     */
    private String pId;

    /**
     * 解析后的from 和 to
     */
    private List<String[]> nodes;

    /**
     * TOMCAT / JETTY
     */
    private String apiType;


    public CallStack(String parentId, String id, String applicationName, boolean hasChild, boolean isMethod, String title, String arguments) {
        this.id = id;
        this.applicationName = applicationName;
        this.parentId = parentId;
        this.isMethod = isMethod;
        this.hasChild = hasChild;
        this.title = title;
        this.arguments = arguments;
    }


    public CallStack(String parentId, String id, String applicationName, boolean hasChild, boolean isMethod, String title, String arguments, String apiType) {
        this.id = id;
        this.applicationName = applicationName;
        this.parentId = parentId;
        this.isMethod = isMethod;
        this.hasChild = hasChild;
        this.title = title;
        this.arguments = arguments;
        this.apiType = apiType;
    }


    public void addChild(CallStack callStack) {
        CallStack parent = getByID(callStack.getParentId());
        parent.getChilds().add(callStack);
    }

    public CallStack getByID(String id) {
        return getByID(this, id);
    }

    //1:[2:[];3[10;12:[18;19]];4[11;5];6] 5
    //1:[3[12:[18;19]];4[11;5]]
    private CallStack getByID(CallStack callStack, final String id) {
        if (callStack.getId().equals(id)) {
            return callStack;
        }
        for (CallStack child : callStack.getChilds()) {
            CallStack byID = getByID(child, id);
            if (byID != null) {
                return byID;
            }
        }
        return null;
    }


    //清空中间数据
    public void clean() {
        List<String[]> nodes = new ArrayList<>();
        appendPId(this, nodes);
        this.nodes = nodes;
    }

    private void appendPId(CallStack parent, List<String[]> nodes) {
        //找到 parent 节点各个分支 最近的urlinfo节点
        List<CallStack> nextNodes = new ArrayList<>();
        for (CallStack callStack : parent.getChilds()) {
            if (isUrlInfo(callStack)) {
                nextNodes.add(callStack);
            } else {
                addNextNode(callStack, nextNodes);
            }
        }
        for (CallStack callStack : nextNodes) {
            callStack.setPId(parent.getId());
            nodes.add(new String[]{parent.getId(), callStack.getId()});
            if (callStack.isHasChild() && !callStack.getChilds().isEmpty()) {
                appendPId(callStack, nodes);
            }
        }


    }

    private void addNextNode(CallStack child, List<CallStack> nextNodes) {
        for (CallStack c : child.getChilds()) {
            if (isUrlInfo(c)) {
                nextNodes.add(c);
            } else {
                addNextNode(c, nextNodes);
            }
        }
    }


    private boolean isUrlInfo(CallStack callStack) {
        if (isServletProcess(callStack) || isTomcatUrl(callStack)) {
            return true;
        }
        return false;
    }

    private boolean isServletProcess(CallStack callStack) {
        String Servlet_Process = "Servlet Process";
        return callStack.getTitle().equals(Servlet_Process);
    }

    private boolean isTomcatUrl(CallStack callStack) {
        boolean web = "TOMCAT".equalsIgnoreCase(callStack.getArguments()) || "JETTY".equalsIgnoreCase(callStack.getArguments());
        return web && callStack.getArguments().contains("/");
    }


}
