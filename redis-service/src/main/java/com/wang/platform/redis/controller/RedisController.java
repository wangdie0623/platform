package com.wang.platform.redis.controller;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.redis.model.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
public class RedisController {
    @Autowired
    private IRedisService redisService;

    @PostMapping("list/right/minutes/{timeOut}")
    public ResultInfo pushListRM(String key, String value, @PathVariable Long timeOut) {
        redisService.rPushList(key, value, timeOut);
        return ResultInfo.ok("保存成功");
    }

    @PostMapping("list/left/minutes/{timeOut}")
    public ResultInfo pushListLM(String key, String value, @PathVariable Long timeOut) {
        redisService.lPushList(key, value, timeOut);
        return ResultInfo.ok("保存成功");
    }

    @PutMapping("obj/minutes/{timeOut}")
    public ResultInfo pushObjM(String key, String value, @PathVariable Long timeOut) {
        redisService.pushObj(key, value, timeOut);
        return ResultInfo.ok("保存成功");
    }

    @GetMapping("list/top/{key}")
    public ResultInfo listTop(@PathVariable String key) {
        return ResultInfo.ok("消费首条数据", null, redisService.getListTop(key));
    }

    @GetMapping("list/tail/{key}")
    public ResultInfo listTail(@PathVariable String key) {
        return ResultInfo.okStr("消费尾条数据", redisService.getListTail(key));
    }

    @GetMapping("full")
    public ResultInfo fullVal(String key) {
        return ResultInfo.okStr(String.format("查询%s值", key), redisService.getFullValue(key));
    }

    @DeleteMapping("keys")
    public ResultInfo del(String keyVal) {
        redisService.del(keyVal);
        return ResultInfo.ok("删除成功");
    }

    @DeleteMapping("keys/obj")
    public ResultInfo delObj(String keyVal) {
        redisService.delObj(keyVal);
        return ResultInfo.ok("删除成功");
    }

    @DeleteMapping("keys/list")
    public ResultInfo delList(String keyVal) {
        redisService.delList(keyVal);
        return ResultInfo.ok("删除成功");
    }

    @GetMapping("keys")
    public ResultInfo keys(String queryStr) {
        return ResultInfo.ok("查询成功", redisService.keys(queryStr));
    }
}
