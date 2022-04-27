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
    public @ResponseBody byte[] getImageAsByteArray(@PathVariable("imageId") final String imageIdP) throws Exception {

        longParser(imageIdP);
        long imageId = Long.parseLong(imageIdP);

        RawImage image = ims.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getBytes();
    }

    private void longParser(Object... str) throws Exception {
        if(str.length > 0){
            try{
                Long str0 = Long.parseLong((String) str[0]);
            } catch (NumberFormatException e) {
                throw new Exception(str[0] + " is not a number");
            }
        }
        if(str.length > 1){
            try{
                Long str1 = Long.parseLong((String) str[1]);
            } catch (NumberFormatException e) {
                throw new Exception(str[1] + " is not a number");
            }
        }
        if(str.length > 2){
            try{
                Long str2 = Long.parseLong((String) str[2]);
            } catch (NumberFormatException e) {
                throw new Exception(str[2] + " is not a number");
            }
        }
    }
}
