package com.xingkaichun.helloworldblockchain.node.service;

import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutputId;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionByTransactionHashResponse;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;

import java.util.List;

/**
 * 用户service
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public interface BlockChainBrowserService {

    QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto queryTransactionOutputByTransactionOutputId(TransactionOutputId transactionOutputId);
    List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryTransactionOutputListByAddress(String address, long from, long size);
    List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryUnspendTransactionOutputListByAddress(String address, long from, long size);
    List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> querySpendTransactionOutputListByAddress(String address, long from, long size);

    QueryTransactionByTransactionHashResponse.TransactionDto queryTransactionByTransactionHash(String transactionHash);
    List<QueryTransactionByTransactionHashResponse.TransactionDto> queryTransactionListByAddress(String address, long from, long size);

}
