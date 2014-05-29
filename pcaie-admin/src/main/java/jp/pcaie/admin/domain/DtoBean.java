package jp.pcaie.admin.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class DtoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3788480701876184382L;

    private long id;

    private Date updDate;

    private int delFlg;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Date getUpdDate() {
        return this.updDate;
    }

    public void setUpdDate(final Date updDate) {
        this.updDate = updDate;
    }

    public int getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(final int delFlg) {
        this.delFlg = delFlg;
    }
}
