package com.example.demo1;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		Map<String, String> inputParams = input.getQueryStringParameters();

		context.getLogger().log("Input request: " + input.toString());
		context.getLogger().log("Input: " + inputParams);
		String name = inputParams.get("name");
		context.getLogger().log("Name is - " + name);
		MyDto dto = new MyDto();
		dto.setId("id1");
		dto.setName("Tanmoyy");
		dto.setAddress("Testing my address");

		/*to fix cors issue with browser*/
		Map<String, String> responseheaders = new HashMap<>();
		responseheaders.put("Content-Type", "application/json");
		responseheaders.put("Access-Control-Allow-Origin", "*");
		
		ObjectMapper objMapper = new ObjectMapper();
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
		responseEvent.setHeaders(responseheaders);
		try {
			responseEvent.setBody(objMapper.writeValueAsString(dto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		responseEvent.setStatusCode(200);
		return responseEvent;
	}

}
