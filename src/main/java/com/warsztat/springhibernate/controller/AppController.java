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
 
    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
 
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }
 
    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }
 
    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
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
 
 
    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{pesel}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String pesel, ModelMap model) {
        User user = userService.findByPesel(pesel);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }
     
    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
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
 
     
    /**
     * This method will delete an user by it's Pesel value.
     */
    @RequestMapping(value = { "/delete-user-{pesel}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String pesel) {
        userService.deleteUserByPesel(pesel);
        return "redirect:/list";
    }
     
 
    /**
     * This method will provide Actions list to views
     */
    @ModelAttribute("roles")
    public List<Action> initializeActions() {
        return actionService.findAll();
    }
 
}