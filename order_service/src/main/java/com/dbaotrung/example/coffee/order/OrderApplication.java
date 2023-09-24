package com.dbaotrung.example.coffee.order;

import com.dbaotrung.example.coffee.common.constant.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.util.StringUtils;

import java.io.File;

@SpringBootApplication(scanBasePackages = {"com.kbtg.kuniv", "tech.corefinance.common"})
public class OrderApplication {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(OrderApplication.class);
        String logFileNameDefault = "order_app";
        if (!StringUtils.hasText(System.getProperty(CommonConstants.LOGBACK_FILE_PATH_KEY))) {
            System.setProperty(CommonConstants.LOGBACK_FILE_PATH_KEY, CommonConstants.LOGBACK_FILE_PATH_DEFAULT);
        }
        log.info("Log folder path [{}]", System.getProperty(CommonConstants.LOGBACK_FILE_PATH_KEY));
        if (!StringUtils.hasText(System.getProperty(CommonConstants.LOGBACK_FILE_NAME_KEY))) {
            System.setProperty(CommonConstants.LOGBACK_FILE_NAME_KEY, logFileNameDefault);
        }
        log.info("Log file name [{}]", System.getProperty(CommonConstants.LOGBACK_FILE_NAME_KEY));
        SpringApplicationBuilder app = new SpringApplicationBuilder(OrderApplication.class);
        File file = new File(CommonConstants.LOGBACK_FILE_PATH_DEFAULT + "/" + logFileNameDefault + "_shutdown.pid");
        app.build().addListeners(new ApplicationPidFileWriter(file));
        app.run(args);
        log.info("Generated PID file {}", file.getAbsolutePath());
    }
}
