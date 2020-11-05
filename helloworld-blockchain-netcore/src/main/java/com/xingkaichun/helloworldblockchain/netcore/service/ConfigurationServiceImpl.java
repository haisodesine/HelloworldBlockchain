package com.xingkaichun.helloworldblockchain.netcore.service;

import com.google.common.base.Strings;
import com.xingkaichun.helloworldblockchain.core.BlockchainCore;
import com.xingkaichun.helloworldblockchain.netcore.dao.ConfigurationDao;
import com.xingkaichun.helloworldblockchain.netcore.dto.configuration.ConfigurationDto;
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

    private ConfigurationEntity getConfigurationByConfigurationKey(String confKey) {
        if(Strings.isNullOrEmpty(confKey)){
            return null;
        }
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setConfKey(confKey);
        ConfigurationEntity configurationEntity = configurationDao.getConfiguratioValue(confKey);
        return configurationEntity;
    }

    //事务
    private void setConfiguration(ConfigurationDto configurationDto) {
        String confKey = configurationDto.getConfKey();
        String confValue = configurationDto.getConfValue();
        if(Strings.isNullOrEmpty(confKey)){
            throw new NullPointerException("ConfKey不能为空");
        }
        if(Strings.isNullOrEmpty(confValue)){
            throw new NullPointerException("ConfValue不能为空");
        }
        ConfigurationEntity configurationEntity = new ConfigurationEntity();
        configurationEntity.setConfKey(confKey);
        configurationEntity.setConfValue(confValue);
        insertOrUpdate(configurationEntity);
    }

    private void insertOrUpdate(ConfigurationEntity configurationEntity){
        ConfigurationEntity configurationEntity2 = configurationDao.getConfiguratioValue(configurationEntity.getConfKey());
        if(configurationEntity2 == null){
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
        ConfigurationEntity configurationEntity = getConfigurationByConfigurationKey(IS_MINER_ACTIVE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void activeMiner() {
        blockChainCore.getMiner().active();
        ConfigurationDto configurationDto = new ConfigurationDto(IS_MINER_ACTIVE,String.valueOf(true));
        setConfiguration(configurationDto);
    }

    @Override
    public void deactiveMiner() {
        blockChainCore.getMiner().deactive();
        ConfigurationDto configurationDto = new ConfigurationDto(IS_MINER_ACTIVE,String.valueOf(false));
        setConfiguration(configurationDto);
    }

    @Override
    public void restorSynchronizerConfiguration() {
        if(isSynchronizerActive()){
            blockChainCore.getSynchronizer().active();
        }else {
            blockChainCore.getSynchronizer().deactive();
        }
    }

    @Override
    public boolean isSynchronizerActive() {
        ConfigurationEntity configurationEntity = getConfigurationByConfigurationKey(IS_SYNCHRONIZER_ACTIVE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void activeSynchronizer() {
        blockChainCore.getSynchronizer().active();
        ConfigurationDto configurationDto = new ConfigurationDto(IS_SYNCHRONIZER_ACTIVE,String.valueOf(true));
        setConfiguration(configurationDto);
    }

    @Override
    public void deactiveSynchronizer() {
        blockChainCore.getSynchronizer().active();
        ConfigurationDto configurationDto = new ConfigurationDto(IS_SYNCHRONIZER_ACTIVE,String.valueOf(false));
        setConfiguration(configurationDto);
    }

    @Override
    public boolean isAutoSearchNode() {
        ConfigurationEntity configurationEntity = getConfigurationByConfigurationKey(AUTO_SEARCH_NODE);
        if(configurationEntity == null){
            //默认值
            return false;
        }
        return Boolean.valueOf(configurationEntity.getConfValue());
    }

    @Override
    public void setAutoSearchNode(boolean autoSearchNode) {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setConfKey(AUTO_SEARCH_NODE);
        configurationDto.setConfValue(String.valueOf(autoSearchNode));
        setConfiguration(configurationDto);
    }
}