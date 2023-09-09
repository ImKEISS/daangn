package daangn.daangnspring.service;

import daangn.daangnspring.controller.PostingForm;
import daangn.daangnspring.controller.SearchForm;
import daangn.daangnspring.domain.Post;
import daangn.daangnspring.domain.User;
import daangn.daangnspring.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 홈 화면 글 나열
    public List<Post> listing(Integer regionId){
        return postRepository.findByRegionId(regionId);
    }

    // 게시글 검색
    public List<Post> search(SearchForm searchForm, Integer regionId) {
        return postRepository.findBySearchRequirement(searchForm.getTitle(), searchForm.getMinPrice(), searchForm.getMaxPrice(), searchForm.getDealStatus(), regionId);
    }

    // 게시글 작성
    public Long posting(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    // 게시글 작성
    public Post viewPost(Long postId) {
        Optional<Post> post = postRepository.findByPostId(postId);
        if (post.isEmpty()) return new Post();
        return post.get();
    }

    // 로그인
    public User login(String userId){
        Optional<User> user = postRepository.findByUserId(userId);
        if (user.isEmpty()) return null;
        return user.get();
    }
}
