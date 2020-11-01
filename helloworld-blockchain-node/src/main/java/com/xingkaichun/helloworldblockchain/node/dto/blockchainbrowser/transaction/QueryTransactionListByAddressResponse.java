package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class QueryTransactionListByAddressResponse {

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
