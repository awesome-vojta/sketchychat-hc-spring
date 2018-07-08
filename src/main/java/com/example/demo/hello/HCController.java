package com.example.demo.hello;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

import java.util.Scanner;

public class HCController {

    private final IMap<String, String> map =
            Hazelcast.newHazelcastInstance(null).getMap("my-map");
    private String username;
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        HCController controller = new HCController();
        controller.initialize();
        controller.send(" has joined the chat");
        controller.startChat();
    }


    public void initialize(){
        map.addEntryListener(new CallBack(),true);
        System.out.print("Username>>  ");
        username = sc.next();
    }

    public void send(String content) {
        map.put(username,content);
    }

    public void startChat() {
        while (true) {
            System.out.print("msg>> ");
            String input = sc.next();
            send(input);
        }
    }

    private class CallBack implements
            EntryAddedListener<String, String>,
            EntryUpdatedListener<String, String> {

        @Override
        public void entryAdded(EntryEvent<String, String> entryEvent) {
            System.out.println(entryEvent.getKey() + " " + entryEvent.getValue());
        }

        @Override
        public void entryUpdated(EntryEvent<String, String> entryEvent) {
            System.out.println(entryEvent.getKey() + " " + entryEvent.getValue());
        }
    }

}











