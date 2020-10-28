package com.xingkaichun.helloworldblockchain.node.dto.blockchainbrowser.transaction;

import java.util.List;

public class QueryTxoByTransactionOutputIdResponse {

    private TransactionOutputDetailDto TransactionOutputDetailDto;


    public QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto getTransactionOutputDetailDto() {
        return TransactionOutputDetailDto;
    }

    public void setTransactionOutputDetailDto(QueryTxoByTransactionOutputIdResponse.TransactionOutputDetailDto transactionOutputDetailDto) {
        TransactionOutputDetailDto = transactionOutputDetailDto;
    }


    public static class TransactionDto {
        private String transactionHash;

        private List<TransactionInputDto> transactionInputDtoList;
        private List<TransactionOutputDto> transactionOutputDtoList;


        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public List<TransactionInputDto> getTransactionInputDtoList() {
            return transactionInputDtoList;
        }

        public void setTransactionInputDtoList(List<TransactionInputDto> transactionInputDtoList) {
            this.transactionInputDtoList = transactionInputDtoList;
        }

        public List<TransactionOutputDto> getTransactionOutputDtoList() {
            return transactionOutputDtoList;
        }

        public void setTransactionOutputDtoList(List<TransactionOutputDto> transactionOutputDtoList) {
            this.transactionOutputDtoList = transactionOutputDtoList;
        }
    }

    public static class TransactionInputDto {
        private String transactionHash;
        private long transactionOutputIndex;
        private String address;
        private long value;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public long getTransactionOutputIndex() {
            return transactionOutputIndex;
        }

        public void setTransactionOutputIndex(long transactionOutputIndex) {
            this.transactionOutputIndex = transactionOutputIndex;
        }
    }

    public static class TransactionOutputDto {
        private String transactionHash;
        private long transactionOutputIndex;
        private String address;
        private long value;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public long getTransactionOutputIndex() {
            return transactionOutputIndex;
        }

        public void setTransactionOutputIndex(long transactionOutputIndex) {
            this.transactionOutputIndex = transactionOutputIndex;
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
