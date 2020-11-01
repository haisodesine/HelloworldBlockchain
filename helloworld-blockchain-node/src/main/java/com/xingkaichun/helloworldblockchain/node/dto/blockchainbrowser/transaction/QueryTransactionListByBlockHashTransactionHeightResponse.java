package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class QueryTransactionListByBlockHashTransactionHeightResponse {

    private List<QueryTransactionByTransactionHashResponse.TransactionDto> transactionDtoList;




    //region get set

    public List<QueryTransactionByTransactionHashResponse.TransactionDto> getTransactionDtoList() {
        return transactionDtoList;
    }

    public void setTransactionDtoList(List<QueryTransactionByTransactionHashResponse.TransactionDto> transactionDtoList) {
        this.transactionDtoList = transactionDtoList;
    }


    //endregion
}
