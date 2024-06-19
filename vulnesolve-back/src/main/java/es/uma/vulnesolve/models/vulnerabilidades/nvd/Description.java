package es.uma.vulnesolve.models.vulnerabilidades.nvd;

public class Description {

    private String lang;
    private String value;

    public Description() {
    }

    public Description(String lang, String value) {
        this.lang = lang;
        this.value = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
