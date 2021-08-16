package com.javatechie.trx.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.javatechie.trx.dto.FlighBookingRequest;
import com.javatechie.trx.dto.FlightBookingAcknowledgement;
import com.javatechie.trx.entity.Passanger;
import com.javatechie.trx.entity.Payment;
import com.javatechie.trx.repository.PassangerRepository;
import com.javatechie.trx.repository.PaymentRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import utils.PaymentUtils;

@Slf4j // TODO: Not able to print log
@Service
public class FlightBookingService {
	
	@Autowired
	private PassangerRepository passangerRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public FlightBookingAcknowledgement bookFlightTicket(FlighBookingRequest request){
		
		 Passanger passanger = request.getPassanger();
		 Passanger p = passangerRepository.saveAndFlush(passanger);
		 
		 System.out.println("Saved passanger: " + p);
		 
		 Payment payment = request.getPayment();
		 PaymentUtils.validateCreditLimit(payment.getAccountNo(), passanger.getFare());				
		 payment.setPassangerId(passanger.getPid());
		 payment.setAmount(passanger.getFare());
		 
		 Payment pay = paymentRepository.saveAndFlush(payment);
		 System.out.println("Saved Payment: " + pay);
		 
		return new FlightBookingAcknowledgement("SUCCESS", passanger.getFare(), UUID.randomUUID().toString().split("-")[0], passanger);
		
		
	}

}
