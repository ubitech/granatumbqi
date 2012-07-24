package eu.granatum.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Created with IntelliJ IDEA.
 * User: pgouvas
 * Date: 7/23/12
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "UIBeans")
@RequestScoped
public class UIBeans {

    /**
     * Creates a new instance of UIBeans
     */
    private String annotations;
    private String drugs;
    private String searchterm;

    public UIBeans() {
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }

    public String doSearch(){

        return annotations+" "+drugs+" "+searchterm;

    }


}
