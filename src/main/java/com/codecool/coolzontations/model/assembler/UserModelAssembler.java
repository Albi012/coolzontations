package com.codecool.coolzontations.model.assembler;

import com.codecool.coolzontations.controller.RouteController;
import com.codecool.coolzontations.controller.dto.response.ResponseUserModel;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserModel, EntityModel<ResponseUserModel>> {

    @Autowired
    private Util util;

    public EntityModel<ResponseUserModel> toModel(UserModel entity) {
        ResponseUserModel user = util.setUserProperties(entity);
        return new EntityModel<ResponseUserModel>(user,
                linkTo(methodOn(RouteController.class).findUserByName(entity.getUsername())).withSelfRel()
        );
    }
}
