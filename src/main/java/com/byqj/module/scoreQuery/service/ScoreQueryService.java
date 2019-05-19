package com.byqj.module.scoreQuery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreQueryService {


    @Autowired
    private ScoreQueryCheckService scoreQueryCheckService;
    @Autowired
    private ScoreQueryBusinessService scoreQueryBusinessService;

    /**
     * 查询成绩
     */
    public void scoreQueryRequestProcess() {
        scoreQueryCheckService.scoreQueryRequestCheck();
        scoreQueryBusinessService.scoreQueryRequestProcess();
    }
}
