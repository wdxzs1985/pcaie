package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.ProductBean;
import jp.pcaie.domain.StockBean;
import jp.pcaie.exception.PageNotFoundException;
import jp.pcaie.service.StockService;
import jp.pcaie.support.Paginate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    private StockService stockService = null;
    @Autowired
    private final MessageSource messageSource = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) final String name,
                        @RequestParam(required = false, defaultValue = "1") final int page,
                        final Model model) {
        final Paginate<StockBean> paginate = new Paginate<StockBean>();
        paginate.setPage(page);

        final Map<String, Object> params = paginate.getParams();
        params.put("name", name);

        this.stockService.doSearch(paginate);

        model.addAttribute("paginate", paginate);
        return "stock/index";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String doGetCreate(final Model model) {
        final StockBean stockBean = new StockBean();
        model.addAttribute("stockBean", stockBean);
        return "stock/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String doPostCreate(@ModelAttribute final StockBean inputStockBean,
                               final Model model,
                               final Locale locale,
                               final RedirectAttributes redirectAttributes) {
        if (this.stockService.validate(inputStockBean, model, locale)) {
            this.stockService.save(inputStockBean);
            return "redirect:/stock";
        }
        model.addAttribute("stockBean", inputStockBean);
        return "stock/create";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        final StockBean stockBean = this.stockService.getStockById(id);
        if (stockBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("stockBean", stockBean);
        return "stock/edit";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id,
                             @ModelAttribute final StockBean inputStockBean,
                             final Model model,
                             final Locale locale,
                             final RedirectAttributes redirectAttributes) {
        final StockBean stockBean = this.stockService.getStockById(id);
        if (stockBean == null) {
            throw new PageNotFoundException();
        }
        final ProductBean productBean = stockBean.getProductBean();
        final ProductBean inputProductBean = inputStockBean.getProductBean();
        productBean.setName(inputProductBean.getName());
        productBean.setContent(inputProductBean.getContent());
        productBean.setPrice(inputProductBean.getPrice());
        //
        stockBean.setStock(inputStockBean.getStock());
        stockBean.setSafeStock(inputStockBean.getSafeStock());
        stockBean.setNotificationEmail(inputStockBean.getNotificationEmail());

        if (this.stockService.validate(stockBean, model, locale)) {
            this.stockService.update(stockBean);
            final String message = this.messageSource.getMessage("admin.stock.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/stock/" + id;
        }
        model.addAttribute("stockBean", stockBean);
        return "stock/edit";
    }
}
