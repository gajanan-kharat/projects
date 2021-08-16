package com.javatechie.trx.dto;

import com.javatechie.trx.entity.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingAcknowledgement {
	private String status;
	private double totalFare;
	
	private String pnrNo;
	private Passanger passanger;
	public FlightBookingAcknowledgement(String status, double totalFare, String pnrNo, Passanger passanger) {
		super();
		this.status = status;
		this.totalFare = totalFare;
		this.pnrNo = pnrNo;
		this.passanger = passanger;
	}
	
	

}
