package com.xingkaichun.helloworldblockchain.node.service;

import com.xingkaichun.helloworldblockchain.core.BlockChainCore;
import com.xingkaichun.helloworldblockchain.core.model.Block;
import com.xingkaichun.helloworldblockchain.core.model.transaction.*;
import com.xingkaichun.helloworldblockchain.core.tools.ScriptTool;
import com.xingkaichun.helloworldblockchain.core.tools.TransactionTool;
import com.xingkaichun.helloworldblockchain.netcore.NetBlockchainCore;
import com.xingkaichun.helloworldblockchain.netcore.dto.common.ServiceResult;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionByTransactionHashResponse;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;
import com.xingkaichun.helloworldblockchain.node.util.BlockChainBrowserControllerModel2Dto;
import com.xingkaichun.helloworldblockchain.node.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
@Service
public class BlockChainBrowserServiceImpl implements BlockChainBrowserService {

    @Autowired
    private NetBlockchainCore netBlockchainCore;

    @Override
    public QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto getTransactionOutputDetailDtoByTransactionOutputId(TransactionOutputId transactionOutputId) {
        TransactionOutput transactionOutput = getBlockChainCore().getBlockChainDataBase().queryTransactionOutputByTransactionOutputId(transactionOutputId);
        if(transactionOutput == null){
            return null;
        }

        QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto transactionOutputDetailDto = new QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto();
        transactionOutputDetailDto.setBlockHeight(transactionOutput.getBlockHeight());
        transactionOutputDetailDto.setBlockHash("");//TODO
        transactionOutputDetailDto.setTransactionHash(transactionOutput.getTransactionHash());
        transactionOutputDetailDto.setValue(transactionOutput.getValue());
        transactionOutputDetailDto.setScriptLock(ScriptTool.toString(transactionOutput.getScriptLock()));
        transactionOutputDetailDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputIndex());
        transactionOutputId.setTransactionHash(transactionOutput.getTransactionHash());
        transactionOutputId.setTransactionOutputIndex(transactionOutput.getTransactionOutputIndex());
        TransactionOutput transactionOutputTemp = getBlockChainCore().getBlockChainDataBase().queryUnspendTransactionOutputByTransactionOutputId(transactionOutputId);
        transactionOutputDetailDto.setSpend(transactionOutputTemp==null);

        //来源
        Transaction inputTransaction = getBlockChainCore().getBlockChainDataBase().queryTransactionByTransactionHash(transactionOutputId.getTransactionHash());
        QueryTxoByTransactionOutputIdResponse.TransactionDto inputTransactionDto = BlockChainBrowserControllerModel2Dto.toTransactionDto(inputTransaction);

        //去向
        QueryTxoByTransactionOutputIdResponse.TransactionDto outputTransactionDto = null;
        if(transactionOutputTemp==null){
            String transactionHash = getBlockChainCore().getBlockChainDataBase().queryToTransactionHashByTransactionOutputId(transactionOutputId);
            Transaction outputTransaction = getBlockChainCore().getBlockChainDataBase().queryTransactionByTransactionHash(transactionHash);
            outputTransactionDto = BlockChainBrowserControllerModel2Dto.toTransactionDto(outputTransaction);

            List<TransactionInput> inputs = outputTransaction.getInputs();
            if(inputs != null){
                for(TransactionInput transactionInput:inputs){
                    UnspendTransactionOutput unspendTransactionOutput = transactionInput.getUnspendTransactionOutput();
                    if(transactionOutput.getTransactionHash().equals(unspendTransactionOutput.getTransactionHash()) &&
                            transactionOutput.getTransactionIndexInBlock()==unspendTransactionOutput.getTransactionOutputIndex()){
                        transactionOutputDetailDto.setScriptKey(ScriptTool.toString(transactionInput.getScriptKey()));
                        break;
                    }
                }
            }
        }
        transactionOutputDetailDto.setInputTransaction(inputTransactionDto);
        transactionOutputDetailDto.setOutputTransaction(outputTransactionDto);
        return transactionOutputDetailDto;
    }

    @Override
    public List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryTxosByAddress(String address, long from, long size) {
        List<TransactionOutput> utxoList = getBlockChainCore().queryTxoListByAddress(address,from,size);
        if(utxoList == null){
            return null;
        }
        List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList = new ArrayList<>();
        for(TransactionOutput transactionOutput:utxoList){
            QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto transactionOutputDetailDto = getTransactionOutputDetailDtoByTransactionOutputId(transactionOutput);
            transactionOutputDetailDtoList.add(transactionOutputDetailDto);
        }
        return transactionOutputDetailDtoList;
    }

    @Override
    public List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> queryUtxosByAddress(String address, long from, long size) {
        List<TransactionOutput> utxoList = getBlockChainCore().queryUtxoListByAddress(address,from,size);
        if(utxoList == null){
            return null;
        }
        List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto> transactionOutputDetailDtoList = new ArrayList<>();
        for(TransactionOutput transactionOutput:utxoList){
            QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto transactionOutputDetailDto = getTransactionOutputDetailDtoByTransactionOutputId(transactionOutput);
            transactionOutputDetailDtoList.add(transactionOutputDetailDto);
        }
        return transactionOutputDetailDtoList;
    }

    @Override
    public List<QueryTransactionByTransactionHashResponse.TransactionDto> queryTransactionListByAddress(String address, long from, long size) {
        List<Transaction> transactionList = getBlockChainCore().queryTransactionListByAddress(address,from,size);
        if(transactionList == null){
            return null;
        }
        List<QueryTransactionByTransactionHashResponse.TransactionDto> transactionDtoList = new ArrayList<>();
        for(Transaction transaction:transactionList){
            QueryTransactionByTransactionHashResponse.TransactionDto transactionDto = queryTransactionByTransactionHash(transaction.getTransactionHash());
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }

    @Override
    public QueryTransactionByTransactionHashResponse.TransactionDto queryTransactionByTransactionHash(String transactionHash) {
        Transaction transaction = getBlockChainCore().queryTransactionByTransactionHash(transactionHash);
        if(transaction == null){
            return null;
        }
        long blockChainHeight = getBlockChainCore().queryBlockChainHeight();
        Block block = getBlockChainCore().queryBlockByBlockHeight(transaction.getBlockHeight());
        QueryTransactionByTransactionHashResponse.TransactionDto transactionDto = new QueryTransactionByTransactionHashResponse.TransactionDto();

        transactionDto.setTransactionHash(transaction.getTransactionHash());
        transactionDto.setBlockHeight(transaction.getBlockHeight());
        transactionDto.setConfirmCount(blockChainHeight-block.getHeight());
        transactionDto.setBlockTime(DateUtil.timestamp2ChinaTime(block.getTimestamp()));

        transactionDto.setTransactionFee(TransactionTool.calculateTransactionFee(transaction));
        transactionDto.setTransactionType(transaction.getTransactionType().name());
        transactionDto.setTransactionInputCount(TransactionTool.getTransactionInputCount(transaction));
        transactionDto.setTransactionOutputCount(TransactionTool.getTransactionOutputCount(transaction));
        transactionDto.setTransactionInputValues(TransactionTool.getInputsValue(transaction));
        transactionDto.setTransactionOutputValues(TransactionTool.getOutputsValue(transaction));

        List<TransactionInput> inputs = transaction.getInputs();
        List<QueryTransactionByTransactionHashResponse.TransactionInputDto> transactionInputDtoList = new ArrayList<>();
        if(inputs != null){
            for(TransactionInput transactionInput:inputs){
                QueryTransactionByTransactionHashResponse.TransactionInputDto transactionInputDto = new QueryTransactionByTransactionHashResponse.TransactionInputDto();
                transactionInputDto.setAddress(transactionInput.getUnspendTransactionOutput().getAddress());
                transactionInputDto.setValue(transactionInput.getUnspendTransactionOutput().getValue());
                transactionInputDto.setScriptKey(ScriptTool.toString(transactionInput.getScriptKey()));
                transactionInputDto.setTransactionHash(transactionInput.getUnspendTransactionOutput().getTransactionHash());
                transactionInputDto.setTransactionOutputIndex(transactionInput.getUnspendTransactionOutput().getTransactionOutputIndex());
                transactionInputDtoList.add(transactionInputDto);
            }
        }

        List<TransactionOutput> outputs = transaction.getOutputs();
        List<QueryTransactionByTransactionHashResponse.TransactionOutputDto> transactionOutputDtoList = new ArrayList<>();
        if(outputs != null){
            for(TransactionOutput transactionOutput:outputs){
                QueryTransactionByTransactionHashResponse.TransactionOutputDto transactionOutputDto = new QueryTransactionByTransactionHashResponse.TransactionOutputDto();
                transactionOutputDto.setAddress(transactionOutput.getAddress());
                transactionOutputDto.setValue(transactionOutput.getValue());
                transactionOutputDto.setScriptLock(ScriptTool.toString(transactionOutput.getScriptLock()));
                transactionOutputDto.setTransactionHash(transactionOutput.getTransactionHash());
                transactionOutputDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputIndex());
                transactionOutputDtoList.add(transactionOutputDto);
            }
        }

        transactionDto.setTransactionInputDtoList(transactionInputDtoList);
        transactionDto.setTransactionOutputDtoList(transactionOutputDtoList);
        return transactionDto;
    }

    private BlockChainCore getBlockChainCore(){
        return netBlockchainCore.getBlockChainCore();
    }
}
