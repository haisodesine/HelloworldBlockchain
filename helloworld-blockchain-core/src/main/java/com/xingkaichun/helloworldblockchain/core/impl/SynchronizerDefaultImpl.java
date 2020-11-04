package com.xingkaichun.helloworldblockchain.core.impl;

import com.xingkaichun.helloworldblockchain.core.BlockchainDatabase;
import com.xingkaichun.helloworldblockchain.core.Synchronizer;
import com.xingkaichun.helloworldblockchain.core.SynchronizerDatabase;
import com.xingkaichun.helloworldblockchain.core.model.Block;
import com.xingkaichun.helloworldblockchain.core.tools.BlockTool;
import com.xingkaichun.helloworldblockchain.core.tools.Dto2ModelTool;
import com.xingkaichun.helloworldblockchain.util.LongUtil;
import com.xingkaichun.helloworldblockchain.util.StringUtil;
import com.xingkaichun.helloworldblockchain.util.ThreadUtil;
import com.xingkaichun.helloworldblockchain.netcore.transport.dto.BlockDTO;
import com.xingkaichun.helloworldblockchain.setting.GlobalSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认实现
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class SynchronizerDefaultImpl extends Synchronizer {

    private static final Logger logger = LoggerFactory.getLogger(SynchronizerDefaultImpl.class);

    //本节点的区块链，同步器的目标就是让本节点区块链增长长度。
    private BlockchainDatabase targetBlockchainDatabase;
    /**
     * 一个临时的区块链
     * 同步器实现的机制：
     * ①将本节点的区块链的数据复制进临时区块链
     * ②发现一个可以同步的节点，将这个节点的数据同步至临时区块链
     * ③将临时区块链的数据同步至本节点区块链
     */
    private BlockchainDatabase temporaryBlockchainDatabase;

    //同步开关:默认同步其它节点区块链数据
    private boolean synchronizeOption = true;

    public SynchronizerDefaultImpl(BlockchainDatabase targetBlockchainDatabase,
                                   BlockchainDatabase temporaryBlockchainDatabase,
                                   SynchronizerDatabase synchronizerDataBase) {
        super(synchronizerDataBase);
        this.targetBlockchainDatabase = targetBlockchainDatabase;
        this.temporaryBlockchainDatabase = temporaryBlockchainDatabase;
    }

    @Override
    public void start() {
        while (true){
            ThreadUtil.sleep(10);
            if(!synchronizeOption){
                continue;
            }
            String availableSynchronizeNodeId = synchronizerDataBase.getDataTransferFinishFlagNodeId();
            if(availableSynchronizeNodeId == null){
                continue;
            }
            synchronizeBlockChainNode(availableSynchronizeNodeId);
        }
    }

    @Override
    public void deactive() {
        synchronizeOption = false;
    }

    @Override
    public void active() {
        synchronizeOption = true;
    }

    @Override
    public boolean isActive() {
        return synchronizeOption;
    }

    private void synchronizeBlockChainNode(String availableSynchronizeNodeId) {
        if(!synchronizeOption){
            return;
        }
        copyTargetBlockChainDataBaseToTemporaryBlockChainDataBase(targetBlockchainDatabase, temporaryBlockchainDatabase);
        boolean hasDataTransferFinishFlag = synchronizerDataBase.hasDataTransferFinishFlag(availableSynchronizeNodeId);
        if(!hasDataTransferFinishFlag){
            synchronizerDataBase.clear(availableSynchronizeNodeId);
            return;
        }

        long maxBlockHeight = synchronizerDataBase.getMaxBlockHeight(availableSynchronizeNodeId);
        if(maxBlockHeight <= 0){
            return;
        }
        long targetBlockChainHeight = targetBlockchainDatabase.queryBlockChainHeight();
        if(!LongUtil.isEquals(targetBlockChainHeight,LongUtil.ZERO) && LongUtil.isGreatEqualThan(targetBlockChainHeight,maxBlockHeight)){
            synchronizerDataBase.clear(availableSynchronizeNodeId);
            return;
        }

        long minBlockHeight = synchronizerDataBase.getMinBlockHeight(availableSynchronizeNodeId);
        BlockDTO blockDTO = synchronizerDataBase.getBlockDto(availableSynchronizeNodeId,minBlockHeight);
        if(blockDTO != null){
            temporaryBlockchainDatabase.deleteBlocksUtilBlockHeightLessThan(minBlockHeight);
            while(blockDTO != null){
                Block block = Dto2ModelTool.blockDto2Block(temporaryBlockchainDatabase,blockDTO);
                boolean isAddBlockToBlockChainSuccess = temporaryBlockchainDatabase.addBlock(block);
                if(!isAddBlockToBlockChainSuccess){
                    break;
                }
                minBlockHeight++;
                blockDTO = synchronizerDataBase.getBlockDto(availableSynchronizeNodeId,minBlockHeight);
            }
        }
        promoteTargetBlockChainDataBase(targetBlockchainDatabase, temporaryBlockchainDatabase);
        synchronizerDataBase.clear(availableSynchronizeNodeId);
    }

    /**
     * 若targetBlockChainDataBase的高度小于blockChainDataBaseTemporary的高度，
     * 则targetBlockChainDataBase同步blockChainDataBaseTemporary的数据。
     */
    private void promoteTargetBlockChainDataBase(BlockchainDatabase targetBlockchainDatabase,
                                                 BlockchainDatabase temporaryBlockchainDatabase) {
        Block targetBlockChainTailBlock = targetBlockchainDatabase.queryTailBlock();
        Block temporaryBlockChainTailBlock = temporaryBlockchainDatabase.queryTailBlock() ;
        //不需要调整
        if(temporaryBlockChainTailBlock == null){
            return;
        }
        if(targetBlockChainTailBlock == null){
            Block block = temporaryBlockchainDatabase.queryBlockByBlockHeight(GlobalSetting.GenesisBlock.HEIGHT +1);
            boolean isAddBlockToBlockChainSuccess = targetBlockchainDatabase.addBlock(block);
            if(!isAddBlockToBlockChainSuccess){
                return;
            }
            targetBlockChainTailBlock = targetBlockchainDatabase.queryTailBlock();
        }
        if(targetBlockChainTailBlock == null){
            throw new RuntimeException("在这个时刻，targetBlockChainTailBlock必定不为null。");
        }
        if(LongUtil.isGreatEqualThan(targetBlockChainTailBlock.getHeight(),temporaryBlockChainTailBlock.getHeight())){
            return;
        }
        //未分叉区块高度
        long noForkBlockHeight = targetBlockChainTailBlock.getHeight();
        while (true){
            if(LongUtil.isLessEqualThan(noForkBlockHeight,LongUtil.ZERO)){
                break;
            }
            Block targetBlock = targetBlockchainDatabase.queryBlockByBlockHeight(noForkBlockHeight);
            if(targetBlock == null){
                break;
            }
            Block temporaryBlock = temporaryBlockchainDatabase.queryBlockByBlockHeight(noForkBlockHeight);
            if(StringUtil.isEquals(targetBlock.getHash(),temporaryBlock.getHash()) &&
                    StringUtil.isEquals(targetBlock.getPreviousBlockHash(),temporaryBlock.getPreviousBlockHash())){
                break;
            }
            targetBlockchainDatabase.deleteTailBlock();
            noForkBlockHeight = targetBlockchainDatabase.queryBlockChainHeight();
        }

        long targetBlockChainHeight = targetBlockchainDatabase.queryBlockChainHeight() ;
        while(true){
            targetBlockChainHeight++;
            Block currentBlock = temporaryBlockchainDatabase.queryBlockByBlockHeight(targetBlockChainHeight) ;
            if(currentBlock == null){
                break;
            }
            boolean isAddBlockToBlockChainSuccess = targetBlockchainDatabase.addBlock(currentBlock);
            if(!isAddBlockToBlockChainSuccess){
                break;
            }
        }
    }
    /**
     * 使得temporaryBlockChainDataBase和targetBlockChainDataBase的区块链数据一模一样
     */
    private void copyTargetBlockChainDataBaseToTemporaryBlockChainDataBase(BlockchainDatabase targetBlockchainDatabase,
                                                                           BlockchainDatabase temporaryBlockchainDatabase) {
        Block targetBlockChainTailBlock = targetBlockchainDatabase.queryTailBlock() ;
        Block temporaryBlockChainTailBlock = temporaryBlockchainDatabase.queryTailBlock() ;
        if(targetBlockChainTailBlock == null){
            //清空temporary
            temporaryBlockchainDatabase.deleteBlocksUtilBlockHeightLessThan(LongUtil.ONE);
            return;
        }
        //删除Temporary区块链直到尚未分叉位置停止
        while(true){
            if(temporaryBlockChainTailBlock == null){
                break;
            }
            Block targetBlockChainBlock = targetBlockchainDatabase.queryBlockByBlockHeight(temporaryBlockChainTailBlock.getHeight());
            if(BlockTool.isBlockEquals(targetBlockChainBlock,temporaryBlockChainTailBlock)){
                break;
            }
            temporaryBlockchainDatabase.deleteTailBlock();
            temporaryBlockChainTailBlock = temporaryBlockchainDatabase.queryTailBlock();
        }
        //复制target数据至temporary
        long temporaryBlockChainHeight = temporaryBlockchainDatabase.queryBlockChainHeight();
        while(true){
            temporaryBlockChainHeight++;
            Block currentBlock = targetBlockchainDatabase.queryBlockByBlockHeight(temporaryBlockChainHeight) ;
            if(currentBlock == null){
                break;
            }
            boolean isAddBlockToBlockChainSuccess = temporaryBlockchainDatabase.addBlock(currentBlock);
            if(!isAddBlockToBlockChainSuccess){
                return;
            }
        }
    }
}
