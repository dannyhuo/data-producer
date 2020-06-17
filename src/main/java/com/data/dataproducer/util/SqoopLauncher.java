package com.data.dataproducer.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.SqoopOptions;
import org.apache.sqoop.hive.HiveImport;
import org.apache.sqoop.tool.SqoopTool;
import org.apache.sqoop.util.OptionsFileUtil;

/**
 * @author danny
 * @date 2020/5/27 11:37 AM
 */
public class SqoopLauncher {

    public static void sqoopImport (String[] args) throws Exception {



        String[] expandArguments = OptionsFileUtil.expandArguments(args);
        SqoopTool tool = SqoopTool.getTool("import");

        Configuration conf = new Configuration();
        Configuration sqoopLoadConf = SqoopTool.loadPlugins(conf);


        Sqoop sqoop = new Sqoop((com.cloudera.sqoop.tool.SqoopTool) tool, sqoopLoadConf);



        int res = Sqoop.runSqoop(sqoop, expandArguments);





        SqoopOptions options = new SqoopOptions();
        options.setConnectString("");
        options.setUsername("");
        options.setPassword("");
        options.setNumMappers(8);
        options.setSplitByCol("");

        tool = SqoopTool.getTool("import");
        int result = tool.run((com.cloudera.sqoop.SqoopOptions) options);



    }
}
