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
	
	private Integer step;
	
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
	
	public Transaction(Long id, Integer step, String type, BigDecimal amount, String nameOrig,
			BigDecimal oldBalanceOrig, BigDecimal newBalanceOrig, String nameDest, BigDecimal oldBalanceDest,
			BigDecimal newBalanceDest, Boolean isFraud, Boolean isFlaggedFraud, Instant entryDate) {
		super();
		this.id = id;
		this.step = step;
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

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", step=" + step + ", type=" + type + ", amount=" + amount + ", nameOrig="
				+ nameOrig + ", oldBalanceOrig=" + oldBalanceOrig + ", newBalanceOrig=" + newBalanceOrig + ", nameDest="
				+ nameDest + ", oldBalanceDest=" + oldBalanceDest + ", newBalanceDest=" + newBalanceDest + ", isFraud="
				+ isFraud + ", isFlaggedFraud=" + isFlaggedFraud + ", entryDate=" + entryDate + "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
