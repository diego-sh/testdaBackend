package com.testda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testda.tx.TxOrderProduct;
//import com.testda.tx.TxOrder;
import com.testda.util.WebRequest;
import com.testda.util.WebResponse;
import com.testda.util.WebResponseMessage;

@RestController
@RequestMapping(value = "/order")
public class OrderProductApi {

	@Autowired
	private WebResponse wrei;

	@Autowired
	private TxOrderProduct txOrderProduct;

	@RequestMapping(value = "/api", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> txQuickResponse(@RequestBody WebRequest wri) {

		switch (wri.getTransactionCode()) {

		case TxOrderProduct.TX_CODE_OrderSave:
			return this.txOrderProduct.TxQQROrderSave(wri);
			
		case TxOrderProduct.TX_CODE_GetAllOrders:
			return this.txOrderProduct.TxQQRGetAllOrders(wri);
			
		case TxOrderProduct.TX_CODE_DeleteOrder:
			return this.txOrderProduct.TxQQDeleteOrder(wri);

		default:
			wrei.setTransactionName("Transacción no encontrada");
			wrei.setTransactionCode("TxNotFound");
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			wrei.setMessage("Transacción no encontrada");
			return new ResponseEntity<Object>(wrei, HttpStatus.OK);

		}
	}

}
