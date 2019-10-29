package com.demo.oauth2.form;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
    private List<String> searchRange = Arrays.asList("userId", "email", "userName");
    private String searchKey;

}
