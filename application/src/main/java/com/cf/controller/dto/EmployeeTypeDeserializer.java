package com.cf.controller.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;


public class EmployeeTypeDeserializer implements JsonDeserializer<EmployeeTypeDto> {

    @Override
    public EmployeeTypeDto deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        final EmployeeTypeDto employeeTypeDto = new EmployeeTypeDto();

        final JsonElement jsonId = jsonObject.get("id");
        final Long id = jsonId != null ? jsonId.getAsLong():null;
        employeeTypeDto.setId(id);

        final JsonElement jsonName = jsonObject.get("name");
        final String name = jsonName.getAsString();
        employeeTypeDto.setName(name);

        final int weeklyWorkHours = jsonObject.get("weeklyWorkHours") != null ? jsonObject.get("weeklyWorkHours").getAsInt() : 0;
        employeeTypeDto.setWeeklyWorkHours(weeklyWorkHours);

        JsonElement jsonWorkExceptionsElement = jsonObject.get("workExceptions");
        if (jsonWorkExceptionsElement != null){
            final JsonArray jsonWorkExceptionArray = jsonWorkExceptionsElement.getAsJsonArray();

            final WorkExceptionDto[] workExceptions = new WorkExceptionDto[jsonWorkExceptionArray.size()];
            for (int i = 0; i < workExceptions.length; i++) {
                final JsonElement jsonWorkException = jsonWorkExceptionArray.get(i);
                jsonObject = jsonWorkException.getAsJsonObject();

                WorkExceptionDto dto = new WorkExceptionDto();
                dto.setId(jsonObject.get("id") != null? jsonObject.get("id").getAsLong(): null);
                dto.setName(jsonObject.get("name").getAsString());
                dto.setStartTime(jsonObject.get("startDate").getAsString());
                dto.setEndTime(jsonObject.get("endDate").getAsString());
                workExceptions[i] = dto;
            }
            employeeTypeDto.setWorkExceptions(workExceptions);
        }
        return employeeTypeDto;
    }

}
