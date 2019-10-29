package com.demo.oauth2.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.form.AppUserForm;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomUserSerialize extends StdSerializer<AppUserForm> {

    public CustomUserSerialize() {
        this(null);
    }

    public CustomUserSerialize(Class<AppUserForm> t) {
        super(t);
    }

    @Override
    public void serialize(AppUserForm appUserForm, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeNumberField("userId", appUserForm.getUserId());
        generator.writeStringField("email", appUserForm.getEmail());
        generator.writeStringField("userName", appUserForm.getUserName());
        generator.writeBooleanField("enable", appUserForm.isEnable());
        generator.writeStringField("firstName", appUserForm.getFirstName());
        generator.writeStringField("lastName", appUserForm.getLastName());
        generator.writeStringField("role", appUserForm.getRole());
        generator.writeStringField("update", appUserForm.getUpdate().toString());
        generator.writeStringField("create", appUserForm.getCreate().toString());
        generator.writeEndObject();
    }

}
