package com.xingkaichun.helloworldblockchain.node.util;

import com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionInput;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutput;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTxoByTransactionOutputIdResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class BlockChainBrowserControllerModel2Dto {


    public static QueryTxoByTransactionOutputIdResponse.TransactionDto toTransactionDto(Transaction inputTransaction) {
        String transactionHash = inputTransaction.getTransactionHash();

        List<QueryTxoByTransactionOutputIdResponse.TransactionInputDto> transactionInputDtoList = new ArrayList<>();
        List<TransactionInput> transactionInputs = inputTransaction.getInputs();
        if(transactionInputs != null){
            for (TransactionInput transactionInput:transactionInputs) {
                QueryTxoByTransactionOutputIdResponse.TransactionInputDto transactionInputDto = new QueryTxoByTransactionOutputIdResponse.TransactionInputDto();
                transactionInputDto.setTransactionHash(transactionHash);
                transactionInputDto.setTransactionOutputIndex(transactionInput.getUnspendTransactionOutput().getTransactionOutputSequence());
                transactionInputDto.setAddress(transactionInput.getUnspendTransactionOutput().getAddress());
                transactionInputDto.setValue(transactionInput.getUnspendTransactionOutput().getValue());
                transactionInputDtoList.add(transactionInputDto);
            }
        }

        List<QueryTxoByTransactionOutputIdResponse.TransactionOutputDto> transactionOutputDtoList = new ArrayList<>();
        List<TransactionOutput> transactionOutputs = inputTransaction.getOutputs();
        if(transactionOutputs != null){
            for (TransactionOutput transactionOutput:transactionOutputs) {
                QueryTxoByTransactionOutputIdResponse.TransactionOutputDto transactionInputDto = new QueryTxoByTransactionOutputIdResponse.TransactionOutputDto();
                transactionInputDto.setTransactionHash(transactionHash);
                transactionInputDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputSequence());
                transactionInputDto.setAddress(transactionOutput.getAddress());
                transactionInputDto.setValue(transactionOutput.getValue());
                transactionOutputDtoList.add(transactionInputDto);
            }
        }

        QueryTxoByTransactionOutputIdResponse.TransactionDto transactionDto = new QueryTxoByTransactionOutputIdResponse.TransactionDto();
        transactionDto.setTransactionHash(transactionHash);
        transactionDto.setTransactionInputDtoList(transactionInputDtoList);
        transactionDto.setTransactionOutputDtoList(transactionOutputDtoList);
        return transactionDto;
    }



}
