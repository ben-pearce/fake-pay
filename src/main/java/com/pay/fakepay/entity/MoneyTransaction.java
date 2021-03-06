package com.pay.fakepay.entity;

import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
            name="MoneyTransaction.getByUsername",
            query="SELECT t FROM MoneyTransaction t "
                    + "WHERE (t.recipient.username = :username "
                    + "OR t.sender.username = :username) "
                    + "AND t.pending = false"),
    @NamedQuery(
            name="MoneyTransaction.getOutgoing",
            query="SELECT t FROM MoneyTransaction t "
                    + "WHERE t.sender.username = :username "
                    + "AND t.pending = true"
    ),
    @NamedQuery(
            name="MoneyTransaction.getIncoming",
            query="SELECT t FROM MoneyTransaction t "
                    + "WHERE t.recipient.username = :username "
                    + "AND t.pending = true"
    ),
    @NamedQuery(
        name="MoneyTransaction.getAll",
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
    private float senderAmount;
    
    @NotNull
    private float recipientAmount;
    
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn;
    
    @NotNull
    private boolean pending;

    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            float senderAmount,
            float recipientAmount,
            LocalDateTime createdOn,
            boolean pending) {
        this.sender = sender;
        this.recipient = recipient;
        this.senderAmount = senderAmount;
        this.recipientAmount = recipientAmount;
        this.createdOn = createdOn;
        this.pending = pending;
    }
    
    public MoneyTransaction(
            SystemUser sender, 
            SystemUser recipient, 
            float senderAmount,
            float recipientAmount,
            LocalDateTime createdOn) {
        this(sender, recipient, senderAmount, recipientAmount, createdOn, true);
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
    
    public MoneyTransactionDTO getMoneyTransactionData() {
        return createMoneyTransaction();
    }
    
    private MoneyTransactionDTO createMoneyTransaction() {
        return new MoneyTransactionDTO(
                getId(), 
                getSender().getSystemUserDetailsData(), 
                getRecipient().getSystemUserDetailsData(), 
                getSenderAmount(), 
                getRecipientAmount(), 
                getCreatedOn(),
                isPending());
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
