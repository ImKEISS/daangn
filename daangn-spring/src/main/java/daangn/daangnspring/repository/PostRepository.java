package daangn.daangnspring.repository;

import daangn.daangnspring.domain.Post;
import daangn.daangnspring.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findByPostId(Long postId);
    List<Post> findBySearchRequirement(String title, Integer minPrice, Integer maxPrice, String dealStatus, Integer regionId);
    List<Post> findByRegionId(Integer regionId);
    Optional<User> findByUserId(String userId);
}
