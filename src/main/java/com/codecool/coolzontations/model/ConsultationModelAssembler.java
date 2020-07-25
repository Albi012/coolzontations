package com.codecool.coolzontations.model;

import com.codecool.coolzontations.controller.RouteController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ConsultationModelAssembler implements RepresentationModelAssembler<Consultation, EntityModel<Consultation>> {

    @Override
    public EntityModel<Consultation> toModel(Consultation entity) {
        return new EntityModel<Consultation>(entity,
                linkTo(methodOn(RouteController.class).singleConsultation(entity.getId())).withSelfRel(),
                linkTo(methodOn(RouteController.class).allConsultations()).withRel("consultations")
                );
    }
}
