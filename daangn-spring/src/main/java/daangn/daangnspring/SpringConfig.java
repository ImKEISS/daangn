package daangn.daangnspring;

import daangn.daangnspring.repository.JdbcTemplatePostRepository;
import daangn.daangnspring.repository.PostRepository;
import daangn.daangnspring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new JdbcTemplatePostRepository(dataSource);
    }
}
