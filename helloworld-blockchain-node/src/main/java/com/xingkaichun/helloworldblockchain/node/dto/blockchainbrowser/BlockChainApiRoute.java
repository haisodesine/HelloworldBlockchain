package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class BlockChainApiRoute {

    public static final String GENERATE_ACCOUNT = "/Api/BlockChain/GenerateAccount";
    public static final String SUBMIT_TRANSACTION = "/Api/BlockChain/SubmitTransactionDto";
    public static final String QUERY_TRANSACTION_BY_TRANSACTION_HASH = "/Api/BlockChain/QueryTransactionByTransactionHash";
    public static final String QUERY_TRANSACTION_BY_TRANSACTION_HEIGHT = "/Api/BlockChain/QueryTransactionByTransactionHeight";
    public static final String QUERY_MINING_TRANSACTION_BY_TRANSACTION_HASH = "/Api/BlockChain/QueryMiningTransactionByTransactionHash";
    public static final String QUERY_UTXOS_BY_ADDRESS = "/Api/BlockChain/QueryUtxosByAddress";
    public static final String QUERY_TXOS_BY_ADDRESS = "/Api/BlockChain/QueryTxosByAddress";

    //根据交易输出ID，查询[交易输出来源所在的]交易和[交易输出去向所在的]交易
    public static final String QUERY_TXO_BY_TRANSACTION_OUTPUT_ID = "/Api/BlockChain/QueryTxoByTransactionOutputId";

    public static final String PING = "/Api/BlockChain/Ping";
    public static final String QUERY_MINING_TRANSACTION_LIST = "/Api/BlockChain/QueryMiningTransactionList";

    public static final String QUERY_BLOCKDTO_BY_BLOCK_HEIGHT = "/Api/BlockChain/QueryBlockDtoByBlockHeight";
    public static final String QUERY_BLOCKDTO_BY_BLOCK_HASH = "/Api/BlockChain/QueryBlockDtoByBlockHash";
    public static final String QUERY_LAST10_BLOCKDTO = "/Api/BlockChain/QueryLast10BlockDto";
}
