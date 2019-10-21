package com.testda.tx;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testda.model.Article;
import com.testda.model.OrderProduct;
import com.testda.service.IOrderProductService;
import com.testda.util.Crypto;
import com.testda.util.WebRequest;
import com.testda.util.WebResponse;
import com.testda.util.WebResponseMessage;

@Component
public class TxOrderProduct {
	
	public static final String TX_NAME_OrderSave = "OrderSave";
	public static final String TX_CODE_OrderSave = "TxQQROrderSave";
	
	public static final String TX_NAME_GetAllOrders = "GetAllOrders";
	public static final String TX_CODE_GetAllOrders = "TxQQRGetAllOrders";
	
	public static final String TX_NAME_DeleteOrder = "DeleteOrder";
	public static final String TX_CODE_DeleteOrder = "TxQQRDeleteOrder";

	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();


	@Autowired
	private IOrderProductService orderProductService;
	
	/**
	 * TX NAME: OrderSave: Guarda/actualiza la orden de los artículos
	 * 
	 */
	public ResponseEntity<Object> TxQQROrderSave(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_OrderSave);
		wrei.setTransactionCode(TX_CODE_OrderSave);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				OrderProduct order = JSON_MAPPER.readValue(jsonValue, OrderProduct.class);
				order.setDate(LocalDate.now());
				order = this.orderProductService.create(order);

				if (order != null) {
					String json = JSON_MAPPER.writeValueAsString(order);
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
	public ResponseEntity<Object> TxQQRGetAllOrders(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_GetAllOrders);
		wrei.setTransactionCode(TX_CODE_GetAllOrders);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {

				List<OrderProduct> listOrders = this.orderProductService.findAll();

				if (listOrders != null || !listOrders.isEmpty()) {
					String json = JSON_MAPPER.writeValueAsString(listOrders);
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
	public ResponseEntity<Object> TxQQDeleteOrder(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_DeleteOrder);
		wrei.setTransactionCode(TX_CODE_DeleteOrder);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				OrderProduct order = JSON_MAPPER.readValue(jsonValue, OrderProduct.class);
				this.orderProductService.delete(order.getId().toString());				
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
