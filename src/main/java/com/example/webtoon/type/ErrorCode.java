package com.example.webtoon.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LOGIN_FAIL_EMAIL_NOT_EXIST("가입된 이메일 주소가 없습니다."),
    LOGIN_FAIL_PASSWORD_WRONG("비밀번호가 일치하지 않습니다."),
    ALREADY_EXISTED_EMAIL("중복된 이메일 주소입니다."),
    ALREADY_EXISTED_NICKNAME("중복된 닉네임입니다."),
    ALREADY_EXISTED_WEBTOON_TITLE("중복된 웹툰 제목입니다."),
    ALREADY_EXISTED_WEBTOON_THUMBNAIL_FILENAME("이미 웹툰 썸네일이 등록되어있습니다."),
    WEBTOON_TITLE_NOT_FOUND("입력한 웹툰 제목이 존재하지 않습니다."),
    NICKNAME_NOT_FOUND("검색한 닉네임이 존재하지 않습니다."),
    AUTHENTICATION_NOT_MATCHED("인증정보가 일치하지 않습니다."),
    UNAUTHORIZED_USER("사용자에게 권한이 없습니다."),
    FILE_STORAGE_FAILED("파일 업로드를 하지 못했습니다.");

    private final String message;
}
