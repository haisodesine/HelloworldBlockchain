package com.xingkaichun.helloworldblockchain.netcore.service;

import com.xingkaichun.helloworldblockchain.core.BlockchainCore;
import com.xingkaichun.helloworldblockchain.netcore.dao.ConfigurationDao;
import com.xingkaichun.helloworldblockchain.netcore.entity.ConfigurationEntity;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class ConfigurationServiceImpl implements ConfigurationService {
    //矿工是否处于激活状态？
    private static final String IS_MINER_ACTIVE = "IS_MINER_ACTIVE";
    //同步者是否处于激活状态？
    private static final String IS_SYNCHRONIZER_ACTIVE = "IS_SYNCHRONIZER_ACTIVE";
    //是否自动搜寻区块链网络节点。
    private static final String AUTO_SEARCH_NODE = "AUTO_SEARCH_NODE";

    private BlockchainCore blockChainCore;
    private ConfigurationDao configurationDao;

    public ConfigurationServiceImpl(BlockchainCore blockChainCore,ConfigurationDao configurationDao) {
        this.blockChainCore = blockChainCore;
        this.configurationDao = configurationDao;
    }

    private void setConfiguration(ConfigurationEntity configurationEntity) {
        ConfigurationEntity configurationEntityInDb = configurationDao.getConfiguratioValue(configurationEntity.getConfKey());
        if(configurationEntityInDb == null){
            configurationDao.addConfiguration(configurationEntity);
        }else {
            configurationDao.updateConfiguration(configurationEntity);
        }
    }

    @Override
    public void restoreMinerConfiguration() {
        if(isMinerActive()){
            blockChainCore.getMiner().active();
        }else {
            blockChainCore.getMiner().deactive();
        }
    }

    @Override
    public boolean isMinerActive() {
        ConfigurationEntity configurationEntity = configurationDao.getConfiguratioValue(IS_MINER_ACTIVE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void activeMiner() {
        blockChainCore.getMiner().active();
        ConfigurationEntity configurationEntity = new ConfigurationEntity(IS_MINER_ACTIVE,String.valueOf(true));
        setConfiguration(configurationEntity);
    }

    @Override
    public void deactiveMiner() {
        blockChainCore.getMiner().deactive();
        ConfigurationEntity configurationEntity = new ConfigurationEntity(IS_MINER_ACTIVE,String.valueOf(false));
        setConfiguration(configurationEntity);
    }

    @Override
    public void restoreSynchronizerConfiguration() {
        if(isSynchronizerActive()){
            blockChainCore.getSynchronizer().active();
        }else {
            blockChainCore.getSynchronizer().deactive();
        }
    }

    @Override
    public boolean isSynchronizerActive() {
        ConfigurationEntity configurationEntity = configurationDao.getConfiguratioValue(IS_SYNCHRONIZER_ACTIVE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void activeSynchronizer() {
        blockChainCore.getSynchronizer().active();
        ConfigurationEntity configurationEntity = new ConfigurationEntity(IS_SYNCHRONIZER_ACTIVE,String.valueOf(true));
        setConfiguration(configurationEntity);
    }

    @Override
    public void deactiveSynchronizer() {
        blockChainCore.getSynchronizer().active();
        ConfigurationEntity configurationEntity = new ConfigurationEntity(IS_SYNCHRONIZER_ACTIVE,String.valueOf(false));
        setConfiguration(configurationEntity);
    }

    @Override
    public boolean isAutoSearchNode() {
        ConfigurationEntity configurationEntity = configurationDao.getConfiguratioValue(AUTO_SEARCH_NODE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void setAutoSearchNode(boolean autoSearchNode) {
        ConfigurationEntity configurationDto = new ConfigurationEntity(AUTO_SEARCH_NODE,String.valueOf(autoSearchNode));
        setConfiguration(configurationDto);
    }
}