package com.xingkaichun.helloworldblockchain.node.service;

import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutputId;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;

import java.util.List;

/**
 * 用户service
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public interface BlockChainBrowserService {

    QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto getTransactionOutputDetailDtoByTransactionOutputId(TransactionOutputId transactionOutputId);
    List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryTxosByAddress(String address, long from, long size);
    List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryUtxosByAddress(String address, long from, long size);
}
