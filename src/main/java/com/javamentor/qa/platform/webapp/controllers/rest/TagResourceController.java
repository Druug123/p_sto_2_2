package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Rustam
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/tag")
@Api(value = "Работа с тэгами на вопросы", tags = {"Тэг и вопросы"})
public class TagResourceController {

    private final TagDtoService tagDtoService;
    private final UserService userService;


    @ApiOperation(value = "Получение списка из 10 тэгов с " +
            "наибольшим количеством вопросов с данным тэгом", tags = {"Получение списка тэгов"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение")})
    @GetMapping("/related")
    public ResponseEntity<List<RelatedTagsDto>> getRelatedTagDto() {
        return new ResponseEntity<>(tagDtoService.getRelatedTagsDto(), HttpStatus.OK);
    }

    @ApiOperation(value = "Getting all TrackedTagDto", tags = {"TrackedTagDto"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "TrackedTagDto not exist")})
    @GetMapping("/tracked")
    public ResponseEntity<List<TagDto>> getAllTrackedTagDto(Authentication authentication) {
        Long id = 2L; // todo убрать когда будет готово секьюрити
        return new ResponseEntity<>(tagDtoService
                .getTrackedTagById(userService.getById(id).get().getId()), HttpStatus.OK);
    }

    @ApiOperation(value = "Getting all IgnoredTagDto", tags = {"IgnoredTagDto"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "IgnoredTagDto not exist")})
    @GetMapping("/ignored")
    public ResponseEntity<List<TagDto>> getAllIgnoredTagDto(Authentication authentication) {
        Long id = 2L; // todo убрать когда будет готово секьюрити
        return new ResponseEntity<>(tagDtoService
                .getTrackedTagById(userService.getById(id).get().getId()), HttpStatus.OK);
    }
}
