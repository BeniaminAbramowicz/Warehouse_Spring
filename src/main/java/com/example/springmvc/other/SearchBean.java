package com.example.springmvc.other;

public class SearchBean {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String category;
    private Double leftPriceField;
    private Double rightPriceField;
    private String leftOP;
    private String rightOP;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getLeftPriceField() {
        return leftPriceField;
    }

    public void setLeftPriceField(Double leftPriceField) {
        this.leftPriceField = leftPriceField;
    }

    public Double getRightPriceField() {
        return rightPriceField;
    }

    public void setRightPriceField(Double rightPriceField) {
        this.rightPriceField = rightPriceField;
    }

    public String getLeftOP() {
        return leftOP;
    }

    public void setLeftOP(String leftOP) {
        this.leftOP = leftOP;
    }

    public String getRightOP() {
        return rightOP;
    }

    public void setRightOP(String rightOP) {
        this.rightOP = rightOP;
    }

    public boolean isEmpty() {
        if(id!=null) return false;
        if(name!=null && !name.isEmpty()) return false;
        if(description!=null && !description.isEmpty()) return false;
        if(type!=null && !type.isEmpty()) return false;
        if(category!=null && !category.isEmpty()) return false;
        if(leftPriceField!=null) return false;
        if(rightPriceField!=null) return false;
//        if(leftOP!=null && !leftOP.isEmpty()) return false;
//        if(rightOP!=null && !rightOP.isEmpty()) return false;
        return true;
    }
}
