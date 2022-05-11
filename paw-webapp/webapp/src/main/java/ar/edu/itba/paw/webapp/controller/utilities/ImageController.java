package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.RawImage;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    private final ImageService ims;

    @Autowired
    public ImageController(final ImageService ims) {
        this.ims = ims;
    }

    @RequestMapping(value = "/resources_/images/{imageId}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImageAsByteArray(@PathVariable("imageId") final String imageIdP) throws Exception {

        ControllerUtils.longParser(imageIdP).orElseThrow(() -> new LongParseException(imageIdP));
        long imageId = Long.parseLong(imageIdP);

        RawImage image = ims.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getBytes();
    }
}
