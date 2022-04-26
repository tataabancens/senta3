package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.RawImage;
import ar.edu.itba.paw.persistance.ImageDao;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private ImageService ims;

    @Autowired
    public ImageController(final ImageService ims) {
        this.ims = ims;
    }

    @RequestMapping(value = "/resources/images/{imageId}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImageAsByteArray(@PathVariable final String imageId) throws IOException {
        RawImage image = ims.getImageById(Long.parseLong(imageId)).orElseThrow(ImageNotFoundException::new);
        return image.getBytes();
    }
}
