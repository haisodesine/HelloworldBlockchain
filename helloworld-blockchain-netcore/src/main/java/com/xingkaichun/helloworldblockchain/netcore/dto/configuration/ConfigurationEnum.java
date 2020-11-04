package com.xingkaichun.helloworldblockchain.netcore.dto.configuration;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public enum ConfigurationEnum {
    IS_MINER_ACTIVE("false","矿工是否处于激活状态？"),
    IS_SYNCHRONIZER_ACTIVE("false","同步者是否处于激活状态？"),
    AUTO_SEARCH_NODE("true","是否自动搜寻区块链网络节点。"),
    ;

    private String defaultConfValue;
    private String details;
    ConfigurationEnum(String defaultConfValue,String details) {
        this.defaultConfValue = defaultConfValue;
        this.details = details;
    }

    public String getDefaultConfValue() {
        return defaultConfValue;
    }

    public String getDetails() {
        return details;
    }
}
