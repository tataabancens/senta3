package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.service.DishService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.webapp.dto.ImageDto;
import ar.edu.itba.paw.model.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.model.exceptions.LongParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

@Path("/resources/images")
@Component
public class ImageController {

    @Autowired
    private ImageService ims;
    @Autowired
    private DishService ds;

    @GET
    @Path("/{imageId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getImageAsByteArray(@PathParam("imageId") final String imageIdP){

        ControllerUtils.longParser(imageIdP).orElseThrow(() -> new LongParseException(imageIdP));
        long imageId = Long.parseLong(imageIdP);

        Image image = ims.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        ImageDto imageDto = ImageDto.fromImage(image);
        return Response.ok(imageDto.getBitmap()).build();
    }



    @PUT
    @Path("/{dishId}")
    public Response editImage(@PathParam("dishId") final String dishId, CommonsMultipartFile photo) throws IOException {

        Optional<Dish> dish = ds.getDishById(Long.parseLong(dishId));
        if(!dish.isPresent()){
            return Response.noContent().build();
        }
        ds.updateDishPhoto(Long.parseLong(dishId), photo);
        Optional<Image> image = ims.getImageById(dish.get().getImageId());
        if(!image.isPresent()){
            return Response.noContent().build();
        }
        ImageDto imageDto = ImageDto.fromImage(image.get());
        return Response.ok(imageDto).build();
    }
}
