package com.pay.fakepay.ejb;

import com.pay.fakepay.CurrencyExchange;
import com.pay.fakepay.entity.dao.MoneyTransactionDAO;
import com.pay.fakepay.entity.dao.SystemUserDAO;
import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import com.pay.fakepay.entity.dto.SystemUserDetailsDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;


@TransactionAttribute(REQUIRED)
@Stateless public class TransactionService {
    
    @EJB
    SystemUserDAO su;
    
    @EJB
    MoneyTransactionDAO mt;
    
    public TransactionService() { }
    
    public void makePayment(String from, String to, float senderAmount) {
        SystemUserDetailsDTO sender = su.details(from);
        SystemUserDetailsDTO recipient = su.details(to);
        
        Float recipientAmount = CurrencyExchange.convert(
                sender.getCurrency(), 
                recipient.getCurrency(), 
                senderAmount);
        
        MoneyTransactionDTO transaction = new MoneyTransactionDTO(
                sender, 
                recipient, 
                senderAmount, 
                recipientAmount);
        completeTransaction(transaction);
    }
    
    public void requestPayment(String from, String to, float recipientAmount) {
        SystemUserDetailsDTO sender = su.details(from);
        SystemUserDetailsDTO recipient = su.details(to);
        
        Float senderAmount = CurrencyExchange.convert(
                recipient.getCurrency(), 
                sender.getCurrency(),
                recipientAmount);
        
        MoneyTransactionDTO transaction = new MoneyTransactionDTO(
                sender, 
                recipient, 
                senderAmount,
                recipientAmount);
        
        mt.save(transaction);
    }
    
    public void acceptPendingTransaction(Long transactionId) {
        MoneyTransactionDTO transaction = mt.get(transactionId);
        completeTransaction(transaction);
    }
    
    public void declinePendingTransaction(Long transactionId) {
        MoneyTransactionDTO transaction = mt.get(transactionId);
        mt.delete(transaction);
    }
    
    public List<MoneyTransactionDTO> getTransactions(String username) {
        return mt.get(username);
    }
    
    public List<MoneyTransactionDTO> getOutgoingTransactions(String username) {
        return mt.outgoing(username);
    }
    
    public List<MoneyTransactionDTO> getIncomingTransactions(String username) {
        return mt.incoming(username);
    }
    
    private void completeTransaction(MoneyTransactionDTO transaction) {
        SystemUserDetailsDTO recipient = transaction.getRecipient();
        SystemUserDetailsDTO sender = transaction.getSender();
        
        if(sender.getBalance() >= transaction.getSenderAmount()) {
            sender.setBalance(
                    sender.getBalance() - transaction.getSenderAmount());
            recipient.setBalance(
                    recipient.getBalance() + transaction.getRecipientAmount());
            transaction.setPending(false);

            su.update(sender);
            su.update(recipient);
            if(transaction.getId() == null) {
                mt.save(transaction);
            } else {
                mt.update(transaction);
            }
        }
    }
}
