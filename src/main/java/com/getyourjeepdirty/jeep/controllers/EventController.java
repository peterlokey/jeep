package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.User;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import com.getyourjeepdirty.jeep.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        System.out.println("ID: " + id + " " + user.getFirstName());
        model.addAttribute("user", user);
        model.addAttribute("event", new Event());
        return "event/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newEvent (Model model, @Valid @ModelAttribute Event event, String userId) {
        User user = userDao.findById(Integer.parseInt(userId)).get();
        event.setUser(user);
        eventDao.save(event);

        return "redirect:..";
    }

}