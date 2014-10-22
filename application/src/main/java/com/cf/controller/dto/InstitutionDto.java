package com.cf.controller.dto;

import com.cf.domain.Institution;

public class InstitutionDto extends GenericDto {
    private Institution institution;

    public InstitutionDto(Institution institution) {
        super(institution);
        this.institution = institution;
    }

}
