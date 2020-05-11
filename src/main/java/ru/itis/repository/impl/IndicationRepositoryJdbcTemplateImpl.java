package ru.itis.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.model.Indication;
import ru.itis.repository.IndicationRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


@Component
public class IndicationRepositoryJdbcTemplateImpl implements IndicationRepository {
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from indication where id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from indication";
    //language=SQL
    private static final String SQL_INSERT = "insert into indication(token, tempin, tempout,  humidityin, humidityout, humiditybot, pressure, date) values (?, ?, ?, ?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private RowMapper<Indication> indicationRowMapper = (row, rowNumber) ->
            Indication.builder()
                    .id(row.getInt("id"))
                    .date(row.getDate("date"))
                    .humidityBot(row.getDouble("humiditybot"))
                    .humidityIn(row.getDouble("humidityin"))
                    .humidityOut(row.getDouble("humidityout"))
                    .pressure(row.getDouble("pressure"))
                    .tempIn(row.getDouble("tempin"))
                    .tempOut(row.getDouble("tempout"))
                    .build();

    @Override
    public Optional<Indication> find(Integer id) {
        try {
            Indication ind = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, indicationRowMapper);
            return Optional.ofNullable(ind);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Indication> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, indicationRowMapper);
    }

    @Override
    public void save(Indication entity) {

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getToken());
            statement.setDouble(2, entity.getTempIn());
            statement.setDouble(3, entity.getTempOut());
            statement.setDouble(4, entity.getHumidityIn());
            statement.setDouble(5, entity.getHumidityOut());
            statement.setDouble(6, entity.getHumidityBot());
            statement.setDouble(7, entity.getPressure());
            statement.setDate(8, entity.getDate());
            return statement;
        });
    }

    public void delete(Integer id) {

    }

    @Override
    public void update(Indication entity) {
    }

}
