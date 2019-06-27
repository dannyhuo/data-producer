package com.data.dataproducer.util;

import java.io.*;

/**
 * @author danny
 * @date 2019/5/30 7:58 PM
 */
public class ReadUtil {
    /**
     * Read a file if file is exists.
     * @param path The file path
     * @param doer Do a line
     * @param startRow Read from the row start
     * @param endRow  Read to the row end
     * @param charset Charset
     */
    public static void read(String path, DoLiner doer, String charset, int startRow, int endRow){
        File file = new File(path);
        if(file.exists()){
            int count = 0;
            InputStreamReader in = null;
            BufferedReader reader = null;
            try {
                in = new InputStreamReader(new FileInputStream(file), charset);
                reader = new BufferedReader(in);
                if(endRow > 0){
                    while(reader.ready() && count < endRow){
                        String line = reader.readLine();
                        count++;
                        if(count >= startRow){
                            doer.accept(line);
                        }
                    }
                }else{
                    while(reader.ready()){
                        String line = reader.readLine();
                        count++;
                        if(count >= startRow){
                            doer.accept(line);
                        }
                    }
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Read a file if file is exists.
     * @param doer Do a line
     * @param startRow Read from the row start
     * @param endRow  Read to the row end
     * @param charset Charset
     */
    public static void read(InputStream stream, DoLiner doer, String charset, int startRow, int endRow){
        if(null != stream){
            int count = 0;
            InputStreamReader in = null;
            BufferedReader reader = null;
            try {
                in = new InputStreamReader(stream, charset);
                reader = new BufferedReader(in);
                if(endRow > 0){
                    while(reader.ready() && count < endRow){
                        String line = reader.readLine();
                        count++;
                        if(count >= startRow){
                            doer.accept(line);
                        }
                    }
                }else{
                    while(reader.ready()){
                        String line = reader.readLine();
                        count++;
                        if(count >= startRow){
                            doer.accept(line);
                        }
                    }
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    in.close();
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Read a file if file is exists. <br>
     * Read all rows.
     * @param doer Do a line
     * @param charset The encoding
     */
    public static void read(InputStream stream, DoLiner doer, String charset){
        read(stream, doer, charset, 1, -1);
    }

    /**
     * Read a file if file is exists. <br>
     * Read all rows.
     * @param path The file path
     * @param doer Do a line
     * @param charset The encoding
     */
    public static void read(String path, DoLiner doer, String charset){
        read(path, doer, charset, 1, -1);
    }

    /**
     * Read a file as default utf-8 encoding
     * @param path The file path
     * @param doer Do a line
     */
    public static void read(String path, DoLiner doer){
        read(path, doer, "utf-8", 1, -1);
    }

    /**
     * Read a file as default utf-8 encoding
     * @param doer Do a line
     */
    public static void read(InputStream stream, DoLiner doer){
        read(stream, doer, "utf-8", 1, -1);
    }

    /**
     * Read a file as default utf-8 encoding
     * @param path The file path
     * @param doer Do a line
     * @param startRow Read from the row start
     * @param endRow  Read to the row end
     */
    public static void read(String path, DoLiner doer, int startRow, int endRow){
        read(path, doer, "utf-8", startRow, endRow);
    }

    /**
     * Read some file
     * @param paths The file paths
     * @param doer Do a line
     * @param charset The encoding style
     */
    public static void read(String[] paths, DoLiner doer, String charset){
        if(null != paths){
            int len = paths.length;
            for(int i = 0; i < len; i++){
                read(paths[i], doer, charset);
            }
        }
    }

    /**
     * Read some file as default utf-8 encoding
     * @param paths The file paths
     * @param doer Do a line
     */
    public static void read(String[] paths, DoLiner doer){
        read(paths, doer, "utf-8");
    }
}
