package com.testda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testda.tx.TxClient;
import com.testda.util.WebRequest;
import com.testda.util.WebResponse;
import com.testda.util.WebResponseMessage;

@RestController
@RequestMapping(value = "/client")
public class ClientApi {

	@Autowired
	private WebResponse wrei;

	@Autowired
	private TxClient txClient;
	
	
	@RequestMapping(value = "/api", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> txQuickResponse(@RequestBody WebRequest wri) {

		switch (wri.getTransactionCode()) {

		case TxClient.TX_CODE_ClientSave:
			return this.txClient.TxQQRClientSave(wri);
			
		case TxClient.TX_CODE_GetAllClient:
			return this.txClient.TxQQRGetAllClient(wri);
		
		case TxClient.TX_CODE_DeleteClient:
			return this.txClient.TxQQDeleteClient(wri);
			

		default:
			wrei.setTransactionName("Transacción no encontrada");
			wrei.setTransactionCode("TxNotFound");
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			wrei.setMessage("Transacción no encontrada");
			return new ResponseEntity<Object>(wrei, HttpStatus.OK);

		}

	}

}
