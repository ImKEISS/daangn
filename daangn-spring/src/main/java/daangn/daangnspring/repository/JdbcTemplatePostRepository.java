package daangn.daangnspring.repository;

import daangn.daangnspring.domain.Post;
import daangn.daangnspring.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JdbcTemplatePostRepository implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePostRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("posts").usingGeneratedKeyColumns("postid");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", post.getTitle());
        parameters.put("price", post.getPrice());
        parameters.put("posttime", post.getTime());
        parameters.put("content", post.getContent());
        parameters.put("deal_status", post.getDealStatus());
        parameters.put("userid", post.getUser().getId());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        post.setId(key.longValue());
        return post;
    }

    @Override
    public Optional<Post> findByPostId(Long postId) {
        List<Post> result = jdbcTemplate.query("SELECT Posts.*, Regions.province, Regions.district, Regions.town, Users.nickname, Users.manner_temperature" +
                                                    " FROM Posts" +
                                                    " JOIN Users ON Posts.userid = Users.userid" +
                                                    " JOIN Regions ON Users.residence = Regions.regionid" +
                                                    " WHERE postid = ?", onePostRowMapper(), postId);
        return result.stream().findAny();
    }

    @Override
    public List<Post> findBySearchRequirement(String title, Integer minPrice, Integer maxPrice, String dealStatus, Integer regionId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT Posts.*, Regions.province, Regions.district, Regions.town" +
                            " FROM Posts " +
                            " JOIN Users ON Posts.userid = Users.userid" +
                            " JOIN Regions ON Users.residence = Regions.regionid" +
                            " WHERE title LIKE ?");

        List<Object> parameters = new ArrayList<>();
        parameters.add("%" + title + "%");

        // minPrice가 null이면 조건을 추가하지 않음
        if (minPrice != null) {
            queryBuilder.append(" AND price >= ?");
            parameters.add(minPrice);
        }
        // maxPrice가 null이면 조건을 추가하지 않음
        if (maxPrice != null) {
            queryBuilder.append(" AND price <= ?");
            parameters.add(maxPrice);
        }
        // dealStatus가 공백이면 조건을 추가하지 않음
        if (dealStatus != null && !dealStatus.isEmpty()) {
            queryBuilder.append(" AND deal_status = ?");
            parameters.add(dealStatus);
        }
        //regionId가 공백이면 조건을 추가하지 않음
        if (regionId != null) {
            queryBuilder.append(" AND Posts.userid IN (SELECT userid FROM Users WHERE residence = ?)");
            parameters.add(regionId);
        }

        queryBuilder.append(" ORDER BY posttime DESC");
        String query = queryBuilder.toString();
        List<Post> posts = jdbcTemplate.query(query, postsRowMapper(), parameters.toArray());

        return posts;
    }

    @Override
    public List<Post> findByRegionId(Integer regionId) {
        StringBuilder queryBuilder = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        queryBuilder.append("SELECT Posts.*, Regions.province, Regions.district, Regions.town" +
                            " FROM Posts " +
                            " JOIN Users ON Posts.userid = Users.userid" +
                            " JOIN Regions ON Users.residence = Regions.regionid");

        //regionId가 공백이면 조건을 추가하지 않음
        if (regionId != null) {
            queryBuilder.append(" WHERE Posts.userid IN (SELECT userid FROM Users WHERE residence = ?)");
            parameters.add(regionId);
        }

        queryBuilder.append(" ORDER BY posttime DESC");
        String query = queryBuilder.toString();
        List<Post> posts= jdbcTemplate.query(query, postsRowMapper(), parameters.toArray());

        return posts;
    }

    public Optional<User> findByUserId(String userId){
        String query = "SELECT Users.*, Regions.province, Regions.district, Regions.town" +
                        " FROM Users" +
                        " JOIN Regions ON Users.residence = Regions.regionid" +
                        " WHERE userid=?";
        List<User> result= jdbcTemplate.query(query, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getString("userid"),
                        rs.getString("nickname"),
                        rs.getFloat("manner_temperature"),
                        rs.getInt("residence"),
                        new String[] {
                                rs.getString("province"),
                                rs.getString("district"),
                                rs.getString("town")
                        }
                );
                return user;
            }
        }, userId);
        return result.stream().findAny();
    }

    private RowMapper<Post> postsRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("postid"));
            post.setTitle(rs.getString("title"));
            post.setPrice(rs.getInt("price"));
            post.setTime(getDuration(rs));
            post.setDealStatus(rs.getString("deal_status"));
            post.setUser(new User(
                    new String[] {
                            rs.getString("province"),
                            rs.getString("district"),
                            rs.getString("town")
                    }
            ));
            return post;
        };
    }

    private RowMapper<Post> onePostRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("postid"));
            post.setTitle(rs.getString("title"));
            post.setPrice(rs.getInt("price"));
            post.setTime(getDuration(rs));
            post.setContent(rs.getString("content"));
            post.setDealStatus(rs.getString("deal_status"));
            post.setUser(new User(
                    rs.getString("userid"),
                    rs.getString("nickname"),
                    rs.getFloat("manner_temperature"),
                    new String[] {
                            rs.getString("province"),
                            rs.getString("district"),
                            rs.getString("town")
                    }
            ));
            return post;
        };
    }

    private static String getDuration(ResultSet rs) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = rs.getString("posttime");
        LocalDateTime posttime = LocalDateTime.parse(time, formatter);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(posttime, now);
        if (duration.toSeconds() < 60)  time = duration.toSeconds() + "초 전";
        else if (duration.toMinutes() < 60) time = duration.toMinutes() + "분 전";
        else if (duration.toHours() < 24) time = duration.toHours() + "시간 전";
        else if (duration.toDays() < 31) time = duration.toDays() + "일 전";
        else if (duration.toDays() / 30 < 12) time =  (int) duration.toDays() / 30 + "달 전";
        else time = (int) duration.toDays() / 365 + "년 전";
        return time;
    }
}
