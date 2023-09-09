package daangn.daangnspring.controller;

import daangn.daangnspring.domain.Post;
import daangn.daangnspring.domain.User;
import daangn.daangnspring.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DaangnController {

    private final PostService postService;
    private HttpSession session;

    @Autowired
    public DaangnController(PostService postService, HttpSession session) {
        this.postService = postService;
        this.session = session;
    }

    @GetMapping("/login")
    public String login() {
        String userId = "dkanro5";

        User user = postService.login(userId);
        String nickname = user.getNickname();
        Integer regionId = user.getResidenceId();
        String[] residence = user.getResidence();

        session.setAttribute("regionId", regionId);
        session.setAttribute("userId", userId);
        session.setAttribute("residence", residence);

        return "redirect:/";
    }

    // 홈 페이지
    @GetMapping("/")
    public String home(Model model) {
        String userid = (String) session.getAttribute("userId");
        model.addAttribute("login", userid != null ? "logout" : "login");

        String[] residence = (String[]) session.getAttribute("residence");
        model.addAttribute("residence", residence != null ? residence[2] : null);

        List<Post> posts = postService.listing((Integer) session.getAttribute("regionId"));
        model.addAttribute("posts", posts);

        return "home";
    }

    // 검색 페이지
    @PostMapping("/search")
    public String search(SearchForm searchForm, Model model) {
        model.addAttribute("searchForm", searchForm);
        String[] residence = (String[]) session.getAttribute("residence");
        model.addAttribute("residence", residence != null ? residence[2] : null);

        Integer regionId = (Integer) session.getAttribute("regionId");
        List<Post> posts = postService.search(searchForm, regionId);
        model.addAttribute("posts", posts);

        return "search";
    }

    // 글 자세히 보기
    @GetMapping("/post")
    public String post(@RequestParam("id") Long postId, Model model) {
        System.out.println(postId);

        Post post = postService.viewPost(postId);
        model.addAttribute("post", post);

        String wishlistStatus = "on";
        model.addAttribute("wishlistStatus", wishlistStatus);

        return "post";
    }

    // 글 작성 페이지
    @GetMapping("/posting")
    public String createPostingForm(Model model) {
        String[] residence = (String[]) session.getAttribute("residence");
        model.addAttribute("residence", residence != null ? residence[2] : null);

        return "posting";
    }

    // 글 작성
    @PostMapping("/posting")
    public String createPost(PostingForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setPrice(postForm.getPrice());
        post.setContent(postForm.getContent());
        post.setUser(new User(
                (String) session.getAttribute("userId")
        ));

        postService.posting(post);

        return "redirect:/";
    }
}
