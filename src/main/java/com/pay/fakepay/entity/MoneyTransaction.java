package com.pay.fakepay.entity;

import com.pay.fakepay.Currency;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries({
    @NamedQuery(
        name="MoneyTransaction.byReceipientId", 
        query="SELECT t FROM MoneyTransaction t "
                + "WHERE t.recipient = :recipientId "
                + "AND t.pending = false"),
    @NamedQuery(
            name="MoneyTransaction.bySenderId",
            query="SELECT t FROM MoneyTransaction t "
                    + "WHERE t.sender = :senderId "
                    + "AND t.pending = false"),
    @NamedQuery(
            name="MoneyTransaction.byUsername",
            query="SELECT t FROM MoneyTransaction t "
                    + "WHERE t.recipient.username = :username "
                    + "OR t.sender.username = :username "
                    + "AND t.pending = false"),
    @NamedQuery(
        name="MoneyTransaction.findAll",
        query="SELECT t FROM MoneyTransaction t "
                + "WHERE t.pending = false")
})
public class MoneyTransaction implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @JoinColumn(name="sender_username", referencedColumnName="username")
    @ManyToOne
    private SystemUser sender;
    
    @NotNull
    @JoinColumn(name="recipient_username", referencedColumnName="username")
    @ManyToOne
    private SystemUser recipient;
    
    @NotNull
    private float amount;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    
    @NotNull
    private boolean pending;

    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            float amount,
            Currency currency,
            boolean pending) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.currency = currency;
        this.pending = pending;
    }
    
    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            int amount,
            Currency currency) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.currency = currency;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        return Objects.equals(this.id, other.id);
    }
}
