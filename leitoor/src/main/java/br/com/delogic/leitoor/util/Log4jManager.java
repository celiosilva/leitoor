package br.com.delogic.leitoor.util;

import java.util.Enumeration;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jManager {

    public static void setLevel(Level level) {
        Logger root = Logger.getRootLogger();
        @SuppressWarnings("rawtypes")
        Enumeration allLoggers = root.getLoggerRepository().getCurrentCategories();
        root.setLevel(level);
        while (allLoggers.hasMoreElements()) {
            Category tmpLogger = (Category) allLoggers.nextElement();
            tmpLogger.setLevel(level);
        }
    }

}
