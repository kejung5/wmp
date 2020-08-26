package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonAutoDetect
public class ResultResponse {

    private int share;

    private int rest;

}
