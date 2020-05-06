/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */
package org.smpp.smscsim;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.smpp.pdu.SubmitSM;
import org.smpp.smscsim.ShortMessageValue;

/**
 * Class <code>ShortMessageStore</code> is used to store the sms's sent by
 * a client to the smsc. Provides method to store the message, cancel
 * the message and replace it as well as methods for printing of all
 * the messages to standard output.
 *
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version $Revision: 1.2 $
 * @see ShortMessageValue
 */
public class BlackholeShortMessageStore extends ShortMessageStore{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Construct the message store.
	 */
	public BlackholeShortMessageStore() {
	}

	/**
	 * Print the message to logger and discard 
	 * 
	 * @param message the message received from the client
	 * @param messageId the message id assigned by smsc
	 * @param systemId the system id of the client to distinguish different
	 *                 clients
	 * @see ShortMessageValue
	 * @see org.smpp.pdu.SubmitSM
	 */
	@Override
	public synchronized void submit(SubmitSM message, String messageId, String systemId) throws UnsupportedEncodingException {
		MDC.put("srcAddr", message.getSourceAddr().getAddress());
		MDC.put("destAddr", message.getDestAddr().getAddress());
		MDC.put("shortMsg", message.getShortMessage("utf-8"));
		
		logger.info("SubmitSM: source = {}, dest = {}, msg = {}", 
			message.getSourceAddr().getAddress(), message.getDestAddr().getAddress(), message.getShortMessage("utf-8"));
		
		MDC.clear();
	}

	/**
	 * No operation
	 *
	 * @param messageId id of the message to remove
	 */
	@Override
	public synchronized void cancel(String messageId) {
		
	}

	/**
	 * No operation
	 *
	 * @param messageId id of message to replace
	 * @param newMessage the text of the new message
	 */
	@Override
	public synchronized void replace(String messageId, String newMessage) {
		
	}

	/**
	 * No operation
	 *
	 * @param messageId the messag id of the message to return
	 * @see ShortMessageValue
	 */
	@Override
	public synchronized ShortMessageValue getMessage(String messageId) {
		return null;
	}

	/**
	 * No operation.
	 */
	@Override
	public synchronized void print() {		
		System.out.println("Blackhole: There is no message in the message store.");		
	}

	/**
	 * Prints one message.
	 *
	 * @param key the key (message id) of the message
	 * @param sMV the message to print
	 */
	
}
/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1  2003/07/23 00:28:39  sverkera
 * Imported
 *
 * 
 * Old changelog:
 * 20-09-01 ticp@logica.com added getMessage() returning a message data
 *                          for message with given messageId
 */
