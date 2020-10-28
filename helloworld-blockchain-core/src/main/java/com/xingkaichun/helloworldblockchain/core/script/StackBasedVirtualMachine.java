package com.xingkaichun.helloworldblockchain.core.script;

import com.xingkaichun.helloworldblockchain.core.model.script.*;
import com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction;
import com.xingkaichun.helloworldblockchain.core.tools.TransactionTool;
import com.xingkaichun.helloworldblockchain.core.utils.StringUtil;
import com.xingkaichun.helloworldblockchain.crypto.AccountUtil;
import com.xingkaichun.helloworldblockchain.crypto.HexUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 基于栈的虚拟机
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
public class StackBasedVirtualMachine {

    /**
     * 执行脚本
     */
    public ScriptExecuteResult executeScript(Transaction transactionEnvironment, Script script) throws RuntimeException {
        ScriptExecuteResult stack = new ScriptExecuteResult();

        for(int i=0;i<script.size();i++){
            String command = script.get(i);
            byte[] byteCommand = HexUtil.hexStringToBytes(command);
            if(Arrays.equals(OperationCodeEnum.OP_DUP.getCode(),byteCommand)){
                stack.push(stack.peek());
            }else if(Arrays.equals(OperationCodeEnum.OP_HASH160.getCode(),byteCommand)){
                String top = stack.peek();
                String publicKeyHash = AccountUtil.publicKeyHashFromPublicKey(top);
                stack.pop();
                stack.push(publicKeyHash);
            }else if(Arrays.equals(OperationCodeEnum.OP_EQUALVERIFY.getCode(),byteCommand)){
                if(!StringUtil.isEquals(stack.pop(),stack.pop())){
                    throw new RuntimeException("脚本执行失败");
                }
            }else if(Arrays.equals(OperationCodeEnum.OP_CHECKSIG.getCode(),byteCommand)){
                String publicKey = stack.pop();
                String sign = stack.pop();
                boolean verifySignatureSuccess = AccountUtil.verifySignature(publicKey, TransactionTool.getSignatureData(transactionEnvironment),sign);
                if(!verifySignatureSuccess){
                    throw new RuntimeException("脚本执行失败");
                }
                stack.push(String.valueOf(Boolean.TRUE));
            }else if(Arrays.equals(OperationCodeEnum.OP_PUSHDATA.getCode(),byteCommand)){
                stack.push(script.get(++i));
            }else {
                throw new RuntimeException("不能识别的指令");
            }
        }
        return stack;
    }

    public static Script createPayToClassicAddressScript(ScriptKey scriptKey, ScriptLock scriptLock) {
        Script script = new Script();
        script.addAll(scriptKey);
        script.addAll(scriptLock);
        return script;
    }

    public static ScriptKey createPayToPublicKeyHashInputScript(String sign, String publicKey) {
        ScriptKey script = new ScriptKey();
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_PUSHDATA.getCode()));
        script.add(sign);
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_PUSHDATA.getCode()));
        script.add(publicKey);
        return script;
    }

    public static ScriptLock createPayToPublicKeyHashOutputScript(String address) {
        ScriptLock script = new ScriptLock();
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_DUP.getCode()));
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_HASH160.getCode()));
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_PUSHDATA.getCode()));
        String publicKeyHash = AccountUtil.publicKeyHashFromAddress(address);
        script.add(publicKeyHash);
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_EQUALVERIFY.getCode()));
        script.add(HexUtil.bytesToHexString(OperationCodeEnum.OP_CHECKSIG.getCode()));
        return script;
    }

    public static String getPublicKeyHashByPayToPublicKeyHashOutputScript(List<String> scriptLock) {
        return scriptLock.get(3);
    }
}
