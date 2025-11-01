package com.wetech.demo.web3j.service;

import com.wetech.demo.web3j.contracts.mytoken.MyToken;
import com.wetech.demo.web3j.contracts.simplestorage.SimpleStorage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyTokenService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;

    private MyToken contract;
    /**
     * -- GETTER --
     *  Get the address of the currently loaded contract
     *
     * @return the contract address
     */
    @Getter
    private String contractAddress;

    /**
     * Deploy the MyToken contract to the blockchain
     * @return the address of the deployed contract
     */
    public CompletableFuture<String> deployContract() {
        log.info("Deploying SimpleStorage contract...");
        return MyToken.deploy(web3j, credentials, gasProvider)
                .sendAsync()
                .thenApply(contract -> {
                    this.contract = contract;
                    this.contractAddress = contract.getContractAddress();
                    log.info("MyToken contract deployed to: {}", contractAddress);
                    return contractAddress;
                });
    }

    /**
     * Load an existing contract from the blockchain
     * @param contractAddress the address of the contract to load
     */
    public void loadContract(String contractAddress) {
        log.info("Loading MyToken contract from address: {}", contractAddress);
        this.contract = MyToken.load(contractAddress, web3j, credentials, gasProvider);
        this.contractAddress = contractAddress;
    }

    /**
     * Get the current value stored in the contract
     * @return the stored value
     */
//    public CompletableFuture<BigInteger> getValue() {
//        if (contract == null) {
//            throw new IllegalStateException("Contract not deployed or loaded");
//        }
//        log.info("Getting value from contract at address: {}", contractAddress);
//        return contract.get().sendAsync();
//    }
//
//    /**
//     * Set a new value in the contract
//     * @param value the new value to store
//     * @return the transaction receipt
//     */
//    public CompletableFuture<TransactionReceipt> setValue(BigInteger value) {
//        if (contract == null) {
//            throw new IllegalStateException("Contract not deployed or loaded");
//        }
//        log.info("Setting value {} in contract at address: {}", value, contractAddress);
//        return contract.set(value).sendAsync();
//    }

    public CompletableFuture<TransactionReceipt> mint(String to, BigInteger amount) {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
        log.info("Mint {} to address {} with amount {}", to, contractAddress, amount);
        return contract.mint(to, amount).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> transfer(String to, BigInteger value) {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
        log.info("Transfer to address {} with value {}", to, contractAddress);
        return contract.transfer(to, value).sendAsync();
    }

    public CompletableFuture<BigInteger> balanceOf(String account) {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
        log.info("Balance of account {}", account);
        return contract.balanceOf(account).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> approve(String sender, BigInteger value) {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
        log.info("Approve account {} to address {}", sender, contractAddress);
        return contract.approve(sender, value).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> transferFrom(String from, String to, BigInteger value) {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
        log.info("TransferFrom {} to address {} with amount {}", from, to, contractAddress);
        return contract.transferFrom(from, to, value).sendAsync();
    }
}