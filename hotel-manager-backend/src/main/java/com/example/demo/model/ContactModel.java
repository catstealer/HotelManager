package com.example.demo.model;

import com.example.demo.entity.Contact;
import com.example.demo.validators.PersonExists;
import com.example.demo.validators.PhoneNumber;
import lombok.Data;

@Data
public class ContactModel {

    @PhoneNumber
    public String phoneNumber;

    @PersonExists
    public Long personId;

}
