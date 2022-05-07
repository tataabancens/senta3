package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.RawImage;
import ar.edu.itba.paw.persistance.ImageDao;
import ar.edu.itba.paw.service.ControllerService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private ImageService ims;
    private ControllerService controllerService;

    @Autowired
    public ImageController(final ImageService ims, final ControllerService controllerService) {
        this.ims = ims;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/resources_/images/{imageId}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImageAsByteArray(@PathVariable("imageId") final String imageIdP) throws Exception {

        controllerService.longParser(imageIdP).orElseThrow(() -> new LongParseException(imageIdP));
        long imageId = Long.parseLong(imageIdP);

        RawImage image = ims.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getBytes();
    }
}
