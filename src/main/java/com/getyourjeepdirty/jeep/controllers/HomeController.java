package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private EventDao eventDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        /*ArrayList<Event> eventList = new ArrayList<Event>();
        for (Event e : eventDao.findAll()){
            eventList.add(e);
        }*/
        model.addAttribute("eventList", sortedByDate());
        return "index";
    }

    public ArrayList<Event> sortedByDate() {
        ArrayList<Event> sortedList = new ArrayList<>();
        boolean first = true;
        for (Event e : eventDao.findAll()){
                sortedList.add(e);
        }
        for (int j=0; j<sortedList.size(); j++){
            for(int i=j+1; i<sortedList.size(); i++){
                if((sortedList.get(i).getDateTime().compareTo(sortedList.get(j).getDateTime()) < 0)){
                    Event temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(i));
                    sortedList.set(i, temp);
                }
            }
        }
        return sortedList;
    }
}
