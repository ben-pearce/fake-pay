package com.pay.fakepay.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("capitalizeConverter")
public class CapitalizeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsString(context, component, value);
    }

    @Override
    public String getAsString(
            FacesContext context, 
            UIComponent component,
            Object value) {
        if (value == null || ((String) value).isEmpty()) {
            return null;
        }

        String string = (String) value;
        return new StringBuilder()
            .append(Character.toTitleCase(string.charAt(0)))
            .append(string.substring(1))
            .toString();
    }
}