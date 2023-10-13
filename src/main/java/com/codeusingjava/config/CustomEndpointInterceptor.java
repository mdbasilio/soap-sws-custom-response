package com.codeusingjava.config;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

@Component
public class CustomEndpointInterceptor implements EndpointInterceptor {

    private static final Log LOG = LogFactory.getLog(CustomEndpointInterceptor.class);
    private static final String PREFERRED_PREFIX = "soapenv";
    private static final String SOAP_ENV_NAMESPACE = "SOAP-ENV";

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        LOG.info("Endpoint Request Handling");
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        SaajSoapMessage response = (SaajSoapMessage) messageContext.getResponse();
        alterSoapEnvelope(response);
        LOG.info("Endpoint Response Handling");
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        LOG.info("Endpoint Exception Handling");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        SaajSoapMessage response = (SaajSoapMessage) messageContext.getResponse();
        SOAPMessage soapMessage = response.getSaajMessage();
        
        if(soapMessage.getSOAPHeader() != null){
            soapMessage.getSOAPHeader().detachNode();
            soapMessage.saveChanges();
        }

        LOG.info("Execute code after completion");
    }

    private void alterSoapEnvelope(SaajSoapMessage soapResponse) {
        try {
            SOAPMessage soapMessage = soapResponse.getSaajMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = soapMessage.getSOAPBody();
            SOAPHeader header = soapMessage.getSOAPHeader();
            SOAPFault fault = body.getFault();
            envelope.removeNamespaceDeclaration(envelope.getPrefix());
            envelope.addNamespaceDeclaration(PREFERRED_PREFIX, SOAP_ENV_NAMESPACE);
            envelope.setPrefix(PREFERRED_PREFIX);
            body.setPrefix(PREFERRED_PREFIX);
            header.setPrefix(PREFERRED_PREFIX);
            if (fault != null) {
                fault.setPrefix(PREFERRED_PREFIX);
            }
            soapMessage.saveChanges();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}