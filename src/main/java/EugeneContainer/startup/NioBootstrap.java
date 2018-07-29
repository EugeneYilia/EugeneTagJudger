package EugeneContainer.startup;

import EugeneContainer.server.NIOConnector;
import EugeneContainer.server.Processor;

public class NioBootstrap {
    public static void main(String[] args) {
        Processor.init();
        new NIOConnector().init().start();
    }
}
