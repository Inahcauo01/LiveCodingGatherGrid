package com.epshiro.controller;


import com.epshiro.entities.Event;
import com.epshiro.entities.User;
import com.epshiro.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@WebServlet(name = "events", value = {"/events", "/eventDelete"})

public class EventServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        if (action.equalsIgnoreCase("/eventDelete")){
            doDelete(req, resp);
        }else {
            List<Event> events = new EventService().getAllEvents();
            req.setAttribute("events", events);

            for (Event event: events) {
                User user = event.getUser();
                if (user != null){
                    event.setUser(user);
                }
            }

            req.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(req, resp);
        }


//        List<Category> categories = new CategoryService().getAllCategories();
//        req.setAttribute("categories", categories);

//        getServletContext().getRequestDispatcher("/dashboard/pages/events.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Integer max = Integer.valueOf(req.getParameter("max"));

        String dateString = req.getParameter("date");

        LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);

        User user = (User) req.getSession().getAttribute("user");
        Event event = new Event(name, max, date, user);

        EventService eventService = new EventService();
        Event event1 = eventService.createEvent(event);

        List<Event> events = new EventService().getAllEvents();
        req.setAttribute("events", events);

        req.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long eventID = Long.valueOf(req.getParameter("id"));

        EventService eventService = new EventService();
        eventService.findEvent(eventID);
        Event e = eventService.deleteEvent(eventID);
        String s ;
        if (e != null){
            s = "sup succés";
        }else {
            s = "sup echoué";
        }
        req.setAttribute("msg", s);

        List<Event> events = new EventService().getAllEvents();
        req.setAttribute("events", events);
        req.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(req, resp);
//        resp.sendRedirect("events");
    }
}
