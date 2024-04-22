package com.pdfgenerator.cv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    private String position;
    private String company;
    private String duration;
    private String description;
}
