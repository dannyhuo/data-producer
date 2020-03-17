package com.data.dataproducer.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author danny
 * @date 2020/3/17 10:20 AM
 */
@Data
@Builder
public class ImportInfo {
    private String jdbcUrl;
    private String userName;
    private String password;
    private String dataPath;
    private String tableName;
    private String splitChar;
    private Integer batchSize = 2000;
}
