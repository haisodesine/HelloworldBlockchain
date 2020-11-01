package com.xingkaichun.helloworldblockchain.node.service;

import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutputId;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionByTransactionHashResponse;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionOutputByTransactionOutputIdResponse;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.TransactionView;

import java.util.List;

/**
 * 用户service
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public interface BlockChainBrowserService {

    QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto queryTransactionOutputByTransactionOutputId(TransactionOutputId transactionOutputId);
    List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> queryTransactionOutputListByAddress(String address, long from, long size);
    List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> queryUnspendTransactionOutputListByAddress(String address, long from, long size);
    List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto> querySpendTransactionOutputListByAddress(String address, long from, long size);

    TransactionView queryTransactionByTransactionHash(String transactionHash);
    List<TransactionView> queryTransactionListByAddress(String address, long from, long size);

    List<TransactionView> queryTransactionListByBlockHashTransactionHeight(String blockHash, long from, long size);
}
