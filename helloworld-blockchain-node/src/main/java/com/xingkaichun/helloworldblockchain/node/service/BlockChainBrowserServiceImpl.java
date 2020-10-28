package com.xingkaichun.helloworldblockchain.node.service;

import com.xingkaichun.helloworldblockchain.core.BlockChainCore;
import com.xingkaichun.helloworldblockchain.core.model.transaction.*;
import com.xingkaichun.helloworldblockchain.core.tools.ScriptTool;
import com.xingkaichun.helloworldblockchain.netcore.NetBlockchainCore;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;
import com.xingkaichun.helloworldblockchain.node.util.BlockChainBrowserControllerModel2Dto;
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
        transactionOutputDetailDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputSequence());
        transactionOutputId.setTransactionHash(transactionOutput.getTransactionHash());
        transactionOutputId.setTransactionOutputSequence(transactionOutput.getTransactionOutputSequence());
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
                            transactionOutput.getTransactionSequenceNumberInBlock()==unspendTransactionOutput.getTransactionOutputSequence()){
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

    private BlockChainCore getBlockChainCore(){
        return netBlockchainCore.getBlockChainCore();
    }
}
