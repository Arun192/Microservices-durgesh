package com.arun.user.service.services.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {


    private String id;
    private String name;
    private String location;
    private String about;
}
