package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.response;

import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionOutputByTransactionOutputIdResponse;

import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class QueryTransactionOutputListByAddressResponse {

    private List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList;

    public List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> getTransactionOutputDetailDtoList() {
        return transactionOutputDetailDtoList;
    }

    public void setTransactionOutputDetailDtoList(List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList) {
        this.transactionOutputDetailDtoList = transactionOutputDetailDtoList;
    }
}
