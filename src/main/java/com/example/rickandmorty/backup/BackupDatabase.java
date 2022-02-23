package com.example.rickandmorty.backup;

import com.smattme.MysqlExportService;
import com.smattme.MysqlImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@Component
@Slf4j
public class BackupDatabase {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @Scheduled(cron = "0 3 * * * ?")
    public void backup() {
        Properties properties = new Properties();

        properties.setProperty(MysqlExportService.DB_NAME, "test");
        properties.setProperty(MysqlExportService.DB_USERNAME, username);
        properties.setProperty(MysqlExportService.DB_PASSWORD, password);
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("backup").getPath());
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_SQL_FILE, "true");

        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        mysqlExportService.getGeneratedZipFile();
        try {
            mysqlExportService.export();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error("Failed to backup database", e);
        }
    }

    public void restore() {
        String sql = null;
        File dir = new File("backup/sql");
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.sort(files);
        }
        try {
            sql = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(files)[files.length - 1].toString())));
            log.debug(files[files.length - 1].toString());
        } catch (IOException e) {
            log.error("Failed to find backup file", e);
        }
        try {
            MysqlImportService.builder()
                    .setDatabase("test")
                    .setSqlString(sql)
                    .setUsername(username)
                    .setPassword(password)
                    .setDeleteExisting(true)
                    .setDropExisting(true)
                    .importDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Failed to restore database", e);
        }
    }
}
