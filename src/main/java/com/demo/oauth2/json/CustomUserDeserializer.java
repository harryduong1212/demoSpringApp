package com.demo.oauth2.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.demo.oauth2.form.AppUserForm;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CustomUserDeserializer extends JsonDeserializer<AppUserForm> {


    @Override
    public AppUserForm deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        try {
            JsonNode jsonNode = jsonparser.getCodec().readTree(jsonparser);
            AppUserForm appUserForm = new AppUserForm();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            appUserForm.setUserId(jsonNode.get("userId").asLong());
            appUserForm.setUserName(jsonNode.get("userName").asText());
            appUserForm.setEmail(jsonNode.get("email").asText());
            appUserForm.setFirstName(jsonNode.get("firstName").asText());
            appUserForm.setLastName(jsonNode.get("lastName").asText());
            appUserForm.setRole(jsonNode.get("role").asText());
            appUserForm.setUpdate(format.parse(jsonNode.get("update").asText()));
            appUserForm.setCreate(format.parse(jsonNode.get("create").asText()));
            return appUserForm;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}