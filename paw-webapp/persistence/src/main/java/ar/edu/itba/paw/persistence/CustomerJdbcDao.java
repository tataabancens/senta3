package ar.edu.itba.paw.persistence;

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

@Repository
public class CustomerJdbcDao implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Customer> ROW_MAPPER = ((resultSet, i) ->
            new Customer(resultSet.getLong("customerId"),
                    resultSet.getString("customerName"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Mail")));

    @Autowired
    public CustomerJdbcDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("customer")
                .usingGeneratedKeyColumns("customerId");
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

        int customerId = jdbcInsert.execute(customerData);
        return new Customer(customerId, customerName, phone, mail);
    }
}
