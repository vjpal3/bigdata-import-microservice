package com.vrishalipal.microservices.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer stepTime;
	
	@Column(length = 10)
	private String type;
	
	private BigDecimal amount;
	
	@Column(length = 32)
	private String nameOrig;
	
	private BigDecimal oldBalanceOrig;
	
	private BigDecimal newBalanceOrig;
	
	@Column(length = 32)
	private String nameDest;
	
	private BigDecimal oldBalanceDest;
	
	private BigDecimal newBalanceDest;
	
	private Boolean isFraud;
	
	private Boolean isFlaggedFraud;
	
	private Instant entryDate;
	
	public Transaction() {
	}
	
	public Transaction(Long id, Integer stepTime, String type, BigDecimal amount, String nameOrig,
			BigDecimal oldBalanceOrig, BigDecimal newBalanceOrig, String nameDest, BigDecimal oldBalanceDest,
			BigDecimal newBalanceDest, Boolean isFraud, Boolean isFlaggedFraud, Instant entryDate) {
		super();
		this.id = id;
		this.stepTime = stepTime;
		this.type = type;
		this.amount = amount;
		this.nameOrig = nameOrig;
		this.oldBalanceOrig = oldBalanceOrig;
		this.newBalanceOrig = newBalanceOrig;
		this.nameDest = nameDest;
		this.oldBalanceDest = oldBalanceDest;
		this.newBalanceDest = newBalanceDest;
		this.isFraud = isFraud;
		this.isFlaggedFraud = isFlaggedFraud;
		this.entryDate = entryDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStepTime() {
		return stepTime;
	}

	public void setStepTime(Integer step) {
		this.stepTime = step;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNameOrig() {
		return nameOrig;
	}

	public void setNameOrig(String nameOrig) {
		this.nameOrig = nameOrig;
	}

	public BigDecimal getOldBalanceOrig() {
		return oldBalanceOrig;
	}

	public void setOldBalanceOrig(BigDecimal oldBalanceOrig) {
		this.oldBalanceOrig = oldBalanceOrig;
	}

	public BigDecimal getNewBalanceOrig() {
		return newBalanceOrig;
	}

	public void setNewBalanceOrig(BigDecimal newBalanceOrig) {
		this.newBalanceOrig = newBalanceOrig;
	}

	public String getNameDest() {
		return nameDest;
	}

	public void setNameDest(String nameDest) {
		this.nameDest = nameDest;
	}

	public BigDecimal getOldBalanceDest() {
		return oldBalanceDest;
	}

	public void setOldBalanceDest(BigDecimal oldBalanceDest) {
		this.oldBalanceDest = oldBalanceDest;
	}

	public BigDecimal getNewBalanceDest() {
		return newBalanceDest;
	}

	public void setNewBalanceDest(BigDecimal newBalanceDest) {
		this.newBalanceDest = newBalanceDest;
	}

	public Boolean getIsFraud() {
		return isFraud;
	}

	public void setIsFraud(Boolean isFraud) {
		this.isFraud = isFraud;
	}

	public Boolean getIsFlaggedFraud() {
		return isFlaggedFraud;
	}

	public void setIsFlaggedFraud(Boolean isFlaggedFraud) {
		this.isFlaggedFraud = isFlaggedFraud;
	}

	public Instant getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Instant entryDate) {
		this.entryDate = entryDate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", step=" + stepTime + ", type=" + type + ", amount=" + amount + ", nameOrig="
				+ nameOrig + ", oldBalanceOrig=" + oldBalanceOrig + ", newBalanceOrig=" + newBalanceOrig + ", nameDest="
				+ nameDest + ", oldBalanceDest=" + oldBalanceDest + ", newBalanceDest=" + newBalanceDest + ", isFraud="
				+ isFraud + ", isFlaggedFraud=" + isFlaggedFraud + ", entryDate=" + entryDate + "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
