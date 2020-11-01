package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class QueryTransactionListByAddressResponse {

    private List<TransactionView> transactionViewList;




    //region get set

    public List<TransactionView> getTransactionViewList() {
        return transactionViewList;
    }

    public void setTransactionViewList(List<TransactionView> transactionViewList) {
        this.transactionViewList = transactionViewList;
    }


    //endregion
}
