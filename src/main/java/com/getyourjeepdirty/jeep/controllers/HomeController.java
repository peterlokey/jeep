package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

//TODO: Add Link to index.html to create new event

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

    @RequestMapping(value = "terms")
    public String terms() {
        return "terms";
    }

    public ArrayList<Event> sortedByDate() {
        ArrayList<Event> sortedList = new ArrayList<>();
        boolean first = true;
        for (Event e : eventDao.findAll()){
            //LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm");
            String currentDateString = dtf.format(LocalDateTime.now());

            if(e.getDateTime().compareTo(currentDateString) > 0){
                sortedList.add(e);
            }
/*

            System.out.println(e.getDateTime());
            System.out.println(currentDateString);
*/
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
