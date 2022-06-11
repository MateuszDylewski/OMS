package com.oms.model;

import javax.persistence.*;

@Entity(name = "Attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;
    @OneToOne
    private Form formId;
    private String name;
    private String type;
    @Lob
    private byte[] data;


    protected Attachment() {}
    public Attachment(Form formId, String name, String type, byte[] data) {
        this.formId = formId;
        this.name = name;
        this.type = type;
        this.data = data;
    }


    public Long getAttachmentId() {
        return attachmentId;
    }

    public Form getFormId() {
        return formId;
    }
    public void setFormId(Form formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
}
