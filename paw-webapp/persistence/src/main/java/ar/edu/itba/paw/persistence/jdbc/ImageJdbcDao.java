package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.RawImage;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageJdbcDao implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<RawImage> ROW_MAPPER = ((resultSet, i) ->
            new RawImage(resultSet.getLong("imageId"),
                        resultSet.getBytes("bitmap")));

    @Autowired
    public ImageJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("image")
                .usingGeneratedKeyColumns("imageid");
    }

    @Override
    public Optional<RawImage> getImageById(long id) {
        List<RawImage> query = jdbcTemplate.query("SELECT * FROM image WHERE imageId = ?",
                new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Image create(MultipartFile photo) throws IOException {
        final Map<String, Object> imageData = new HashMap<>();
        byte barr[] = photo.getBytes();

        imageData.put("bitmap", barr);

        Number imageId = jdbcInsert.executeAndReturnKey(imageData);
        return new Image(imageId.longValue(), photo);
    }

    @Override
    public void deleteImageById(long imageId) {
        jdbcTemplate.update("DELETE FROM image WHERE imageId = ?", new Object[]{imageId});

    }
}
