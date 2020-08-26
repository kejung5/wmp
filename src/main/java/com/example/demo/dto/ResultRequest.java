package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonAutoDetect
public class ResultRequest {

    private String url;

    private String type;

    private int unit;

}
