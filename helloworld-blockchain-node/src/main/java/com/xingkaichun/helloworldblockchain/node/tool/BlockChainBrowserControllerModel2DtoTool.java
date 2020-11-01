package com.xingkaichun.helloworldblockchain.node.tool;

import com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionInput;
import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutput;
import com.xingkaichun.helloworldblockchain.core.model.transaction.UnspendTransactionOutput;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.QueryTransactionOutputByTransactionOutputIdResponse;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.TransactionInputView;
import com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction.TransactionOutputView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class BlockChainBrowserControllerModel2DtoTool {


    public static QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto toTransactionDto(Transaction inputTransaction) {
        String transactionHash = inputTransaction.getTransactionHash();

        List<TransactionInputView> transactionInputViewList = new ArrayList<>();
        List<TransactionInput> transactionInputs = inputTransaction.getInputs();
        if(transactionInputs != null){
            for (TransactionInput transactionInput:transactionInputs) {
                TransactionInputView transactionInputView = new TransactionInputView();
                UnspendTransactionOutput unspendTransactionOutput = transactionInput.getUnspendTransactionOutput();
                transactionInputView.setTransactionHash(unspendTransactionOutput.getTransactionHash());
                transactionInputView.setTransactionOutputIndex(unspendTransactionOutput.getTransactionOutputIndex());
                transactionInputView.setAddress(unspendTransactionOutput.getAddress());
                transactionInputView.setValue(unspendTransactionOutput.getValue());
                transactionInputViewList.add(transactionInputView);
            }
        }

        List<TransactionOutputView> transactionOutputViewList = new ArrayList<>();
        List<TransactionOutput> transactionOutputs = inputTransaction.getOutputs();
        if(transactionOutputs != null){
            for (TransactionOutput transactionOutput:transactionOutputs) {
                TransactionOutputView transactionInputDto = new TransactionOutputView();
                transactionInputDto.setTransactionHash(transactionHash);
                transactionInputDto.setTransactionOutputIndex(transactionOutput.getTransactionOutputIndex());
                transactionInputDto.setAddress(transactionOutput.getAddress());
                transactionInputDto.setValue(transactionOutput.getValue());
                transactionOutputViewList.add(transactionInputDto);
            }
        }

        QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto transactionDto = new QueryTransactionOutputByTransactionOutputIdResponse.TransactionDto();
        transactionDto.setTransactionHash(transactionHash);
        transactionDto.setTransactionInputViewList(transactionInputViewList);
        transactionDto.setTransactionOutputViewList(transactionOutputViewList);
        return transactionDto;
    }



}
