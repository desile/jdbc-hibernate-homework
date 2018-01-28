package ru.hh.school.jdbc.example;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CurrencyDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public CurrencyDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public void insert(Currency currency) {
        if (currency.id() != null) {
            throw new IllegalArgumentException("can not insert " + currency + " with already assigned id");
        }

        String query = "INSERT INTO currency (full_name, short_name, update_timestamp, rate_to_dollar)" +
                "VALUES (:full_name, :short_name, :update_timestamp, :rate_to_dollar)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name", currency.getFullName());
        params.addValue("short_name", currency.getShortName());
        params.addValue("update_timestamp", new Date());
        params.addValue("rate_to_dollar", currency.getRateToDollar());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query, params, keyHolder, new String[]{"id"});

        currency.setId(keyHolder.getKey().intValue());
    }

    public Optional<Currency> get(int currencyId) {
        String query = "SELECT id, short_name, full_name, update_timestamp, rate_to_dollar FROM currency WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", currencyId);

        Currency currency;
        try {
            currency = namedParameterJdbcTemplate.queryForObject(query, params, rowToCurrency);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
        return Optional.of(currency);
    }

    public Set<Currency> getAll() {
        String query = "SELECT user_id, first_name, last_name FROM users";
        return new HashSet<>(jdbcTemplate.query(query, rowToCurrency));
    }

    public void update(Currency currency) {

        if (currency.id() == null) {
            throw new IllegalArgumentException("can not update " + currency + " without id");
        }

        String query = "UPDATE currency SET full_name = :full_name, short_name = :short_name, " +
                "update_timestamp = :update_timestamp, rate_to_dollar = :rate_to_dollar WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name", currency.getFullName());
        params.addValue("short_name", currency.getShortName());
        params.addValue("update_timestamp", new Date());
        params.addValue("rate_to_dollar", currency.getRateToDollar());
        params.addValue("id", currency.id());

        namedParameterJdbcTemplate.update(query, params);
    }

    public void delete(int currencyId) {

        String query = "DELETE FROM currency WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", currencyId);

        namedParameterJdbcTemplate.update(query, params);
    }

    private static final RowMapper<Currency> rowToCurrency = (resultSet, rowNum) ->
            Currency.existing(
                    resultSet.getInt("user_id"),
                    resultSet.getString("full_name"),
                    resultSet.getString("short_name"),
                    resultSet.getDate("update_timestamp"),
                    resultSet.getFloat("rate_to_dollar")
            );

}
