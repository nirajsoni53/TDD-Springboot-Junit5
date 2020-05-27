package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<User> findById(int userId) {
        String query = "SELECT id, name, description FROM USER1 WHERE id =:id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", userId);
        try {
            User user = jdbcTemplate
                    .query(query, params, new BeanPropertyRowMapper<>(User.class)).get(0);
            return Optional.ofNullable(user);
        } catch (DataAccessException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public User save(User user) {

        String query = "INSERT INTO USER1(name, description) VALUES(:name,:desc)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("desc", user.getDesc());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, params, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM USER1 WHERE id =:id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.update(query, params) > 0;
    }

    public List<User> findAll() {
        String query = "SELECT name, description AS desc1 FROM user1";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }
}
