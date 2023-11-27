package com.org.spemajorbackend.dro;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AmountRequest {
    private String id;
    private String messId;
    private Date startDate;
    private Date endDate;
}
