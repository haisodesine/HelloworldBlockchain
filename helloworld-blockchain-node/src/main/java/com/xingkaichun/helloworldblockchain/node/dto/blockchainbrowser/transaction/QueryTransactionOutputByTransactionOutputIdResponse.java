package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

public class QueryTransactionOutputByTransactionOutputIdResponse {

    private TransactionOutputDetailView TransactionOutputDetailView;


    public TransactionOutputDetailView getTransactionOutputDetailView() {
        return TransactionOutputDetailView;
    }

    public void setTransactionOutputDetailView(TransactionOutputDetailView transactionOutputDetailView) {
        TransactionOutputDetailView = transactionOutputDetailView;
    }
}
