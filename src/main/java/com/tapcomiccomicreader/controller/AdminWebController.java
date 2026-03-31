package com.tapcomiccomicreader.controller;

import com.tapcomiccomicreader.dto.CreateComicRequest;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    private final ComicService comicService;
    private final ChapterService chapterService;
    private final GenreService genreService;
    private final UserService userService;
    private final ReportedCommentService reportService;

    public AdminWebController(ComicService comicService, ChapterService chapterService,
                              GenreService genreService, UserService userService,
                              ReportedCommentService reportService) {
        this.comicService = comicService;
        this.chapterService = chapterService;
        this.genreService = genreService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("comicCount", comicService.findAll().size());
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("reportCount", reportService.findAll().size());
        return "admin/dashboard";
    }

    @GetMapping("/comics")
    public String listComics(Model model) {
        model.addAttribute("comics", comicService.findAll());
        return "admin/comics";
    }

    @GetMapping("/comics/new")
    public String newComicForm(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "admin/comic_form";
    }

    @PostMapping("/comics/new")
    public String createComic(@ModelAttribute CreateComicRequest request,
                              @RequestParam("coverFile") MultipartFile coverFile) throws IOException {
        Comic newComic = new Comic(request.getName(), request.getDescription(), request.getAuthor(), request.getArtist());
        comicService.save(newComic);

        if (!coverFile.isEmpty()) {
            comicService.uploadCover(newComic.getId(), coverFile);
        }
        return "redirect:/admin/comics";
    }

    @GetMapping("/comics/{id}/chapters")
    public String manageChapters(@PathVariable int id, Model model) {
        var comic = comicService.find(id);
        model.addAttribute("comic", comic);
        model.addAttribute("chapters", chapterService.findAll(id));
        return "admin/chapters";
    }

    @PostMapping("/comics/{id}/chapters/upload")
    public String uploadChapter(@PathVariable int id,
                                @RequestParam("files") List<MultipartFile> files) throws IOException {
        chapterService.uploadChapterInSequence(id, files);
        return "redirect:/admin/comics/" + id + "/chapters";
    }

    @PostMapping("/comics/chapters/{chapterId}/delete")
    public String deleteChapter(@PathVariable int chapterId, @RequestParam int comicId) throws IOException {
        chapterService.remove(chapterId);
        return "redirect:/admin/comics/" + comicId + "/chapters";
    }

    @GetMapping("/genres")
    public String listGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "admin/genres";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/reports")
    public String listReports(Model model) {
        model.addAttribute("reports", reportService.findAll());
        return "admin/reports";
    }

    @PostMapping("/reports/{id}/resolve")
    public String resolveReport(@PathVariable Integer id) {
        reportService.approve(id);
        return "redirect:/admin/reports";
    }

    @PostMapping("/reports/{id}/reject")
    public String rejectReport(@PathVariable Integer id) {
        reportService.reject(id);
        return "redirect:/admin/reports";
    }

    @PostMapping("/genres/new")
    public String createGenre(@RequestParam String name) {
        genreService.save(name);
        return "redirect:/admin/genres";
    }

    @PostMapping("/genres/{id}/delete")
    public String deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
        return "redirect:/admin/genres";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/comics/{id}/edit")
    public String editComicForm(@PathVariable int id, Model model) {
        var comic = comicService.find(id);
        model.addAttribute("comic", comic);
        model.addAttribute("allGenres", genreService.findAll());

        var currentGenres = comicService.getGenresByComicUuid(comic.getUuid());
        Set<Integer> comicGenreIds = currentGenres.stream()
                .map(genre -> {
                    try {
                        return (Integer) genre.getClass().getMethod("getId").invoke(genre);
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .collect(Collectors.toSet());

        model.addAttribute("comicGenreIds", comicGenreIds);

        return "admin/comic_edit";
    }

    @PostMapping("/comics/{id}/edit")
    public String updateComicDetails(@PathVariable int id,
                                     @ModelAttribute UpdateComicRequest request,
                                     @RequestParam(value = "coverFile", required = false) MultipartFile coverFile) throws IOException {

        comicService.updateComic(id, request);

        if (coverFile != null && !coverFile.isEmpty()) {
            comicService.uploadCover(id, coverFile);
        }

        return "redirect:/admin/comics/" + id + "/edit?success=details";
    }

    @PostMapping("/comics/{id}/genres")
    public String updateComicGenres(@PathVariable int id,
                                    @RequestParam(value = "genreIds", required = false) Set<Integer> genreIds) {
        if (genreIds == null) {
            genreIds = Collections.emptySet();
        }
        comicService.setComicGenres(id, genreIds);

        return "redirect:/admin/comics/" + id + "/edit?success=genres";
    }
}