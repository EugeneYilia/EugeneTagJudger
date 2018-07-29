package EugeneContainer.startup;

import EugeneContainer.catalina.Container;
import EugeneContainer.config.ServletConfigContext;
import EugeneContainer.server.Connector;
import EugeneContainer.server.Processor;

public class Bootstrap {
    public static void main(String[] args) {
        Connector connector = new Connector();
        connector.init(new Container());
        ServletConfigContext.init();
        Processor.init();
        connector.start();
    }
}
