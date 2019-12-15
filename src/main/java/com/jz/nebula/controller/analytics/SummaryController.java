package com.jz.nebula.controller.analytics;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.WishList;
import com.jz.nebula.entity.analytics.DashboardSummary;
import com.jz.nebula.service.WishListService;
import com.jz.nebula.service.analytics.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/analytics")
public class SummaryController {
    @Autowired
    private AnalyticsService analyticsService;

    /**
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    DashboardSummary getDashboardSummary() {
        return analyticsService.getDashboardSummary();
    }
}
