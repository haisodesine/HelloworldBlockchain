package com.xingkaichun.helloworldblockchain.netcore.service;

import com.xingkaichun.helloworldblockchain.netcore.dao.NodeDao;
import com.xingkaichun.helloworldblockchain.netcore.dto.netserver.NodeDto;
import com.xingkaichun.helloworldblockchain.netcore.dto.netserver.BaseNodeDto;
import com.xingkaichun.helloworldblockchain.netcore.entity.NodeEntity;
import com.xingkaichun.helloworldblockchain.setting.GlobalSetting;
import com.xingkaichun.helloworldblockchain.util.LongUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class NodeServiceImpl implements NodeService {

    private NodeDao nodeDao;

    public NodeServiceImpl(NodeDao nodeDao) {
        this.nodeDao = nodeDao;
    }

    @Override
    public List<NodeDto> queryAllNoForkNodeList(){
        List<NodeEntity> nodeEntityList = nodeDao.queryAllNoForkNodeList();
        List<NodeDto> nodeList = classCast(nodeEntityList);
        return nodeList;
    }

    @Override
    public List<NodeDto> queryAllNoForkAliveNodeList(){
        List<NodeEntity> nodeEntityList = nodeDao.queryAllNoForkAliveNodeList();
        List<NodeDto> nodeList = classCast(nodeEntityList);
        return nodeList;
    }

    @Override
    public void nodeErrorConnectionHandle(BaseNodeDto baseNodeDto){
        NodeEntity nodeEntity = nodeDao.queryNode(baseNodeDto.getIp(), baseNodeDto.getPort());
        if(nodeEntity == null){
            return;
        }
        int errorConnectionTimes = nodeEntity.getErrorConnectionTimes()+1;
        if(errorConnectionTimes >= GlobalSetting.NodeConstant.NODE_ERROR_CONNECTION_TIMES_DELETE_THRESHOLD){
            nodeDao.deleteNode(baseNodeDto.getIp(), baseNodeDto.getPort());
        } else {
            nodeEntity.setErrorConnectionTimes(errorConnectionTimes);
            nodeEntity.setIsNodeAvailable(false);
            nodeDao.updateNode(nodeEntity);
        }
    }

    @Override
    public void addOrUpdateNodeForkPropertity(BaseNodeDto baseNodeDto){
        NodeEntity nodeEntity = nodeDao.queryNode(baseNodeDto.getIp(), baseNodeDto.getPort());
        if(nodeEntity == null){
            NodeDto node = new NodeDto();
            node.setIp(baseNodeDto.getIp());
            node.setPort(baseNodeDto.getPort());
            node.setFork(true);
            fillNodeDefaultValue(node);
            NodeEntity nodeEntity1 = classCast(node);
            nodeDao.addNode(nodeEntity1);
        }else {
            nodeEntity.setFork(true);
            nodeDao.updateNode(nodeEntity);
        }
    }

    @Override
    public void deleteNode(BaseNodeDto baseNodeDto){
        nodeDao.deleteNode(baseNodeDto.getIp(), baseNodeDto.getPort());
    }

    @Override
    public List<NodeDto> queryAllNodeList(){
        List<NodeEntity> nodeEntityList = nodeDao.queryAllNodeList();
        List<NodeDto> nodeList = classCast(nodeEntityList);
        return nodeList;
    }

    @Override
    public void addNode(NodeDto node){
        fillNodeDefaultValue(node);
        NodeEntity nodeEntityByPass = classCast(node);
        nodeDao.addNode(nodeEntityByPass);
    }

    @Override
    public void updateNode(NodeDto node){
        NodeEntity nodeEntit = classCast(node);
        nodeDao.updateNode(nodeEntit);
    }

    @Override
    public NodeDto queryNode(BaseNodeDto baseNodeDto){
        NodeEntity nodeEntity = nodeDao.queryNode(baseNodeDto.getIp(), baseNodeDto.getPort());
        if(nodeEntity == null){
            return null;
        }
        return classCast(nodeEntity);
    }


    private List<NodeDto> classCast(List<NodeEntity> nodeEntityList) {
        if(nodeEntityList == null){
            return null;
        }
        List<NodeDto> nodeList = new ArrayList<>();
        for(NodeEntity nodeEntity:nodeEntityList){
            nodeList.add(classCast(nodeEntity));
        }
        return nodeList;
    }

    private NodeDto classCast(NodeEntity nodeEntity) {
        NodeDto node = new NodeDto();
        node.setIp(nodeEntity.getIp());
        node.setPort(nodeEntity.getPort());
        node.setIsNodeAvailable(nodeEntity.getIsNodeAvailable());
        node.setErrorConnectionTimes(nodeEntity.getErrorConnectionTimes());
        node.setBlockchainHeight(nodeEntity.getBlockchainHeight());
        node.setFork(nodeEntity.getFork());
        return node;
    }

    private NodeEntity classCast(NodeDto node) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setIp(node.getIp());
        nodeEntity.setPort(node.getPort());
        nodeEntity.setIsNodeAvailable(node.getIsNodeAvailable());
        nodeEntity.setErrorConnectionTimes(node.getErrorConnectionTimes());
        nodeEntity.setBlockchainHeight(node.getBlockchainHeight());
        nodeEntity.setFork(node.getFork());
        return nodeEntity;
    }

    private void fillNodeDefaultValue(NodeDto node) {
        if(node.getBlockchainHeight() == null){
            node.setBlockchainHeight(LongUtil.ZERO);
        }
        if(node.getIsNodeAvailable() == null){
            node.setIsNodeAvailable(true);
        }
        if(node.getErrorConnectionTimes() == null){
            node.setErrorConnectionTimes(0);
        }
        if(node.getFork() == null){
            node.setFork(false);
        }
    }
}
