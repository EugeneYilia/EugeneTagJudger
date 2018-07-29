package EugeneContainer.server;


import EugeneContainer.catalina.Container;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static EugeneContainer.server.Constants.*;

public class Processor implements Runnable {
    public Socket socket = null;
    private Container container = null;
    private Request request;
    public static ExecutorService executorService = null;


    public static void init() {
        executorService = Executors.newFixedThreadPool(PROCESSOR_POOL_SIZE);
    }

    public Processor(Socket socket, Container container) {
        this.socket = socket;
        this.container = container;
    }

    public static void assign(Socket socket, Container container) {
        Thread thread = new Thread(new Processor(socket, container));
        executorService.execute(thread);
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            request = createRequest(inputStream);
            Response response = createResponse(outputStream);
            response.setRequest(request);
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            if (!request.getRequestURI().contains("/servlet/")) {
                response.sendStaticResource();
            } else {
                container.invoke(requestFacade, responseFacade);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request createRequest(InputStream inputStream) {
        return new Request(inputStream);
    }

    public Response createResponse(OutputStream outputStream) {
        Response response = new Response(outputStream);
        response.init(request);
        return response;
    }

}
