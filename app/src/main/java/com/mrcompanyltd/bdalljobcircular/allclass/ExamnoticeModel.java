package com.mrcompanyltd.bdalljobcircular.allclass;

public class ExamnoticeModel {
    String id, pdf, examNoticeTittle, examNoticeDate, examNoticeDetails, examNoticeSource, viewCount;

    public ExamnoticeModel() {
    }

    public ExamnoticeModel(String id, String pdf, String examNoticeTittle, String examNoticeDate, String examNoticeDetails, String examNoticeSource, String viewCount) {
        this.id = id;
        this.pdf = pdf;
        this.examNoticeTittle = examNoticeTittle;
        this.examNoticeDate = examNoticeDate;
        this.examNoticeDetails = examNoticeDetails;
        this.examNoticeSource = examNoticeSource;
        this.viewCount = viewCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getExamNoticeTittle() {
        return examNoticeTittle;
    }

    public void setExamNoticeTittle(String examNoticeTittle) {
        this.examNoticeTittle = examNoticeTittle;
    }

    public String getExamNoticeDate() {
        return examNoticeDate;
    }

    public void setExamNoticeDate(String examNoticeDate) {
        this.examNoticeDate = examNoticeDate;
    }

    public String getExamNoticeDetails() {
        return examNoticeDetails;
    }

    public void setExamNoticeDetails(String examNoticeDetails) {
        this.examNoticeDetails = examNoticeDetails;
    }

    public String getExamNoticeSource() {
        return examNoticeSource;
    }

    public void setExamNoticeSource(String examNoticeSource) {
        this.examNoticeSource = examNoticeSource;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
}
