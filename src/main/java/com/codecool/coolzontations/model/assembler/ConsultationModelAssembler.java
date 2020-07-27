package com.codecool.coolzontations.model.assembler;

import com.codecool.coolzontations.controller.RouteController;
import com.codecool.coolzontations.controller.dto.response.ResponseConsultationModel;
import com.codecool.coolzontations.controller.dto.response.ResponseUserModel;
import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ConsultationModelAssembler implements RepresentationModelAssembler<Consultation, EntityModel<ResponseConsultationModel>> {

    @Autowired
    private Util util;

    @Override
    public EntityModel<ResponseConsultationModel> toModel(Consultation entity) {
        ResponseConsultationModel consultationModel = util.setConsultationProperties(entity);
        return new EntityModel<ResponseConsultationModel>(consultationModel,
                linkTo(methodOn(RouteController.class).singleConsultation(entity.getId())).withSelfRel(),
                linkTo(methodOn(RouteController.class).allConsultations()).withRel("consultations")
                );
    }

    public CollectionModel<EntityModel<ResponseConsultationModel>> toCollectionModel(List<Consultation> entities) {
        List<EntityModel<ResponseConsultationModel>> entityModels = entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<EntityModel<ResponseConsultationModel>>(entityModels, linkTo(methodOn(RouteController.class).allConsultations()).withSelfRel());
    }
}
