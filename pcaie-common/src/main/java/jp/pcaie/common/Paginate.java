package jp.pcaie.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paginate<T> {

    private int itemCount;

    private int page;

    private int pageSize;

    private int start;

    private int end;

    private int pageCount;

    private List<T> items;

    private Map<String, Object> params = new HashMap<String, Object>();

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(final int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void compute() {
        // init
        this.itemCount = Math.max(this.itemCount, 0);
        this.page = Math.max(this.page, 1);
        this.pageSize = Math.max(this.pageSize, 10);
        this.pageCount = 0;
        this.start = 0;
        this.end = 0;

        // pageCount
        final int mod = this.itemCount % this.pageSize;
        this.pageCount = (this.itemCount - mod) / this.pageSize + 1;
        this.page = Math.min(this.page, this.pageCount);

        // pageCount
        this.start = (this.page - 1) * this.pageSize;
        this.end = Math.min(this.page * this.pageSize - 1, this.itemCount - 1);

        this.params.put("start", this.start);
        this.params.put("end", this.end);
        this.params.put("pageSize", this.pageSize);
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(final List<T> items) {
        this.items = items;
    }

    public boolean isFirst() {
        return this.page == 1;
    }

    public boolean isLast() {
        return this.page == this.pageCount;
    }

    public boolean hasNext() {
        return !this.isFirst() && !this.isLast();
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(final Map<String, Object> params) {
        this.params = params;
    }
}
