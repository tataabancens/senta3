package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageJdbcDao implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ImageJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("image")
                .usingGeneratedKeyColumns("imageid");
    }

    @Override
    public Optional<Image> getImageById(long id) {
        return Optional.empty();
    }

    @Override
    public Image create(MultipartFile photo) throws IOException {
        final Map<String, Object> imageData = new HashMap<>();
        byte barr[] = photo.getBytes();

        imageData.put("bitmap", barr);

        Number imageId = jdbcInsert.executeAndReturnKey(imageData);
        return new Image(imageId.longValue(), photo);

    }
}
