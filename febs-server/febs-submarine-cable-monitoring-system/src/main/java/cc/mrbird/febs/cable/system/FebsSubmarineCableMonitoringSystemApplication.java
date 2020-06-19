package cc.mrbird.febs.cable.system;

import cc.mrbird.febs.common.security.starter.annotation.EnableFebsCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author MrBird
 */
@EnableAsync
@SpringBootApplication
@EnableFebsCloudResourceServer
@EnableTransactionManagement
@MapperScan("cc.mrbird.febs.cable.system.mapper")
public class FebsSubmarineCableMonitoringSystemApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FebsSubmarineCableMonitoringSystemApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
