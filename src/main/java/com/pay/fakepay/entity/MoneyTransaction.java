package com.pay.fakepay.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ben
 */
@Entity
public class MoneyTransaction implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @JoinColumn(name="sender_id", referencedColumnName="id")
    @ManyToOne
    private SystemUser sender;
    
    @NotNull
    @JoinColumn(name="recipient_id", referencedColumnName="id")
    @ManyToOne
    private SystemUser recipient;
    
    @NotNull
    private int amount;
    
    @NotNull
    private boolean pending;

    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            int amount,
            boolean pending) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.pending = pending;
    }
    
    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.pending = true;
    }    
    public MoneyTransaction() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUser getSender() {
        return sender;
    }

    public void setSender(SystemUser sender) {
        this.sender = sender;
    }

    public SystemUser getRecipient() {
        return recipient;
    }

    public void setRecipient(SystemUser recipient) {
        this.recipient = recipient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.sender);
        hash = 79 * hash + Objects.hashCode(this.recipient);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MoneyTransaction other = (MoneyTransaction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
