package com.javatechie.trx.dto;

import com.javatechie.trx.entity.Passanger;
import com.javatechie.trx.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlighBookingRequest {

	private Passanger passanger;
	
	private Payment payment;
}
