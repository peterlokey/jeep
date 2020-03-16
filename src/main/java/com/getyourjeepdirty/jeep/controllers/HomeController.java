package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private EventDao eventDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        ArrayList<Event> eventList = new ArrayList<Event>();
        for (Event e : eventDao.findAll()){
            eventList.add(e);
        }
        model.addAttribute("eventList", eventList);
        return "index";
    }
}
