package com.hsinpingweng.calendar.controller;

import java.util.List;

import javax.validation.Valid;

import com.hsinpingweng.calendar.model.data.Event;
import com.hsinpingweng.calendar.model.data.SharedUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsinpingweng.calendar.model.EventRepository;
import com.hsinpingweng.calendar.model.SharedUserRepository;
import com.hsinpingweng.calendar.model.UserRepository;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.EscapedState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/calendar")
public class CalendarHomeController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SharedUserRepository sharedUserRepo;

    @Autowired
    private UserRepository userRepo;


    @GetMapping
    public String index(Model model) throws JsonProcessingException {
        
        // retrieve userid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        // retrieve shared user
        List<SharedUser> shareToUsers = sharedUserRepo.findByhostUserId(userId);
        model.addAttribute("sharedUsers", shareToUsers);

        // retrieve events created by this user
        List<Event> events = eventRepo.findByhostUserId(userId);

        // retrieve shared user
        List<SharedUser> shareByUsers = sharedUserRepo.findByshareToUserId(userId);

        // retrieve events shared by other users
        for (SharedUser shareByUser : shareByUsers) {
            events.addAll(eventRepo.findByhostUserId(shareByUser.getHostUserId()));
        }

        ObjectMapper mapper = new ObjectMapper();
        String eventsJson = mapper.writeValueAsString(events);
        model.addAttribute("events", eventsJson);
        model.addAttribute("event", new Event());
        model.addAttribute("sharedUser", new SharedUser());

        return "calendar/index";
    }

    @PostMapping
    public String index(@Valid Event event, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Given data is not valid, please try again.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        
        } else if (!auth.getName().equals(event.getHostUserId())){
            redirectAttributes.addFlashAttribute("message", "You can not edit the event since you not the host user.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else {

            // retrieve events are booked by this user
            List<Event> events = eventRepo.findByhostUserIdAndIsBookingTrue(event.getHostUserId());
            
            boolean isEventExisted = false;

            for (Event e : events) { 

                if (e.getId() != event.getId()) {
                    if ((event.getStart().isBefore(e.getStart()) || event.getStart().isEqual(e.getStart()))   
                        && (event.getEnd().isAfter(e.getStart())) 
                    
                        || (event.getStart().isAfter(e.getStart()) || event.getStart().isEqual(e.getStart()))
                        && (event.getStart().isBefore(e.getEnd()))) {
                        
                        redirectAttributes.addFlashAttribute("message", "This time slot is not available. Please input a different time.");
                        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                        
                        return "redirect:/calendar";
                    }
                } else {
                    isEventExisted = true;
                }
            }

            String message = isEventExisted ? "Event edited" : "Event created";
            
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            eventRepo.save(event);
        }

        return "redirect:/calendar";
    }

    @PostMapping("/shareduser")
    public String shareduser(@Valid SharedUser sharedUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        boolean isSharedUserExisted = userRepo.findByUsername(sharedUser.getShareToUserId()) != null;

        if (isSharedUserExisted){
            sharedUserRepo.save(sharedUser);
            redirectAttributes.addFlashAttribute("message", "Calendar is shared with the user.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "User is Not existed.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        return "redirect:/calendar";
    }
    
    @GetMapping("/delete/event/{id}")
    public String deleteevent(@PathVariable int id, RedirectAttributes redirectAttributes){
        
        eventRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Event deleted!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/calendar";
    }

    @GetMapping("/delete/shareduser/{id}")
    public String deletesharedUser(@PathVariable int id, RedirectAttributes redirectAttributes){

        sharedUserRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Shared user deleted!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/calendar";
    }
}
