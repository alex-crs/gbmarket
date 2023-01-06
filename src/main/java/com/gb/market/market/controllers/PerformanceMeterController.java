package com.gb.market.market.controllers;

import com.gb.market.market.services.PerformanceMeter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class PerformanceMeterController {
    private final PerformanceMeter performanceMeter;

    @GetMapping()
    public List<String> meter() {
        return performanceMeter.getTotalPerformanceTimeForAllServices();
    }
}
