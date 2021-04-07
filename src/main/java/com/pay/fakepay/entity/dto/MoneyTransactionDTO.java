package com.pay.fakepay.entity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MoneyTransactionDTO implements Serializable {
    private Long id;
    
    private SystemUserDetailsDTO sender;
    
    private SystemUserDetailsDTO recipient;
    
    private float senderAmount;
    
    private float recipientAmount;
    
    private boolean pending;
    
    private LocalDateTime createdOn;

    public MoneyTransactionDTO(
            Long id, 
            SystemUserDetailsDTO sender, 
            SystemUserDetailsDTO recipient, 
            float senderAmount, 
            float recipientAmount, 
            LocalDateTime createdOn,
            boolean pending) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.senderAmount = senderAmount;
        this.recipientAmount = recipientAmount;
        this.createdOn = createdOn;
        this.pending = pending;
    }
    
    public MoneyTransactionDTO(
            SystemUserDetailsDTO sender, 
            SystemUserDetailsDTO recipient, 
            float senderAmount, 
            float recipientAmount,
            LocalDateTime createdOn) {
        this(null, sender, recipient, senderAmount, 
                recipientAmount, createdOn, true);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public SystemUserDetailsDTO getSender() {
        return sender;
    }
    
    public void setSender(SystemUserDetailsDTO sender) {
        this.sender = sender;
    }
    
    public SystemUserDetailsDTO getRecipient() {
        return recipient;
    }
    
    public void setRecipient(SystemUserDetailsDTO recipient) {
        this.recipient = recipient;
    }

    public float getSenderAmount() {
        return senderAmount;
    }
    
    public void setSenderAmount(float senderAmount) {
        this.senderAmount = senderAmount;
    }
    
    public float getRecipientAmount() {
        return recipientAmount;
    }
    
    public void setRecipientAmount(float recipientAmount) {
        this.recipientAmount = recipientAmount;
    }

    public boolean isPending() {
        return pending;
    }
    
    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
