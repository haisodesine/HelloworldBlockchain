package com.xingkaichun.helloworldblockchain.netcore.dto.netserver.response;

import com.xingkaichun.helloworldblockchain.netcore.dto.netserver.NodeDto;

import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class PingResponse {

    private String blockChainId;
    private Long blockChainVersion;
    private Long blockChainHeight ;
    private List<NodeDto> nodeList;




    //region get set

    public String getBlockchainId() {
        return blockChainId;
    }

    public void setBlockchainId(String blockChainId) {
        this.blockChainId = blockChainId;
    }

    public Long getBlockchainVersion() {
        return blockChainVersion;
    }

    public void setBlockchainVersion(Long blockChainVersion) {
        this.blockChainVersion = blockChainVersion;
    }

    public Long getBlockchainHeight() {
        return blockChainHeight;
    }

    public void setBlockchainHeight(Long blockChainHeight) {
        this.blockChainHeight = blockChainHeight;
    }

    public List<NodeDto> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeDto> nodeList) {
        this.nodeList = nodeList;
    }

    //endregion
}
