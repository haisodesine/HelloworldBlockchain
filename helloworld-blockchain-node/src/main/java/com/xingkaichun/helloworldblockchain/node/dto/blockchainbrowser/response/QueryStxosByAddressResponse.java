package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.response;

import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;

import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class QueryStxosByAddressResponse {

    private List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList;

    public List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> getTransactionOutputDetailDtoList() {
        return transactionOutputDetailDtoList;
    }

    public void setTransactionOutputDetailDtoList(List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList) {
        this.transactionOutputDetailDtoList = transactionOutputDetailDtoList;
    }
}
