package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.Comment;
import com.getyourjeepdirty.jeep.models.Event;
import com.getyourjeepdirty.jeep.models.User;
import com.getyourjeepdirty.jeep.models.data.CommentDao;
import com.getyourjeepdirty.jeep.models.data.EventDao;
import com.getyourjeepdirty.jeep.models.data.UserDao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//TODO: On Add New Event form: handle Date input to ensure a future date is selected

@Controller
@RequestMapping(value = "event")
public class EventController {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "event/index";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newEvent (Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        int id = (int)session.getAttribute("id");
        User user = userDao.findById(id).get();     //DELETE THIS???
        model.addAttribute("creator", user);
        model.addAttribute("event", new Event());
        System.out.println("GET request handled.");
        return "event/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newEvent (Model model, @Valid @ModelAttribute Event event, String userId) throws ParseException {
        System.out.println(event + " " + userId);
        User user = userDao.findById(Integer.parseInt(userId)).get();
        event.setCreator(user);
        Date date  = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(event.getDateTime());

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM d, yyyy");
        event.setFormattedDate(sdf.format(date));
        System.out.println("Checkpoint 1. Attempting to save event.");
        eventDao.save(event);
        System.out.println("Checkpoint 2. Event saved, redirecting.");

        return "redirect:..";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String displayEvent (Model model, @PathVariable("id") int eventId, HttpServletRequest request){
        Event event = eventDao.findById(eventId).get();
        model.addAttribute("event", event);
        User creator = event.getCreator();
        String creatorName = creator.getFirstName() + " " + creator.getLastName();
        model.addAttribute("creatorName", creatorName);

        //determine if the active user has joined this event//
        boolean userIsAttending = false;
        HttpSession session=request.getSession(false);
        if(session!=null) {
            int userId = (int) session.getAttribute("id");
            User user = userDao.findById(userId).get();
            userIsAttending = user.getAttendingEvents().contains(event);
            model.addAttribute("isAttending", userIsAttending);
        }
        if(!userIsAttending){model.addAttribute("isAttending", 0);}

        model.addAttribute("attendees", event.getAttendees());
        model.addAttribute("commentList", event.getComments());

        return "event/view";
    }

    @RequestMapping(value = "{id}/join", method = RequestMethod.GET)
    public String joinEvent (Model model, @PathVariable("id") int eventId, HttpServletRequest request){
        HttpSession session=request.getSession(false);
        int userId = (int)session.getAttribute("id");
        User user = userDao.findById(userId).get();

        Event event = eventDao.findById(eventId).orElse(null);

        user.addAttendingEvent(event);
        userDao.save(user);

        return "redirect:/event/"+eventId;
    }

    @RequestMapping(value = "{id}/leave", method = RequestMethod.GET)
    public String leaveEvent (Model model, @PathVariable("id") int eventId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("id");
        User user = userDao.findById(userId).get();

        Event event = eventDao.findById(eventId).orElse(null);

        user.removeAttendingEvent(event);
        userDao.save(user);
        model.addAttribute("event", event);

        return "redirect:/event/"+eventId;
    }

    @RequestMapping(value = "{id}/addComment", method = RequestMethod.GET)
    public String addComment (Model model, @PathVariable("id") int eventId, HttpServletRequest request){

        return "event/add_comment";
    }

    @RequestMapping(value = "{id}/addComment", method = RequestMethod.POST)
    public String saveComment (Model model, @PathVariable("id") int eventId, HttpServletRequest request,
                               @RequestParam String text){
        Comment comment = new Comment();
        //set Comment's Author
        HttpSession session = request.getSession(false);
        Integer userId = (int) session.getAttribute("id");
        comment.setAuthor(userDao.findById(userId).get());
        //set Comment's Text
        comment.setText(text);
        //set Comment's timestamp
        comment.setDateTime(new Timestamp(System.currentTimeMillis()));
        //set Comment's Parent Event
        Event event = eventDao.findById(eventId).get();
        comment.setEvent(event);

        commentDao.save(comment);

        return "redirect:/event/"+eventId;
    }
}