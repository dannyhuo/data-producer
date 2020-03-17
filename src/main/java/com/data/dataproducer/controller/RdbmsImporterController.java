package com.data.dataproducer.controller;

import com.data.dataproducer.entity.ImportInfo;
import com.data.dataproducer.util.RdbmsImporterUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @author danny
 * @date 2020/3/17 10:32 AM
 */
@RestController
@RequestMapping("/importer/rdbms")
public class RdbmsImporterController {

    @PostMapping("/import")
    public int improt (@RequestBody ImportInfo importInfo) throws SQLException, ClassNotFoundException {
        return RdbmsImporterUtil.import2rdbms(importInfo);
    }
}
