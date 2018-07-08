package com.example.demo.hello;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@RestController
public class ChatController {

    private final IMap<String, String> map = Hazelcast.newHazelcastInstance().getMap("my-map");

    @RequestMapping("/greeting") // maps /greeting to greeting() method
    public ModelAndView greetings(ModelMap modelMap) {
        String msg = "message";
        modelMap.addAttribute("msg",msg);
        return new ModelAndView("usernameForm");
    }

    @RequestMapping("/setUsername")
    public ModelAndView chatWindow(@RequestParam String username, ModelMap modelMap) {
        modelMap.addAttribute("username",username);
        return new ModelAndView("chatWindow");
    }

    @RequestMapping("/showMsg")
    public ModelAndView showMsg(ModelMap modelMap) {
        int counter = 1;
        for(String username : map.keySet()){
            modelMap.addAttribute("msg"+counter,map.get(username));
            counter ++;
        }
        return new ModelAndView("showMsg");
    }

//    private class CallBack implements
//            EntryAddedListener<String, String>,
//            EntryUpdatedListener<String, String> {
//
//        @Override
//        public void entryAdded(EntryEvent<String, String> entryEvent) {
//            System.out.println(entryEvent.getKey() + " " + entryEvent.getValue());
//        }
//
//        @Override
//        public void entryUpdated(EntryEvent<String, String> entryEvent) {
//            System.out.println(entryEvent.getKey() + " " + entryEvent.getValue());
//        }
//    }

}
