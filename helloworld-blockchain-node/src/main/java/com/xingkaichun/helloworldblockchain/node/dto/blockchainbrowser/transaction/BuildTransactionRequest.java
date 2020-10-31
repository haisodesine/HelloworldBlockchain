package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import com.xingkaichun.helloworldblockchain.core.model.pay.Recipient;

import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class BuildTransactionRequest {

    private List<Recipient> recipientList ;



    //region get set

    public List<Recipient> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }


    //endregion
}
