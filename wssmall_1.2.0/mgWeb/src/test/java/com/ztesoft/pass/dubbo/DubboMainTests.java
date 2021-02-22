package com.ztesoft.pass.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-10 16:22
 * To change this template use File | Settings | File Templates.
 */
public class DubboMainTests {
    private static final String DUBBO_PROTOCOL_PORT="dubbo.protocol.port";
    private static final String DUBBO_SPRING_CONFIG="dubbo.spring.config";

    public static final String CONTAINER_KEY = "dubbo.container";

    public static final String SHUTDOWN_HOOK_KEY = "dubbo.shutdown.hook";

    private static final Logger logger = LoggerFactory.getLogger(DubboMainTests.class);

    private static final ExtensionLoader<Container> loader = ExtensionLoader.getExtensionLoader(Container.class);

    private static volatile boolean running = true;

    private static Properties ps=null;

    private static final String DUBBO_FIFLE="/config/dubbo.properties";


    @Test
    public void run() {
        String[] args=null;
        ps=new Properties();
        try{
            ps.load(Thread.currentThread().getClass().getResourceAsStream(DUBBO_FIFLE));
            //设置dubbo服务端口
            System.setProperty(DUBBO_PROTOCOL_PORT,ps.getProperty(DUBBO_PROTOCOL_PORT));
            System.setProperty(DUBBO_SPRING_CONFIG,ps.getProperty(DUBBO_SPRING_CONFIG));
        }catch (IOException e){
            logger.debug("加载dubbo配置文件"+DUBBO_FIFLE+"出错!");
            e.printStackTrace();
        }
        try {

            if (args == null || args.length == 0) {
                String config = ConfigUtils.getProperty(CONTAINER_KEY, loader.getDefaultExtensionName());
                args = Constants.COMMA_SPLIT_PATTERN.split(config);
            }

            final List<Container> containers = new ArrayList<Container>();
            for (int i = 0; i < args.length; i ++) {
                containers.add(loader.getExtension(args[i]));
            }
            logger.info("Use container type(" + Arrays.toString(args) + ") to run dubbo serivce.");

            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
					public void run() {
                        for (Container container : containers) {
                            try {
                                container.stop();
                                logger.info("Dubbo " + container.getClass().getSimpleName() + " stopped!");
                            } catch (Throwable t) {
                                logger.info(t.getMessage(), t);
                            }
                            synchronized (DubboMainTests.class) {
                                running = false;
                                DubboMainTests.class.notify();
                            }
                        }
                    }
                });
            }

            for (Container container : containers) {
                container.start();
                logger.info("Dubbo " + container.getClass().getSimpleName() + " started!");
            }
            logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.info(e.getMessage(), e);
            System.exit(1);
        }
        synchronized (DubboMainTests.class) {
            while (running) {
                try {
                    DubboMainTests.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
}
