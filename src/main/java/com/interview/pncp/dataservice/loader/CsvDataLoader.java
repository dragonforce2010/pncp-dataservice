package com.interview.pncp.dataservice.loader;

import com.interview.pncp.dataservice.constants.CommonConstants;
import com.interview.pncp.dataservice.pojo.RestResult;
import com.interview.pncp.dataservice.service.ESService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class CsvDataLoader {

    private ESService esService = new ESService();
    private String index = CommonConstants.CSV_LOADER_CONFIG_PARAM_INDEX;
    private String type = CommonConstants.CSV_LOADER_CONFIG_PARAM_TYPE;
    private String csvFilePath;
    private ExecutorService executorService;
    private String idAttributeName = CommonConstants.CSV_LOADER_CONFIG_PARAM_ID_COLUMN_NAME;
    private int batchSize = 500;
    private int totalTaskCount;
    private int executedTaskCount;

    private Map<Integer, String> headerMap;

    public CsvDataLoader() {
        this.headerMap = new HashMap<>();
        this.csvFilePath = CommonConstants.CSV_LOADER_CONFIG_PARAM_FILE_PATH;
//        this.executorService = Executors.newCachedThreadPool();
        this.executorService = Executors.newFixedThreadPool(200);
    }

    public RestResult loadData2ES() {
        try{
            File inputF = new File(CsvDataLoader.class.getResource(this.csvFilePath).getPath());
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            this.initCSVHeader(br.readLine());
            process(br);

            return RestResult.newInstance(200, new HashMap<String, Integer>(){{
                this.put("Total Processed record", CsvDataLoader.this.totalTaskCount);
            }}, "Data loaded successfully", null);

        } catch (IOException e) {
            e.printStackTrace();
            return  RestResult.newInstance(500, null, null, e.getMessage());
        }
    }

    private void process(BufferedReader br) {
        List<Map<String, String>> itemList = new ArrayList<>();
        br.lines().forEach(line -> {
            String[] p = line.split(",");
            Map<String, String> item = new HashMap<>();

            for(int i = 0; i < p.length; i ++) {
                item.put(this.headerMap.get(i), p[i]);
            }

            itemList.add(item);
        });

        this.totalTaskCount = itemList.size();
        for(int i = 0; i < itemList.size(); i += batchSize) {
            CsvLoadTask task = new CsvLoadTask(
                    this.index,
                    this.type,
                    this.idAttributeName,
                    this.esService,
                    itemList.subList(i, i + batchSize > itemList.size() ? itemList.size() : i + batchSize)
            );
            this.executorService.execute(task);
        }
        try {
            br.close();
            synchronized (CsvDataLoader.class) {
                CsvDataLoader.class.wait(20*60*1000);
            }
            this.executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initCSVHeader(String line) {
        String[] header = line.split(",");
        for(int i = 0; i < header.length; i++) {
            this.headerMap.put(i, header[i]);
        }
    }

    private class CsvLoadTask implements Runnable {
        private List<Map<String, String>> itemList;
        private String index;
        private String type;
        private String idAttributeName;
        private ESService esService;

        public CsvLoadTask(String index, String type, String idAttributeName, ESService esService, List<Map<String, String>> itemList) {
            this.itemList = itemList;
            this.index = index;
            this.type = type;
            this.idAttributeName = idAttributeName;
            this.esService = esService;
        }

        @Override
        public void run() {
            if(this.itemList == null || this.itemList.size() == 0) {
                return;
            }

            for(Map<String, String> item : itemList) {
                this.esService.index(this.index, this.type, item.get(this.idAttributeName), item);
                CsvDataLoader.this.executedTaskCount += 1;
                System.out.println("Thread - " + Thread.currentThread().getId() + ": Processed record - " + item.get(this.idAttributeName));

                if(CsvDataLoader.this.executedTaskCount == CsvDataLoader.this.totalTaskCount) {
                    synchronized (CsvDataLoader.class) {
                        CsvDataLoader.class.notifyAll();
                    }
                }
                System.out.println("Total task count:" + CsvDataLoader.this.totalTaskCount);
                System.out.println("Executed task count:" + CsvDataLoader.this.executedTaskCount);
            }
        }

        public List<Map<String, String>> getItemList() {
            return itemList;
        }

        public void setItemList(List<Map<String, String>> itemList) {
            this.itemList = itemList;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIdAttributeName() {
            return idAttributeName;
        }

        public void setIdAttributeName(String idAttributeName) {
            this.idAttributeName = idAttributeName;
        }

        public ESService getEsService() {
            return esService;
        }

        public void setEsService(ESService esService) {
            this.esService = esService;
        }
    }

}


