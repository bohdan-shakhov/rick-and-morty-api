package com.example.rickandmorty.backup;

import com.smattme.MysqlExportService;
import com.smattme.MysqlImportService;
import org.apache.log4j.Logger;
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
public class BackupDatabase {
    private static final Logger LOGGER = Logger.getLogger(BackupDatabase.class);

    @Scheduled(cron = "0 3 * * * ?")
    public void backup() {
        LOGGER.info("-------------------BACKUP--------------------");
        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME, "test");
        properties.setProperty(MysqlExportService.DB_USERNAME, "acolyte");
        properties.setProperty(MysqlExportService.DB_PASSWORD, "marvel10");
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("backup").getPath());
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_SQL_FILE, "true");

        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        mysqlExportService.getGeneratedZipFile();
        LOGGER.info("-------------------BACKUP FINISHED--------------------");
        try {
            mysqlExportService.export();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            LOGGER.error("Failed to backup database", e);
        }
    }

    @Scheduled(cron = "0 3 1 * * ?")
    public void restore() {
        LOGGER.info("---------------RESTORE-------------------------");
        String sql = null;
        File dir = new File("backup/sql");
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.sort(files);
        }
        try {
            sql = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(files)[files.length - 1].toString())));
            LOGGER.debug(files[files.length - 1].toString());
        } catch (IOException e) {
            LOGGER.error("Failed to find backup file", e);
        }
        try {
            MysqlImportService.builder()
                    .setDatabase("test")
                    .setSqlString(sql)
                    .setUsername("acolyte")
                    .setPassword("marvel10")
                    .setDeleteExisting(true)
                    .setDropExisting(true)
                    .importDatabase();
            LOGGER.info("----------------RESTORE FINISHED--------------------");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Failed to restore database", e);
        }
    }
}
