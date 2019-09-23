package com.data.dataproducer;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author danny
 * @date 2019/9/5 5:02 PM
 */
public class TestKafka {



    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Map<String, Object> conf = new HashMap<>();
        conf.put("bootstrap.servers", "localhost:9092");
        AdminClient client = AdminClient.create(conf);

        List<NewTopic> newTopics = new ArrayList<>();



        client.createTopics(newTopics);

        ListTopicsResult topics = client.listTopics();
        Set set = topics.names().get();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

    }
}
