package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.CommentLikeRepository;
import com.tapcomiccomicreader.dao.CommentRepository;
import com.tapcomiccomicreader.dao.ReplyCommentRepository;
import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.dto.*;
import com.tapcomiccomicreader.entity.*;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import com.tapcomiccomicreader.helperclass.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyRepository;
    private final ReplyCommentService replyService;
    private final UserService userService;
    private final ComicService comicService;
    private final ChapterService chapterService;
    private final PageService pageService;
    private final ReplyLikeService replyLikeService;
    private final CommentLikeService commentLikeService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ReplyCommentRepository replyRepository, ReplyCommentService replyService, UserService userService, UserRepository userRepository, ComicService comicService, ChapterService chapterService, PageService pageService, CommentLikeRepository commentLikeRepository, ReplyLikeService replyLikeService, CommentLikeService commentLikeService) {
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
        this.replyService = replyService;
        this.userService = userService;
        this.comicService = comicService;
        this.chapterService = chapterService;
        this.pageService = pageService;
        this.replyLikeService = replyLikeService;
        this.commentLikeService = commentLikeService;
    }

    @Override
    public Comment find(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find comment with id - " + id));
    }

    @Override
    public List<CommentDTO> findAll() {
        return List.of();
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        User user = userService.findByUuid(commentRequest.getUserUuid());
        String text = commentRequest.getText();

        Comment comment = new Comment(user, text);

        if (commentRequest.isPageComment()) {
            Page page = pageService.find(commentRequest.getPageId());
            comment.setPage(page);
        } else if (commentRequest.isChapterComment()) {
            Chapter chapter = chapterService.find(commentRequest.getChapterId());
            comment.setChapter(chapter);
        } else {
            Comic comic = comicService.find(commentRequest.getComicId());
            comment.setComic(comic);
        }

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findByComicId(int comicId) {
        var comments = commentRepository.findByComicId(comicId);
        return handleConvertToDTO(comments);
    }

    @Override
    public List<CommentDTO> findByChapterId(int chapterId) {
        var comments = commentRepository.findByChapterId(chapterId);
        return handleConvertToDTO(comments);
    }

    @Override
    public List<CommentDTO> findByPageId(int pageId) {
        var comments = commentRepository.findByPageId(pageId);
        return handleConvertToDTO(comments);
    }

    @Override
    @Transactional
    public void reply(ReplyRequest replyRequest) {
        User user = userService.findByUuid(replyRequest.getUuid());
        Comment mainComment = find(replyRequest.getCommentId());

        var reply = new ReplyComment();
        reply.setUser(user);
        reply.setText(replyRequest.getText());
        reply.setMainComment(mainComment);

        replyService.save(reply);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    private List<CommentDTO> handleConvertToDTO(List<Comment> comments) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();

        if (comments.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> commentIds = comments.stream().map(Comment::getId).toList();
        List<ReplyComment> replies = replyRepository.findByParentIds(commentIds);
        List<Integer> replyIds = replies.stream().map(ReplyComment::getId).toList();

        Map<Integer, Boolean> commentVotesMap = Collections.emptyMap();
        Map<Integer, Boolean> replyVotesMap = Collections.emptyMap();

        if (currentUserId != null) {
            commentVotesMap = commentLikeService.getUserVotes(currentUserId, commentIds);
            replyVotesMap = replyLikeService.getUserVotes(currentUserId, replyIds);
        }

        final var finalReplyVotesMap = replyVotesMap;
        Map<Integer,List<ReplyCommentDTO>> repliesMap = replies.stream()
                .collect(Collectors.groupingBy(ReplyComment::getMainCommentId,
                        Collectors.mapping(
                                reply -> new ReplyCommentDTO(
                                        reply,
                                        finalReplyVotesMap.get(reply.getId()))
                                ,Collectors.toList()
                        )
                ));

        final var commentsMap = commentVotesMap;
        return comments.stream().map(
                comment -> new CommentDTO(
                        comment,
                        commentsMap.get(comment.getId()),
                        repliesMap.get(comment.getId()))).toList();
    }
}

