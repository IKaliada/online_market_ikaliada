package com.gmail.iikaliada.onlinemarket.springbootmodule.handler;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class PaginationHandler {

    public void getPagination(@RequestParam(name = "page", defaultValue = "1")
                                      Integer currentPage, Model model, int totalPage) {
        model.addAttribute("totalPage", totalPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < totalPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[totalPage];
        for (int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
    }
}
