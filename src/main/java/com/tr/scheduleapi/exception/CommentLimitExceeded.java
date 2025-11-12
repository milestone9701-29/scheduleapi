package com.tr.scheduleapi.exception;


// comment limit = 10
public class CommentLimitExceeded extends RuntimeException {
    public CommentLimitExceeded() { super("댓글은 최대 10개까지 등록할 수 있습니다."); }
}