package com.xingkaichun.helloworldblockchain.node.util;

import com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionInput;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutput;
import com.xingkaichun.helloworldblockchain.core.model.transaction.UnspendTransactionOutput;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionOutputByTransactionOutputIdResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class BlockChainBrowserControllerModel2Dto {


    public static QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto toTransactionDto(Transaction inputTransaction) {
        String transactionHash = inputTransaction.getTransactionHash();

        List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionInputDto> transactionInputDtoList = new ArrayList<>();
        List<TransactionInput> transactionInputs = inputTransaction.getInputs();
        if(transactionInputs != null){
            for (TransactionInput transactionInput:transactionInputs) {
                QueryTransactionOutputByTransactionOutputIdResponse.TransactionInputDto transactionInputDto = new QueryTransactionOutputByTransactionOutputIdResponse.TransactionInputDto();
                UnspendTransactionOutput unspendTransactionOutput = transactionInput.getUnspendTransactionOutput();
                transactionInputDto.setTransactionHash(unspendTransactionOutput.getTransactionHash());
                transactionInputDto.setTransactionOutputIndex(unspendTransactionOutput.getTransactionOutputIndex());
                transactionInputDto.setAddress(unspendTransactionOutput.getAddress());
                transactionInputDto.setValue(unspendTransactionOutput.getValue());
                transactionInputDtoList.add(transactionInputDto);
            }
        }

        List<QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDto> transactionOutputDtoList = new ArrayList<>();
        List<TransactionOutput> transactionOutputs = inputTransaction.getOutputs();
        if(transactionOutputs != null){
            for (TransactionOutput transactionOutput:transactionOutputs) {
                QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDto transactionInputDto = new QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDto();
                transactionInputDto.setTransactionHash(transactionHash);
                transactionInputDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputIndex());
                transactionInputDto.setAddress(transactionOutput.getAddress());
                transactionInputDto.setValue(transactionOutput.getValue());
                transactionOutputDtoList.add(transactionInputDto);
            }
        }

        QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto transactionDto = new QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto();
        transactionDto.setTransactionHash(transactionHash);
        transactionDto.setTransactionInputDtoList(transactionInputDtoList);
        transactionDto.setTransactionOutputDtoList(transactionOutputDtoList);
        return transactionDto;
    }



}
