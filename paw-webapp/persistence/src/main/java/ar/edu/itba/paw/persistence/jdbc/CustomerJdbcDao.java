package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.persistance.CustomerDao;
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

//@Repository
public class CustomerJdbcDao implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Customer> ROW_MAPPER = ((resultSet, i) ->
            new Customer(resultSet.getLong("customerId"),
                    resultSet.getString("customerName"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Mail"),
                    resultSet.getInt("points")));

    @Autowired
    public CustomerJdbcDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("customer")
                .usingGeneratedKeyColumns("customerid");
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        List<Customer> query = jdbcTemplate.query("SELECT * FROM customer WHERE customerId = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Customer create(String customerName, String phone, String mail) {
        final Map<String, Object> customerData = new HashMap<>();
        customerData.put("customerName", customerName);
        customerData.put("Phone", phone);
        customerData.put("Mail", mail);
        customerData.put("points", 0);

        Number customerId = jdbcInsert.executeAndReturnKey(customerData);
        return new Customer(customerId.longValue(), customerName, phone, mail, 0);
    }

    @Override
    public Customer create(String customerName, String phone, String mail, long userId) {
        final Map<String, Object> customerData = new HashMap<>();
        customerData.put("customerName", customerName);
        customerData.put("Phone", phone);
        customerData.put("Mail", mail);
        customerData.put("userId", userId);
        customerData.put("points", 0);

        Number customerId = jdbcInsert.executeAndReturnKey(customerData);
        return new Customer(customerId.longValue(), customerName, phone, mail, (int) userId);
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        List<Customer> query = jdbcTemplate.query("SELECT * FROM customer NATURAL JOIN users WHERE username = ?",
                new Object[]{username}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public void addPointsToCustomer(long customerId, int points) {
        jdbcTemplate.update("UPDATE customer SET points = points + ? WHERE customerId = ?", new Object[]{points, customerId});
    }

    @Override
    public void linkCustomerToUserId(long customerId, long userId) {
        jdbcTemplate.update("UPDATE customer SET userId = ? WHERE customerId = ?", new Object[]{userId, customerId});
    }

    @Override
    public void updateCustomerData(long customerId, String name, String phone, String mail) {
        jdbcTemplate.update("UPDATE customer SET customername = ?," +
                                                    " phone = ?," +
                                                    " mail = ? WHERE customerId = ?",
                            new Object[]{name, phone, mail, customerId});
    }
}
