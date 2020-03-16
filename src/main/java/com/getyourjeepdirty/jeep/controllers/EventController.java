package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.User;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import com.getyourjeepdirty.jeep.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO: Signed-in user not being stored as "Creator" of new event

@Controller
@RequestMapping(value = "event")
public class EventController {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "event/index";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newEvent (Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        int id = (int)session.getAttribute("id");
        User user = userDao.findById(id).get();     //DELETE THIS???
        model.addAttribute("user", user);
        model.addAttribute("event", new Event());
        return "event/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newEvent (Model model, @Valid @ModelAttribute Event event, String userId) throws ParseException {
        User user = userDao.findById(Integer.parseInt(userId)).get();
        event.setUser(user);
        Date date  = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(event.getDateTime());

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM d, yyyy");
        event.setFormattedDate(sdf.format(date));
        eventDao.save(event);

        return "redirect:..";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String displayEvent (Model model, @PathVariable("id") int id){
        Event event = eventDao.findById(id).get();
        model.addAttribute("event", event);
        User user = event.getUser();
        String userName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("creatorName", userName);
        return "event/view";
    }

}