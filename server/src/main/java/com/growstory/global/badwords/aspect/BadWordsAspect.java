package com.growstory.global.badwords.aspect;

import com.growstory.global.badwords.dto.ProfanityDto;
import com.growstory.global.badwords.dto.TextContainer;
import com.growstory.global.badwords.service.BlackListService;
import com.growstory.global.exception.BusinessLogicException;
import com.growstory.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class BadWordsAspect {

    private final BlackListService badWordsService;

    @Pointcut("execution(public * com.growstory..journal.controller.*.postJournal(..)) || " +
                "execution(public * com.growstory..journal.controller.*.patchJournal(..)) || " +
                "execution(public * com.growstory..board.controller.*.postBoard(..)) || " +
                "execution(public * com.growstory..board.controller.*.patchBoard(..)) || " +
                "execution(public * com.growstory..comment.controller.*.postComment(..)) || " +
                "execution(public * com.growstory..comment.controller.*.patchComment(..)) ")
    public void beforeWritten() {
    }

    @Before("beforeWritten()")
    public void badWordsFiltering(JoinPoint joinPoint) throws Throwable {
        log.info("# 욕설 필터링 동작");
        Object[] args = joinPoint.getArgs();

        String content = "";
        for(Object container : args) {
            if(container instanceof TextContainer) {
                TextContainer dto = (TextContainer) container;
                content = dto.combineText();
                break;
            }
        }

        ProfanityDto profanityDto = badWordsService.getProfanityWords(content);
        Set<String> profanityWords = profanityDto.getInputProfanityWords();
        //욕설이 포함되어 있다면
        if(!profanityWords.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BAD_WORD_INCLUDED, profanityDto);
        }
    }

}
