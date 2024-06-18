package ba.sum.fpmoz.event.controller;

import ba.sum.fpmoz.event.entity.Event;
import ba.sum.fpmoz.event.entity.User;
import ba.sum.fpmoz.event.service.EventService;
import ba.sum.fpmoz.event.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/events")
    public String showEvents(Model model, Principal principal) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        User currentUser = getCurrentUser(principal);
        model.addAttribute("currentUser", currentUser);
        return "events"; // This will load events.html from the templates directory
    }

    @GetMapping("/eventview")
    public String showEventView(Model model) {
        model.addAttribute("event", new Event());
        return "eventview"; // This will load eventview.html from the templates directory
    }

    @PostMapping("/events")
    public String createEvent(@ModelAttribute Event event, Principal principal) {
        User organizer = getCurrentUser(principal);
        event.setOrganizer(organizer);
        eventService.save(event);
        return "redirect:/events";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model, Principal principal) {
        Event event = eventService.findById(id);
        User currentUser = getCurrentUser(principal);
        if (!event.getOrganizer().equals(currentUser)) {
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        return "editevent"; // This will load editevent.html from the templates directory
    }

    @PostMapping("/events/update/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute Event event, Principal principal) {
        Event existingEvent = eventService.findById(id);
        User currentUser = getCurrentUser(principal);
        if (!existingEvent.getOrganizer().equals(currentUser)) {
            return "redirect:/events";
        }
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setStartDatetime(event.getStartDatetime());
        existingEvent.setEndDatetime(event.getEndDatetime());
        existingEvent.setImageUrl(event.getImageUrl());
        eventService.save(existingEvent);
        return "redirect:/events";
    }

    @GetMapping("/events/delete/{id}")
    public String showDeleteEventForm(@PathVariable Long id, Model model, Principal principal) {
        Event event = eventService.findById(id);
        User currentUser = getCurrentUser(principal);
        if (!event.getOrganizer().equals(currentUser)) {
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        return "deleteevent"; // This will load deleteevent.html from the templates directory
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id, Principal principal) {
        Event event = eventService.findById(id);
        User currentUser = getCurrentUser(principal);
        if (!event.getOrganizer().equals(currentUser)) {
            return "redirect:/events";
        }
        eventService.deleteById(id);
        return "redirect:/events";
    }

    private User getCurrentUser(Principal principal) {
        return userService.findByEmail(principal.getName());
    }
}
