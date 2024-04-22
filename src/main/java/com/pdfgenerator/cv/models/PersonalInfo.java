package com.pdfgenerator.cv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class PersonalInfo {
    private String name;
    private String email;
    private String phone;

}