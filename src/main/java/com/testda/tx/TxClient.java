package com.testda.tx;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testda.model.Client;
import com.testda.service.IClientService;
import com.testda.util.Crypto;
import com.testda.util.WebRequest;
import com.testda.util.WebResponse;
import com.testda.util.WebResponseMessage;

@Component
public class TxClient {

	public static final String TX_NAME_ClientSave = "ClientSave";
	public static final String TX_CODE_ClientSave = "TxQQRClientSave";

	public static final String TX_NAME_GetAllClient = "GetAllClient";
	public static final String TX_CODE_GetAllClient = "TxQQRGetAllClient";
	
	public static final String TX_NAME_DeleteClient = "DeleteClient";
	public static final String TX_CODE_DeleteClient = "TxQQRDeleteClient";

	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	@Autowired
	private IClientService clientService;

	/**
	 * TX NAME: ClientSave: Guarda/actualiza la orden de los artículos
	 * 
	 */
	public ResponseEntity<Object> TxQQRClientSave(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_ClientSave);
		wrei.setTransactionCode(TX_CODE_ClientSave);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				Client client = JSON_MAPPER.readValue(jsonValue, Client.class);
				client = this.clientService.create(client);

				if (client != null) {
					String json = JSON_MAPPER.writeValueAsString(client);
					String jsonCryp = Crypto.encrypt(json);
					wrei.setParameters(jsonCryp);
					wrei.setStatus(WebResponseMessage.STATUS_OK);
					wrei.setMessage(WebResponseMessage.CREATE_ORDER_OK);
					return new ResponseEntity<Object>(wrei, HttpStatus.OK);

				} else {
					wrei.setMessage(WebResponseMessage.CREATE_ORDER_ERROR);
					wrei.setStatus(WebResponseMessage.STATUS_ERROR);
					return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}

	/**
	 * TX NAME: GetAllClient: consulta todos los clientes.
	 * 
	 */
	public ResponseEntity<Object> TxQQRGetAllClient(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_GetAllClient);
		wrei.setTransactionCode(TX_CODE_GetAllClient);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {

				List<Client> listClient = this.clientService.findAll();

				if (listClient != null || !listClient.isEmpty()) {
					String json = JSON_MAPPER.writeValueAsString(listClient);
					String jsonCryp = Crypto.encrypt(json);
					wrei.setParameters(jsonCryp);
					wrei.setStatus(WebResponseMessage.STATUS_OK);
					wrei.setMessage(WebResponseMessage.OBJECT_FIND_OK);
					return new ResponseEntity<Object>(wrei, HttpStatus.OK);

				} else {
					wrei.setMessage(WebResponseMessage.OBJECT_FIND_EMPTY);
					wrei.setStatus(WebResponseMessage.STATUS_INFO);
					return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}

	/**
	 * TX NAME: DeleteClient: Elimina un cliente determinado.
	 * 
	 */
	public ResponseEntity<Object> TxQQDeleteClient(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_DeleteClient);
		wrei.setTransactionCode(TX_CODE_DeleteClient);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {

				Client client = JSON_MAPPER.readValue(jsonValue, Client.class);
				this.clientService.delete(client.getId().toString());				
				wrei.setStatus(WebResponseMessage.STATUS_OK);
				wrei.setMessage(WebResponseMessage.OBJECT_DELETED);
				return new ResponseEntity<Object>(wrei, HttpStatus.OK);
				
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}

}
