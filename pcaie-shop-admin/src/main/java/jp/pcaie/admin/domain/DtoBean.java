package jp.pcaie.admin.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class DtoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3788480701876184382L;

    private int id;

    private Date addDate;

    private Date updDate;

    private int delFlg;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(final Date addDate) {
        this.addDate = addDate;
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
