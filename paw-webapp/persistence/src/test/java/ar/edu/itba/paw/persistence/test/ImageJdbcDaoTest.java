package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistence.CustomerJdbcDao;
import ar.edu.itba.paw.persistence.ImageJdbcDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class ImageJdbcDaoTest {

    private static final String IMAGE_TABLE = "image";

    private ImageJdbcDao imageDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertImage;

    @Autowired
    private DataSource ds;


    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

    @Before
    public void setUp(){
        imageDao = new ImageJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertImage = new SimpleJdbcInsert(ds)
                .withTableName(IMAGE_TABLE)
                .usingGeneratedKeyColumns("imageId");
    }

    public Number insertImage(MultipartFile bitmap){
        final Map<String, Object> imageData = new HashMap<>();
        imageData.put("bitmap", bitmap);

        return jdbcInsertImage.executeAndReturnKey(imageData);
    }

    public void cleanAllTables(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, IMAGE_TABLE);
    }

    @Test
    @Rollback
    public void testGetImageById_Exists(){
        // 1. Precondiciones

        // 2. Ejercitacion


        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testGetImageById_NotExists(){
        // 1. Precondiciones


        // 2. Ejercitacion


        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testCreateImage(){
        // 1. Precondiciones


        // 2. Ejercitacion


        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testDeleteImageById(){
        // 1. Precondiciones


        // 2. Ejercitacion


        // 3. PostCondiciones

    }


}
