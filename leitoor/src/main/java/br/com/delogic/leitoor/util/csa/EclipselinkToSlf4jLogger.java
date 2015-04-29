package br.com.delogic.leitoor.util.csa;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EclipselinkToSlf4jLogger extends AbstractSessionLog implements SessionLog {

    private final Logger logger = LoggerFactory.getLogger("EclipseLink");

    @Override
    public void log(SessionLogEntry entry) {

        switch (entry.getLevel()) {
            case SessionLog.OFF:
                break;
            case SessionLog.FINEST:
                logger.trace(entry.getMessage());
                break;
            case SessionLog.FINER:
                logger.trace(entry.getMessage());
                break;
            case SessionLog.FINE:
                logger.debug(entry.getMessage());
                break;
            case SessionLog.CONFIG:
                logger.debug(entry.getMessage());
                break;
            case SessionLog.INFO:
                logger.info(entry.getMessage());
                break;
            case SessionLog.WARNING:
                logger.warn(entry.getMessage());
                break;
            case SessionLog.SEVERE:
                logger.error(entry.getMessage());
                break;
            default:
                logger.info(entry.getMessage());
                break;
        }
    }

    @Override
    public boolean shouldLog(int level) {
        switch (level) {
            case SessionLog.OFF:
                return false;
            case SessionLog.FINEST:
                return logger.isTraceEnabled();
            case SessionLog.FINER:
                return logger.isTraceEnabled();
            case SessionLog.FINE:
                return logger.isDebugEnabled();
            case SessionLog.CONFIG:
                return logger.isInfoEnabled();
            case SessionLog.INFO:
                return logger.isInfoEnabled();
            case SessionLog.WARNING:
                return logger.isWarnEnabled();
            case SessionLog.SEVERE:
                return logger.isErrorEnabled();
            default:
                return logger.isInfoEnabled();
        }
    }

}
