package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import java.util.List;

public class QueryTransactionOutputByTransactionOutputIdResponse {

    private TransactionOutputDetailDto TransactionOutputDetailDto;


    public QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto getTransactionOutputDetailDto() {
        return TransactionOutputDetailDto;
    }

    public void setTransactionOutputDetailDto(QueryTransactionOutputByTransactionOutputIdResponse.TransactionOutputDetailDto transactionOutputDetailDto) {
        TransactionOutputDetailDto = transactionOutputDetailDto;
    }


    public static class TransactionDto {
        private String transactionHash;

        private List<TransactionInputView> transactionInputViewList;
        private List<TransactionOutputView> transactionOutputViewList;


        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public List<TransactionInputView> getTransactionInputViewList() {
            return transactionInputViewList;
        }

        public void setTransactionInputViewList(List<TransactionInputView> transactionInputViewList) {
            this.transactionInputViewList = transactionInputViewList;
        }

        public List<TransactionOutputView> getTransactionOutputViewList() {
            return transactionOutputViewList;
        }

        public void setTransactionOutputViewList(List<TransactionOutputView> transactionOutputViewList) {
            this.transactionOutputViewList = transactionOutputViewList;
        }
    }

    public static class TransactionOutputDetailDto {
        private long blockHeight;
        private String blockHash;
        private String transactionHash;
        private long transactionOutputIndex;
        private long value;
        private boolean isSpend;
        private String scriptLock;
        private String scriptKey;

        private TransactionDto inputTransaction;
        private TransactionDto outputTransaction;

        public String getBlockHash() {
            return blockHash;
        }

        public void setBlockHash(String blockHash) {
            this.blockHash = blockHash;
        }

        public boolean isSpend() {
            return isSpend;
        }

        public void setSpend(boolean spend) {
            isSpend = spend;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getScriptLock() {
            return scriptLock;
        }

        public void setScriptLock(String scriptLock) {
            this.scriptLock = scriptLock;
        }

        public long getBlockHeight() {
            return blockHeight;
        }

        public void setBlockHeight(long blockHeight) {
            this.blockHeight = blockHeight;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public String getScriptKey() {
            return scriptKey;
        }

        public void setScriptKey(String scriptKey) {
            this.scriptKey = scriptKey;
        }

        public long getTransactionOutputIndex() {
            return transactionOutputIndex;
        }

        public void setTransactionOutputIndex(long transactionOutputIndex) {
            this.transactionOutputIndex = transactionOutputIndex;
        }

        public TransactionDto getInputTransaction() {
            return inputTransaction;
        }

        public void setInputTransaction(TransactionDto inputTransaction) {
            this.inputTransaction = inputTransaction;
        }

        public TransactionDto getOutputTransaction() {
            return outputTransaction;
        }

        public void setOutputTransaction(TransactionDto outputTransaction) {
            this.outputTransaction = outputTransaction;
        }
    }
}
