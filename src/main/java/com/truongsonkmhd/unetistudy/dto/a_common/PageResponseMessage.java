package com.truongsonkmhd.unetistudy.dto.a_common;
import com.truongsonkmhd.unetistudy.constant.AppConstant;

public class PageResponseMessage extends ResponseMessage {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalRecord;

    public PageResponseMessage() {
    }

    public PageResponseMessage(Integer currentPage, Integer pageSize, Long totalRecord, Object data) {
        super(AppConstant.ResponseConstant.SUCCESS, AppConstant.ResponseConstant.StatusCode.SUCCESS, AppConstant.ResponseConstant.MessageConstant.SuccessMessage.LOADED, data);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.totalPage = (int) Math.ceil((double)totalRecord / pageSize);
    }


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
