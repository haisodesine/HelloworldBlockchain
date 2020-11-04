package com.xingkaichun.helloworldblockchain.netcore;

import com.xingkaichun.helloworldblockchain.core.BlockchainCore;
import com.xingkaichun.helloworldblockchain.core.model.pay.BuildTransactionRequest;
import com.xingkaichun.helloworldblockchain.core.model.pay.BuildTransactionResponse;
import com.xingkaichun.helloworldblockchain.netcore.dto.common.EmptyResponse;
import com.xingkaichun.helloworldblockchain.netcore.dto.common.ServiceResult;
import com.xingkaichun.helloworldblockchain.netcore.dto.configuration.ConfigurationDto;
import com.xingkaichun.helloworldblockchain.netcore.dto.configuration.ConfigurationEnum;
import com.xingkaichun.helloworldblockchain.netcore.dto.netserver.NodeDto;
import com.xingkaichun.helloworldblockchain.netcore.dto.transaction.SubmitTransactionRequest;
import com.xingkaichun.helloworldblockchain.netcore.dto.transaction.SubmitTransactionResponse;
import com.xingkaichun.helloworldblockchain.netcore.node.server.BlockchainNodeHttpServer;
import com.xingkaichun.helloworldblockchain.netcore.node.client.BlockchainNodeClient;
import com.xingkaichun.helloworldblockchain.netcore.service.ConfigurationService;
import com.xingkaichun.helloworldblockchain.netcore.service.NodeService;
import com.xingkaichun.helloworldblockchain.netcore.transport.dto.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络版区块链核心，代表一个完整的网络版区块链核心系统。
 * 网络版区块链核心系统，由以下几部分组成：
 * 1.单机版[没有网络交互版本]区块链核心
 * @see BlockchainCore
 * 2.节点搜寻器
 * @see com.xingkaichun.helloworldblockchain.netcore.NodeSearcher
 * 3.节点广播者
 * @see com.xingkaichun.helloworldblockchain.netcore.NodeBroadcaster
 * 4.区块搜寻器
 * @see com.xingkaichun.helloworldblockchain.netcore.BlockSearcher
 * 5.区块广播者
 * @see com.xingkaichun.helloworldblockchain.netcore.BlockBroadcaster
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class NetBlockchainCore {

    private BlockchainCore blockChainCore;
    private BlockchainNodeHttpServer blockchainNodeHttpServer;
    private NodeSearcher nodeSearcher;
    private NodeBroadcaster nodeBroadcaster;
    private BlockSearcher blockSearcher;
    private BlockBroadcaster blockBroadcaster;

    private ConfigurationService configurationService;
    private NodeService nodeService;
    private BlockchainNodeClient blockchainNodeClient;
    public NetBlockchainCore(BlockchainCore blockChainCore
            , BlockchainNodeHttpServer blockchainNodeHttpServer, ConfigurationService configurationService
            , NodeSearcher nodeSearcher, NodeBroadcaster nodeBroadcaster
            , BlockSearcher blockSearcher , BlockBroadcaster blockBroadcaster
            , NodeService nodeService, BlockchainNodeClient blockchainNodeClient) {

        this.blockChainCore = blockChainCore;
        this.blockchainNodeHttpServer = blockchainNodeHttpServer;
        this.configurationService = configurationService;
        this.nodeSearcher = nodeSearcher;
        this.nodeBroadcaster = nodeBroadcaster;
        this.blockSearcher = blockSearcher;
        this.blockBroadcaster = blockBroadcaster;

        this.nodeService = nodeService;
        this.blockchainNodeClient = blockchainNodeClient;
        restoreConfiguration();
    }

    /**
     * 恢复配置
     */
    private void restoreConfiguration() {
        //是否激活矿工
        ConfigurationDto isMinerActiveConfigurationDto = configurationService.getConfigurationByConfigurationKey(ConfigurationEnum.IS_MINER_ACTIVE.name());
        if(Boolean.valueOf(isMinerActiveConfigurationDto.getConfValue())){
            blockChainCore.getMiner().active();
        }else {
            blockChainCore.getMiner().deactive();
        }
        //是否激活同步者
        ConfigurationDto isSynchronizerActiveConfigurationDto = configurationService.getConfigurationByConfigurationKey(ConfigurationEnum.IS_SYNCHRONIZER_ACTIVE.name());
        if(Boolean.valueOf(isSynchronizerActiveConfigurationDto.getConfValue())){
            blockChainCore.getSynchronizer().active();
        }else {
            blockChainCore.getSynchronizer().deactive();
        }
    }




    public void start() {
        //启动本地的单机区块链
        blockChainCore.start();
        //启动区块链节点服务器
        blockchainNodeHttpServer.start();

        //启动节点搜寻器
        nodeSearcher.start();
        //启动节点广播器
        nodeBroadcaster.start();
        //启动区块搜寻器
        blockSearcher.start();
        //启动区块广播者
        blockBroadcaster.start();
    }








    public BuildTransactionResponse buildTransaction(BuildTransactionRequest request) {
        BuildTransactionResponse buildTransactionResponse = blockChainCore.buildTransactionDTO(request);
        return buildTransactionResponse;
    }

    public SubmitTransactionResponse submitTransaction(SubmitTransactionRequest request) {
        TransactionDTO transactionDTO = request.getTransactionDTO();
        //将交易提交到本地区块链
        blockChainCore.submitTransaction(transactionDTO);
        //提交交易到网络
        List<NodeDto> nodes = nodeService.queryAllNoForkAliveNodeList();
        List<SubmitTransactionResponse.Node> successSubmitNode = new ArrayList<>();
        List<SubmitTransactionResponse.Node> failSubmitNode = new ArrayList<>();
        if(nodes != null){
            for(NodeDto node:nodes){
                ServiceResult<EmptyResponse> submitSuccess = blockchainNodeClient.sumiteTransaction(node,transactionDTO);
                if(ServiceResult.isSuccess(submitSuccess)){
                    successSubmitNode.add(new SubmitTransactionResponse.Node(node.getIp(),node.getPort()));
                } else {
                    failSubmitNode.add(new SubmitTransactionResponse.Node(node.getIp(),node.getPort()));
                }
            }
        }

        SubmitTransactionResponse response = new SubmitTransactionResponse();
        response.setTransactionDTO(transactionDTO);
        response.setSuccessSubmitNode(successSubmitNode);
        response.setFailSubmitNode(failSubmitNode);
        return response;
    }


    //region get set
    public BlockchainCore getBlockChainCore() {
        return blockChainCore;
    }

    public BlockchainNodeHttpServer getBlockchainNodeHttpServer() {
        return blockchainNodeHttpServer;
    }

    public NodeSearcher getNodeSearcher() {
        return nodeSearcher;
    }

    public BlockSearcher getBlockSearcher() {
        return blockSearcher;
    }

    public BlockBroadcaster getBlockBroadcaster() {
        return blockBroadcaster;
    }

    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    public NodeBroadcaster getNodeBroadcaster() {
        return nodeBroadcaster;
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public BlockchainNodeClient getBlockchainNodeClient() {
        return blockchainNodeClient;
    }
    //end
}
