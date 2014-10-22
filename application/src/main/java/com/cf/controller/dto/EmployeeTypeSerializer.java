package com.cf.controller.dto;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EmployeeTypeSerializer implements JsonSerializer<EmployeeTypeDto> {

    @Override
    public JsonElement serialize(final EmployeeTypeDto employeeTypeDto, final Type typeOfSrc, final JsonSerializationContext context) {

        final JsonObject jsonEmployeeTypeObject = new JsonObject();
        jsonEmployeeTypeObject.addProperty("id", employeeTypeDto.getId());
        jsonEmployeeTypeObject.addProperty("name", employeeTypeDto.getName());
        jsonEmployeeTypeObject.addProperty("weeklyWorkHours", employeeTypeDto.getWeeklyWorkHours());

        final JsonArray jsonWorkExceptionsArray = new JsonArray();
        if (employeeTypeDto.getWorkExceptions() != null){
            for (final WorkExceptionDto workExceptionDto: employeeTypeDto.getWorkExceptions()) {
                final JsonObject jsonWorkException = new JsonObject();
                jsonWorkException.addProperty("id", workExceptionDto.getId());
                jsonWorkException.addProperty("name", workExceptionDto.getName());
                jsonWorkException.addProperty("startDate", workExceptionDto.getStartTime());
                jsonWorkException.addProperty("endDate", workExceptionDto.getEndTime());
                jsonWorkExceptionsArray.add(jsonWorkException);
            }
            jsonEmployeeTypeObject.add("workExceptions", jsonWorkExceptionsArray);
        }

        return jsonEmployeeTypeObject;

    }
}
