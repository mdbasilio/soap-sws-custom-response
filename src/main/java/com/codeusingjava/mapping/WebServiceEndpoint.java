package com.codeusingjava.mapping;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import com.codeusingjava.InputSOATest;
import com.codeusingjava.ObjectFactory;
import com.codeusingjava.OutputSOATest;

@Endpoint
public class WebServiceEndpoint {

	private static final String NAMESPACE_URI = "http://codeusingjava.com";
	/*
	 * @PayloadRoot(namespace = NAMESPACE_URI, localPart = "inputSOATest")
	 * 
	 * @ResponsePayload
	 * public OutputSOATest hello(@RequestPayload InputSOATest req) {
	 * 
	 * String outputString = "Hello " + req.getTest() + "!";
	 * 
	 * ObjectFactory factory = new ObjectFactory();
	 * OutputSOATest response = factory.createOutputSOATest();
	 * response.setResult(outputString);
	 * 
	 * return response;
	 * }
	 */

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "inputSOATest")
	@ResponsePayload
	public OutputSOATest hello(@RequestPayload InputSOATest req) throws SOAPException, TransformerException {
		String outputString = "Hello " + req.getTest() + "!";
	
		// Crear un objeto OutputSOATest y establecer el resultado
		ObjectFactory factory = new ObjectFactory();
		OutputSOATest response = factory.createOutputSOATest();
		response.setResult(outputString);
	
		// Devolver la respuesta
		return response;
	}
}