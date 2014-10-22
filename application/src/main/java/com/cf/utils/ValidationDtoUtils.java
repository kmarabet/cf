package com.cf.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.cf.controller.dto.BaseTimeRegistrationDto;
import com.cf.controller.dto.GenericDto;
import com.cf.core.exeption.TaControllerException;
import com.cf.domain.BaseTimeRegistration;
import com.cf.domain.core.GenericEntity;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ValidationDtoUtils {

    public static void logAndThrowControllerException(final String message, final Logger logger, final Throwable ex){
        if (ex == null){
            logger.error(message);
            throw new TaControllerException(HttpServletResponse.SC_BAD_REQUEST, message);
        } else {
            logger.error(message + ex.getMessage());
            throw new TaControllerException(ex, HttpServletResponse.SC_BAD_REQUEST, message);
        }
    }

    public static <E extends BaseTimeRegistration,D extends BaseTimeRegistrationDto> E validateDtoAndGetEntity(final D dto, final Logger logger){

        E entity = null;
        try {
            entity = (E)dto.getEntityFromDto();

        } catch (ParseException e) {
            logAndThrowControllerException("Unable to create/update any planning", logger, e);
        }
        if(!entity.isTimeInOutValid()){
            logAndThrowControllerException("Not valid times in/out provided", logger, null);
        }
        //todo add validation for other fields if required
        return entity;
    }

    public static <D extends GenericDto, E extends GenericEntity> List<E> getEntityListFromJsonArray(final JsonArray jsonArray, final Class<D> genericDtoType, final Logger logger) {
        List<E> entityList = new ArrayList<>();
        Gson myGson = new Gson();
        for (final JsonElement userElement : jsonArray) {
            D dto = myGson.fromJson(userElement, genericDtoType);
            try {
                entityList.add((E) dto.getEntityFromDto());
            } catch (ParseException e) {
                logAndThrowControllerException("Not valid dates/times provided", logger, e);
            }
        }
        return entityList;
    }

    public static <D extends GenericDto, E extends GenericEntity> List<E> getEntityListFromJsonString(final String jsonArrayStr, final Type dtoListType, final Gson gson, final Logger logger) {

        List<D> dtoList = gson.fromJson(jsonArrayStr, dtoListType);

        return getEntityListFromDtoList(dtoList, logger);
    }

    public static <E extends GenericEntity, D extends GenericDto> List<D> getDtoListFromEntityList(final Iterable<E> entityList, final Class<D> dtoClass, final Logger logger) {

        List<D> resultedDtoList = new ArrayList<>();

        for (final E entity: entityList){
            //resultedList.add(new GenericDto(entity));
            D dto = null;
            try {
                dto = dtoClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e){
                logAndThrowControllerException("Translation of entity list to dto list failed due to: ", logger, e);
            }
            dto.setFieldsFromEntity(entity);
            resultedDtoList.add(dto);
        }
        return resultedDtoList;
    }

    public static <D extends GenericDto, E extends GenericEntity>  List<E> getEntityListFromDtoList(final List<D> dtoList, final Logger logger) {

        List<E> resultedEntityList = new ArrayList<>();

        for (final D dto : dtoList){
            try{
                E entity = (E)dto.getEntityFromDto();
                resultedEntityList.add(entity);
            } catch (ParseException e) {
                logAndThrowControllerException("Not valid dates/times provided", logger, e);
            }
        }
        return resultedEntityList;
    }

}
