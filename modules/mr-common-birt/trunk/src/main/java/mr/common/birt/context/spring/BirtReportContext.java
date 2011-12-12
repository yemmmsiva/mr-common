package mr.common.birt.context.spring;

import java.util.Properties;
import java.util.logging.Level;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.core.framework.PlatformFileContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.stereotype.Component;


/**
 * Clase encargada de iniciar el contexto de Birt para la generación de reportes.
 * Debe estar la propiedad birt.home para identificar la ruta del engine.
 * @author Mariano Ruiz
 */
@Component
public class BirtReportContext {

    @Resource
    private Properties appProperties;
    private static IReportEngine birtEngine = null;
    private static final BirtReportContext instance = new BirtReportContext();
    private static final Log logger = LogFactory.getLog(BirtReportContext.class);


    public synchronized IReportEngine initBirtEngine() {

        //Verificamos si se debe iniciar la plataforma birt
        if (appProperties.getProperty("birt.loadOnStartup") != null
                && appProperties.getProperty("birt.loadOnStartup").trim().toLowerCase().equals("false")) {
            logger.warn("The BIRT platform has not been started due to configuration parameters.");
        } else {
            EngineConfig config = new EngineConfig();
            config.setLogConfig(appProperties.getProperty("birt.logDirectory"),
            		            Level.parse(appProperties.getProperty("birt.logLevel")));
            config.setEngineHome("");
            PlatformConfig pc = new PlatformConfig();
            pc.setBIRTHome(appProperties.getProperty("birt.home"));
            PlatformFileContext context = new PlatformFileContext(pc);
            config.setPlatformContext(context);
            try {
                logger.info("Starting the BIRT platform.");
                Platform.startup(config);
                IReportEngineFactory factory = (IReportEngineFactory) Platform
                        .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
                birtEngine = factory.createReportEngine(config);
            } catch (BirtException e) {
                logger.error("Error starting the BIRT platform. ", e);
            }
        }
        return birtEngine;
    }

    @SuppressWarnings("deprecation")
	public static synchronized void destroyBirtEngine() {
        if (birtEngine == null) { return; }
        birtEngine.shutdown();
        Platform.shutdown();
        birtEngine = null;
    }

    public static IReportEngine getBirtEngine() {
        if (birtEngine == null) {
            logger.warn("The BIRT platform has not been started successfully.");
            instance.initBirtEngine();
        }
        return birtEngine;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
