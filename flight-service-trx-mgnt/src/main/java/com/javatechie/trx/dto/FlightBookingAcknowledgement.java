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

}
