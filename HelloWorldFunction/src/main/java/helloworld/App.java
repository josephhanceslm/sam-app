package helloworld;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handler for requests to Lambda function.
 * 
 * See:
 * https://stackoverflow.com/questions/36599104/aws-lambda-java-response-does-not-support-nested-objects
 * https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-output-format
 * http://www.javacreed.com/simple-gson-example/
 */
public class App implements RequestStreamHandler, RequestHandler<Object, Object> {
    public String[] arr = { "A", "B" };

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        ResponseObject ro = new ResponseObject();
        ro.arr = arr;
        ro.str = "Hello World!";
        ro.number = 5;

        GatewayResponse resp = new GatewayResponse(new Gson().toJson(ro), headers, 200);
        // Gson gson = new GsonBuilder().create();
        // String x = gson.toJson(resp);
        // output.write(x.getBytes());
        output.write(new Gson().toJson(resp).getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public Object handleRequest(Object input, Context context) {
        return null;
    }
}
