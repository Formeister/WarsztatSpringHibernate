package com.warsztat.springhibernate.controller;

import java.util.List;
import java.util.Locale;
 
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
 
import com.warsztat.springhibernate.model.User;
import com.warsztat.springhibernate.model.Action;
import com.warsztat.springhibernate.service.ActionService;
import com.warsztat.springhibernate.service.UserService;
 
 
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {
 
    @Autowired
    UserService userService;
     
    @Autowired
    ActionService actionService;
     
     
    @Autowired
    MessageSource messageSource;
 
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
 
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }
 
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }
 
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        if(!userService.isUserPeselUnique(user.getId(), user.getPesel())){
            FieldError peselError =new FieldError("user","pesel",messageSource.getMessage("non.unique.pesel", new String[]{user.getPesel()}, Locale.getDefault()));
            result.addError(peselError);
            return "registration";
        }
         
        userService.saveUser(user);
 
        model.addAttribute("success", "Klient " + user.getFirstName() + " "+ user.getLastName() + " został zarejestrowany pomyślnie.");
        //return "success";
        return "registrationsuccess";
    }

    @RequestMapping(value = { "/edit-user-{pesel}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String pesel, ModelMap model) {
        User user = userService.findByPesel(pesel);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }
     
    @RequestMapping(value = { "/edit-user-{pesel}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
            ModelMap model, @PathVariable String pesel) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        
        if(!userService.isUserPeselUnique(user.getId(), user.getPesel())){
            FieldError peselError =new FieldError("user","pesel",messageSource.getMessage("non.unique.pesel", new String[]{user.getPesel()}, Locale.getDefault()));
            result.addError(peselError);
            return "registration";
        }
 
 
        userService.updateUser(user);
 
        model.addAttribute("success", "Klient " + user.getFirstName() + " "+ user.getLastName() + " został zaktualizowany pomyślnie.");
        return "registrationsuccess";
    }
 
    @RequestMapping(value = { "/delete-user-{pesel}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String pesel) {
        userService.deleteUserByPesel(pesel);
        return "redirect:/list";
    }
     
    @ModelAttribute("roles")
    public List<Action> initializeActions() {
        return actionService.findAll();
    }
 
}