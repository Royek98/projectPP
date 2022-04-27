package com.rojek.projectpp.Pages.Details;

import com.rojek.projectpp.Pages.Details.Models.Address;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class AddressFormatter implements Formatter<Address> {
    @Override
    public Address parse(String text, Locale locale) throws ParseException {
        String [] txt = text.split(";");
        if(txt.length==4){
            String zipcode=txt[0];
            String city=txt[1];
            String street = txt[2];
            String nr = txt[3];
            return new Address(zipcode, city, street, nr);
        }
        throw new ParseException("Address error",1);
    }

    @Override
    public String print(Address object, Locale locale) {
        if(object==null || object.getZipCode()==null && object.getCity()==null ||
            object.getStreet() == null || object.getNumber() == null){
            return "";
        }
        return String.format("%s;%s;%s;%s", object.getZipCode(), object.getCity(), object.getStreet(), object.getNumber());
    }
}
