package com.xingkaichun.helloworldblockchain.core;

import com.xingkaichun.helloworldblockchain.core.impl.*;
import com.xingkaichun.helloworldblockchain.core.tools.ResourcePathTool;

import java.io.File;

/**
 * 创建BlockChainCore的工厂
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class BlockchainCoreFactory {

    /**
     * 创建BlockChainCore实例
     */
    public static BlockchainCore createBlockChainCore(){
        return createBlockChainCore(ResourcePathTool.getDataRootPath());
    }

    /**
     * 创建BlockChainCore实例
     *
     * @param blockchainDataPath 区块链数据存放位置
     */
    public static BlockchainCore createBlockChainCore(String blockchainDataPath) {

        Incentive incentive = new IncentiveDefaultImpl();
        Consensus consensus = new ProofOfWorkConsensusImpl();
        BlockchainDatabase blockChainDataBase = new BlockchainDatabaseDefaultImpl(blockchainDataPath,incentive,consensus);

        MinerTransactionDtoDatabase minerTransactionDtoDataBase = new MinerTransactionDtoDtoDatabaseDefaultImpl(blockchainDataPath);
        Wallet wallet = new WalletImpl(blockchainDataPath);
        Miner miner = new MinerDefaultImpl(wallet,blockChainDataBase,minerTransactionDtoDataBase);

        SynchronizerDatabase synchronizerDataBase = new SynchronizerDatabaseDefaultImpl(blockchainDataPath);
        BlockchainDatabase temporaryBlockchainDatabase = new BlockchainDatabaseDefaultImpl(new File(blockchainDataPath,"TemporaryBlockChainDataBase").getAbsolutePath(),incentive,consensus);
        Synchronizer synchronizer = new SynchronizerDefaultImpl(blockChainDataBase, temporaryBlockchainDatabase,synchronizerDataBase);

        BlockchainCore blockChainCore = new BlockchainCoreImpl(blockChainDataBase,wallet,miner,synchronizer);
        return blockChainCore;
    }
}
