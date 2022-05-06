package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> ROW_MAPPER = (resultSet, i) ->
            new User(resultSet.getLong("userId"),
            resultSet.getString("username"),
            resultSet.getString("password"),
                    resultSet.getString("role"));
    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Users")
                .usingGeneratedKeyColumns("userid");
    }

    @Override
    public Optional<User> getUserById(long id) {
        List<User> query = jdbcTemplate.query("SELECT * FROM users WHERE userId = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public User create(String username, String password, Roles role) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        userData.put("role", role.getDescription());

        Number userId = jdbcInsert.executeAndReturnKey(userData);
        return new User(userId.longValue(), username, password, Roles.ANONYMOUS.getDescription());
    }

    @Override
    public Optional<User> findByName(final String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{username}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", new Object[]{newPassword, username});
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) {
        jdbcTemplate.update("UPDATE users SET username = ? WHERE username = ?", new Object[]{newUsername, oldUsername});
    }
}
