package com.warsztat.springhibernate.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
 
import com.warsztat.springhibernate.model.Action;
import com.warsztat.springhibernate.service.ActionService;
 
/**
 * A converter class used in views to map id's to actual Action objects.
 */
@Component
public class ActionConverter implements Converter<Object, Action>{
 
    @Autowired
    ActionService actionService;
 
    /**
     * Gets Action by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public Action convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        Action action= actionService.findById(id);
        System.out.println("Action : "+action);
        return action;
    }
     
}