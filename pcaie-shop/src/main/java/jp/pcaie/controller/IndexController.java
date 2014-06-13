package jp.pcaie.controller;

import java.util.List;

import jp.pcaie.domain.StockBean;
import jp.pcaie.exception.PageNotFoundException;
import jp.pcaie.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private StockService stockService = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(final Model model) {
        final List<StockBean> recommand = this.stockService.getRecommand();
        model.addAttribute("recommand", recommand);
        return "index";
    }

    @RequestMapping(value = "/p/{id:\\d+}", method = RequestMethod.GET)
    public String product(@PathVariable final Integer id, final Model model) {
        final StockBean stockBean = this.stockService.getStockById(id);
        if (stockBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("stockBean", stockBean);
        return "product";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(required = false) final String query,
                         final Model model) {
        model.addAttribute("query", query);
        return "search";
    }

}
