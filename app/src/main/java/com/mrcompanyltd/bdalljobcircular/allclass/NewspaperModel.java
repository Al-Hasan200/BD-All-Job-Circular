package com.mrcompanyltd.bdalljobcircular.allclass;

public class NewspaperModel {
    String id, pdf, tittle, date, viewCount;

    public NewspaperModel() {
    }

    public NewspaperModel(String id, String pdf, String tittle, String date, String viewCount) {
        this.id = id;
        this.pdf = pdf;
        this.tittle = tittle;
        this.date = date;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
/*public NewspaperModel() {
    }

    public NewspaperModel(String pdf, String tittle, String date, String viewCount) {
        this.pdf = pdf;
        this.tittle = tittle;
        this.date = date;
        this.viewCount = viewCount;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }*/
}
