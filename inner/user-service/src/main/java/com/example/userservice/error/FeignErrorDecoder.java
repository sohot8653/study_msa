package com.example.userservice.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder{
	@Autowired
	Environment env;

	@Override
	public Exception decode(String methodKey, Response response) {
		// FeignErrorDecoder에 대한 처리는 하고 있으나, 해당 메서드 호출이 안됨.
		// 원인 확인 필요
		switch(response.status()) {
			case 400:
				break;
			case 404:
				if(methodKey.contains("getOrders")) {
					return new ResponseStatusException(HttpStatus.valueOf(response.status()), env.getProperty("order_service.exception.orders_is_empty"));
				}
				break;
			default:
				return new Exception(response.reason());
		}
		
		return null;
	}

}
